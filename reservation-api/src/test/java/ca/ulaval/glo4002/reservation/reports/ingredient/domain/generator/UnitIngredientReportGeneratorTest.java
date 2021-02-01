package ca.ulaval.glo4002.reservation.reports.ingredient.domain.generator;

import ca.ulaval.glo4002.reservation.date.domain.Date;
import ca.ulaval.glo4002.reservation.date.domain.DateInterval;
import ca.ulaval.glo4002.reservation.ingredient.domain.repositories.MealIngredientRepository;
import ca.ulaval.glo4002.reservation.reports.ingredient.domain.IngredientReport;
import ca.ulaval.glo4002.reservation.reports.ingredient.domain.UnitIngredientReport;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class UnitIngredientReportGeneratorTest {
  private final MealIngredientRepository mealIngredientRepository =
      mock(MealIngredientRepository.class);
  private final UnitIngredientReportGenerator reportGenerator =
      new UnitIngredientReportGenerator(mealIngredientRepository);

  @Test
  public void whenGeneratingReport_thenAddsEveryDaysWithinInterval() {
    Date startDateWithinDinnerDate = Date.startOfDay(2150, 7, 21);
    Date endDateWithinDinnerDate = Date.endOfDay(2150, 7, 23);
    DateInterval dateIntervalWithinDinnerDate =
        new DateInterval(startDateWithinDinnerDate, endDateWithinDinnerDate);

    UnitIngredientReport report = reportGenerator.generateReport(dateIntervalWithinDinnerDate);
    Map<Date, IngredientReport> hashMapReport = report.toMap();

    Date dateBetweenStartAndEndDate = Date.startOfDay(2150, 7, 22);
    Date otherDateBetweenStartAndEndDate = Date.startOfDay(2150, 7, 23);

    assertTrue(hashMapReport.containsKey(startDateWithinDinnerDate));
    assertTrue(hashMapReport.containsKey(dateBetweenStartAndEndDate));
    assertTrue(hashMapReport.containsKey(otherDateBetweenStartAndEndDate));
  }
}
