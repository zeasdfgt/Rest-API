package ca.ulaval.glo4002.reservation.reports.ingredient.application;

import ca.ulaval.glo4002.reservation.date.domain.Date;
import ca.ulaval.glo4002.reservation.date.domain.DateInterval;
import ca.ulaval.glo4002.reservation.event.application.ConfigService;
import ca.ulaval.glo4002.reservation.event.domain.EventDateConfiguration;
import ca.ulaval.glo4002.reservation.ingredient.domain.MealIngredient;
import ca.ulaval.glo4002.reservation.ingredient.domain.repositories.MealIngredientRepository;
import ca.ulaval.glo4002.reservation.ingredient.infrastructure.persistence.MealIngredientFromMealsRepository;
import ca.ulaval.glo4002.reservation.reports.exceptions.InvalidReportDateException;
import ca.ulaval.glo4002.reservation.reports.ingredient.domain.TotalIngredientReport;
import ca.ulaval.glo4002.reservation.reports.ingredient.domain.UnitIngredientReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

class IngredientReportServiceTest {
  private final String A_DATE_DURING_DINNER = "2150-07-24";
  private final String A_DATE_NOT_DURING_DINNER = "2120-07-12";
  private final EventDateConfiguration AN_EVENT_DATE_CONFIGURATION = new EventDateConfiguration();
  private final DateInterval DINNER_DATE_INTERVAL =
      AN_EVENT_DATE_CONFIGURATION.getDinnerDateInterval();

  private IngredientReportService ingredientReportService;
  private MealIngredientRepository mealIngredientRepository;
  private ConfigService configService;

  @BeforeEach
  public void ingredientReportServiceInitialisation() {
    mealIngredientRepository = mock(MealIngredientFromMealsRepository.class);
    configService = mock(ConfigService.class);
    ingredientReportService = new IngredientReportService(mealIngredientRepository, configService);

    willReturn(DINNER_DATE_INTERVAL).given(configService).getDinnerDateInterval();
  }

  @Test
  public void
      givenInvalidDateInterval_whenRequestingUnitIngredientReport_thenThrowsInvalidDinnerDateException()
          throws Exception {
    DateInterval anInvalidDateInterval = getInvalidDinnerDateInterval();

    assertThrows(
        InvalidReportDateException.class,
        () -> ingredientReportService.generateUnitIngredientReport(anInvalidDateInterval));
  }

  @Test
  public void
      givenInvalidDateInterval_whenRequestingTotalIngredientReport_thenThrowsInvalidDinnerDateException()
          throws Exception {
    DateInterval anInvalidDateInterval = getInvalidDinnerDateInterval();

    assertThrows(
        InvalidReportDateException.class,
        () -> ingredientReportService.generateTotalIngredientReport(anInvalidDateInterval));
  }

  @Test
  public void whenRequestingTotalIngredientReport_thenReturnsATotalIngredientReport()
      throws Exception {
    List<MealIngredient> ingredients = new ArrayList<>();
    willReturn(ingredients).given(mealIngredientRepository).getMealIngredientsByDate(any());
    DateInterval aValidDateInterval = getValidDinnerDateInterval();
    assertEquals(
        TotalIngredientReport.class,
        ingredientReportService.generateTotalIngredientReport(aValidDateInterval).getClass());
  }

  @Test
  public void whenRequestingUnitIngredientReport_thenReturnsAUnitIngredientReport()
      throws Exception {
    List<MealIngredient> ingredients = new ArrayList<>();
    willReturn(ingredients).given(mealIngredientRepository).getMealIngredientsByDate(any());
    DateInterval aValidDateInterval = getValidDinnerDateInterval();
    assertEquals(
        UnitIngredientReport.class,
        ingredientReportService.generateUnitIngredientReport(aValidDateInterval).getClass());
  }

  private DateInterval getInvalidDinnerDateInterval() throws Exception {
    Date startDateDuringDinner = Date.parseLocalDate(A_DATE_NOT_DURING_DINNER);
    Date endDateAfterDinner = Date.parseLocalDate(A_DATE_DURING_DINNER);
    return new DateInterval(startDateDuringDinner, endDateAfterDinner);
  }

  private DateInterval getValidDinnerDateInterval() throws Exception {
    Date startDateDuringDinner = Date.startOfDay(2150, 7, 21);
    Date endDateAfterDinner = Date.parseLocalDate(A_DATE_DURING_DINNER);
    return new DateInterval(startDateDuringDinner, endDateAfterDinner);
  }
}
