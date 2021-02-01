package ca.ulaval.glo4002.reservation.reports.ingredient.domain.generator;

import ca.ulaval.glo4002.reservation.date.domain.Date;
import ca.ulaval.glo4002.reservation.date.domain.DateInterval;
import ca.ulaval.glo4002.reservation.ingredient.domain.MealIngredient;
import ca.ulaval.glo4002.reservation.ingredient.domain.repositories.MealIngredientRepository;
import ca.ulaval.glo4002.reservation.reports.ingredient.domain.IngredientReport;
import ca.ulaval.glo4002.reservation.reports.ingredient.domain.UnitIngredientReport;

import java.util.List;

public class UnitIngredientReportGenerator {
  private final MealIngredientRepository mealIngredientRepository;

  public UnitIngredientReportGenerator(MealIngredientRepository mealIngredientRepository) {
    this.mealIngredientRepository = mealIngredientRepository;
  }

  public UnitIngredientReport generateReport(DateInterval dateInterval) {
    UnitIngredientReport report = new UnitIngredientReport(mealIngredientRepository);
    for (Date date = dateInterval.getStartDate();
        dateInterval.isWithinInterval(date);
        date = date.plusDays(1)) {
      List<MealIngredient> mealIngredientsForDate =
          mealIngredientRepository.getMealIngredientsByDate(date);
      IngredientReport dailyIngredientReport = new IngredientReport(mealIngredientsForDate);
      report.addDate(date, dailyIngredientReport);
    }
    return report;
  }
}
