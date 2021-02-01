package ca.ulaval.glo4002.reservation.reports.ingredient.rest.factories;

import ca.ulaval.glo4002.reservation.date.domain.Date;
import ca.ulaval.glo4002.reservation.date.domain.DateInterval;
import ca.ulaval.glo4002.reservation.money.Money;
import ca.ulaval.glo4002.reservation.reports.ingredient.application.IngredientReportService;
import ca.ulaval.glo4002.reservation.reports.ingredient.domain.IngredientReportType;
import ca.ulaval.glo4002.reservation.reports.ingredient.domain.TotalIngredientReport;
import ca.ulaval.glo4002.reservation.reports.ingredient.domain.UnitIngredientReport;
import ca.ulaval.glo4002.reservation.reports.ingredient.rest.responses.TotalIngredientReportResponse;
import ca.ulaval.glo4002.reservation.reports.ingredient.rest.responses.UnitIngredientReportResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

class IngredientReportResponseFactoryTest {
  private IngredientReportService ingredientReportService;
  private IngredientReportResponseFactory factory;
  private DateInterval aValidDinnerDate;

  @BeforeEach
  public void ingredientReportFactoryInitialization() {
    ingredientReportService = mock(IngredientReportService.class);
    factory = new IngredientReportResponseFactory(ingredientReportService);

    Date startDateDuringDinner = Date.startOfDay(2150, 7, 23);
    Date endDateAfterDinner = Date.endOfDay(2150, 7, 28);

    aValidDinnerDate = new DateInterval(startDateDuringDinner, endDateAfterDinner);
  }

  @Test
  public void givenATotalReportType_whenCreatingIngredientReport_thenReturnsATotalIngredientReport()
      throws Exception {
    TotalIngredientReport aTotalIngredientReport = mock(TotalIngredientReport.class);
    Money anyMoney = mock(Money.class);
    willReturn(anyMoney).given(aTotalIngredientReport).getTotalPrice();
    willReturn(aTotalIngredientReport)
        .given(ingredientReportService)
        .generateTotalIngredientReport(any());

    assertEquals(
        TotalIngredientReportResponse.class,
        factory.generateReport(aValidDinnerDate, IngredientReportType.TOTAL).getClass());
  }

  @Test
  public void givenAUnitReportType_whenCreatingIngredientReport_thenReturnsAUnitIngredientReport()
      throws Exception {
    UnitIngredientReport aUnitIngredientReport = mock(UnitIngredientReport.class);
    willReturn(aUnitIngredientReport)
        .given(ingredientReportService)
        .generateUnitIngredientReport(any());

    assertEquals(
        UnitIngredientReportResponse.class,
        factory.generateReport(aValidDinnerDate, IngredientReportType.UNIT).getClass());
  }
}
