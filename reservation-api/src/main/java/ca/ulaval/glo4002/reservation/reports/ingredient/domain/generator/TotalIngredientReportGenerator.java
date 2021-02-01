package ca.ulaval.glo4002.reservation.reports.ingredient.domain.generator;

import ca.ulaval.glo4002.reservation.date.domain.DateInterval;
import ca.ulaval.glo4002.reservation.ingredient.domain.MealIngredient;
import ca.ulaval.glo4002.reservation.ingredient.domain.repositories.MealIngredientRepository;
import ca.ulaval.glo4002.reservation.reports.ingredient.domain.IngredientReport;
import ca.ulaval.glo4002.reservation.reports.ingredient.domain.TotalIngredientReport;

import java.util.List;

public class TotalIngredientReportGenerator {
  private final MealIngredientRepository mealIngredientRepository;

  public TotalIngredientReportGenerator(MealIngredientRepository mealIngredientRepository) {
    this.mealIngredientRepository = mealIngredientRepository;
  }

  public TotalIngredientReport generateReport(DateInterval dateInterval) {
    List<MealIngredient> mealIngredientsForDate =
        mealIngredientRepository.getMealIngredientsByDateInterval(dateInterval);
    IngredientReport ingredientReport = new IngredientReport(mealIngredientsForDate);
    return new TotalIngredientReport(ingredientReport);
  }
}
