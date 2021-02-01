package ca.ulaval.glo4002.reservation.reports.total.domain;

import ca.ulaval.glo4002.reservation.date.domain.Date;
import ca.ulaval.glo4002.reservation.date.domain.DateInterval;
import ca.ulaval.glo4002.reservation.event.application.ConfigService;
import ca.ulaval.glo4002.reservation.event.domain.Event;
import ca.ulaval.glo4002.reservation.money.Money;
import ca.ulaval.glo4002.reservation.reports.chef.application.ChefReportService;
import ca.ulaval.glo4002.reservation.reports.chef.domain.DailyChefReport;
import ca.ulaval.glo4002.reservation.reports.exceptions.InvalidReportDateException;
import ca.ulaval.glo4002.reservation.reports.ingredient.application.IngredientReportService;
import ca.ulaval.glo4002.reservation.reports.ingredient.domain.TotalIngredientReport;
import ca.ulaval.glo4002.reservation.reports.material.application.MaterialReportService;
import ca.ulaval.glo4002.reservation.reports.material.domain.MaterialReport;
import ca.ulaval.glo4002.reservation.reservation.domain.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

class TotalReportFactoryTest {
  private final Date START_DATE = new Date(2150, 4, 9, 5, 9, 9);
  private final Date END_DATE = new Date(2150, 4, 9, 15, 9, 9);
  private final DateInterval DATE_INTERVAL = new DateInterval(START_DATE, END_DATE);
  private final Money RESERVATION_PRICE = new Money(5000);
  private final Money INGREDIENTS_PRICE = new Money(3000);
  private final Money CHEFS_PRICE = new Money(500);
  private final Money MATERIAL_PRICE = new Money(300);

  private TotalReportFactory totalReportFactory;
  private Event event;
  private Reservation reservation;
  private TotalIngredientReport totalIngredientReport;
  private DailyChefReport dailyChefReport;
  private MaterialReport materialReport;
  private ConfigService configService;
  private MaterialReportService materialReportService;
  private IngredientReportService ingredientReportService;
  private ChefReportService chefReportService;

  @BeforeEach
  public void totalReportFactoryInitialization() throws InvalidReportDateException {
    event = mock(Event.class);
    configService = mock(ConfigService.class);
    materialReportService = mock(MaterialReportService.class);
    ingredientReportService = mock(IngredientReportService.class);
    chefReportService = mock(ChefReportService.class);
    totalReportFactory =
        new TotalReportFactory(
            event,
            configService,
            materialReportService,
            ingredientReportService,
            chefReportService);
    List<Reservation> reservations = new ArrayList<>();
    reservation = mock(Reservation.class);
    reservations.add(reservation);
    willReturn(reservations).given(event).getAllReservations();
    willReturn(RESERVATION_PRICE).given(reservation).getTotalPrice();

    totalIngredientReport = mock(TotalIngredientReport.class);
    willReturn(INGREDIENTS_PRICE).given(totalIngredientReport).getTotalPrice();
    willReturn(DATE_INTERVAL).given(configService).getDinnerDateInterval();
    willReturn(totalIngredientReport)
        .given(ingredientReportService)
        .generateTotalIngredientReport(DATE_INTERVAL);

    dailyChefReport = mock(DailyChefReport.class);
    List<DailyChefReport> dailyChefReports = new ArrayList<>();
    dailyChefReports.add(dailyChefReport);
    willReturn(dailyChefReports).given(chefReportService).getChefReports();
    willReturn(CHEFS_PRICE).given(dailyChefReport).getPriceForTheDay();

    materialReport = mock(MaterialReport.class);
    willReturn(materialReport).given(materialReportService).generateMaterialReport(any());
    willReturn(MATERIAL_PRICE).given(materialReport).getTotalPrice();
  }

  @Test
  public void whenGeneratingTotalReport_returnsTotalReportContainingAccurateIncome()
      throws InvalidReportDateException {
    double expectedIncome = RESERVATION_PRICE.toRoundedBigDecimal().doubleValue();

    TotalReport returnedReport = totalReportFactory.generateTotalReport();

    assertEquals(expectedIncome, returnedReport.getIncome());
  }

  @Test
  public void whenGeneratingTotalReport_returnTotalReportContainingAccurateExpense()
      throws InvalidReportDateException {
    Money expectedExpense = new Money(0);
    expectedExpense = expectedExpense.add(INGREDIENTS_PRICE);
    expectedExpense = expectedExpense.add(CHEFS_PRICE);
    expectedExpense = expectedExpense.add(MATERIAL_PRICE);
    double expectedExpenseDouble = expectedExpense.toRoundedBigDecimal().doubleValue();

    TotalReport returnedReport = totalReportFactory.generateTotalReport();

    assertEquals(expectedExpenseDouble, returnedReport.getExpense());
  }

  @Test
  public void whenGeneratingTotalReport_returnTotalReportContainingAccurateProfits()
      throws InvalidReportDateException {
    Money expectedExpense = new Money(0);
    expectedExpense = expectedExpense.add(INGREDIENTS_PRICE);
    expectedExpense = expectedExpense.add(CHEFS_PRICE);
    expectedExpense = expectedExpense.add(MATERIAL_PRICE);
    double expectedExpenseDouble = expectedExpense.toRoundedBigDecimal().doubleValue();
    double expectedIncome = RESERVATION_PRICE.toRoundedBigDecimal().doubleValue();
    double expectedProfits = expectedIncome - expectedExpenseDouble;

    TotalReport returnedReport = totalReportFactory.generateTotalReport();

    assertEquals(expectedProfits, returnedReport.getProfits());
  }
}
