package ca.ulaval.glo4002.reservation.ingredient.infrastructure.persistence;

import ca.ulaval.glo4002.reservation.date.domain.Date;
import ca.ulaval.glo4002.reservation.date.domain.DateInterval;
import ca.ulaval.glo4002.reservation.ingredient.domain.MealIngredient;
import ca.ulaval.glo4002.reservation.ingredient.domain.repositories.MealIngredientRepository;
import ca.ulaval.glo4002.reservation.meal.domain.Meal;
import ca.ulaval.glo4002.reservation.meal.domain.repositories.MealRepository;

import java.util.ArrayList;
import java.util.List;

public class MealIngredientFromMealsRepository implements MealIngredientRepository {
  private final MealRepository mealRepository;

  public MealIngredientFromMealsRepository(MealRepository mealRepository) {
    this.mealRepository = mealRepository;
  }

  public List<MealIngredient> getMealIngredientsByDate(Date date) {
    List<Meal> meals = mealRepository.getMealsByDate(date);
    List<MealIngredient> mealIngredients = new ArrayList<>();
    for (Meal meal : meals) {
      List<MealIngredient> ingredients = meal.getIngredients();
      for (MealIngredient mealIngredient : ingredients) {
        mealIngredients.add(mealIngredient);
      }
    }
    return mealIngredients;
  }

  public List<MealIngredient> getMealIngredientsByDateInterval(DateInterval dateInterval) {
    List<MealIngredient> mealIngredients = new ArrayList<>();
    for (Date date = dateInterval.getStartDate();
        dateInterval.isWithinInterval(date);
        date = date.plusDays(1)) {
      List<Meal> meals = mealRepository.getMealsByDate(date);
      for (Meal meal : meals) {
        List<MealIngredient> ingredients = meal.getIngredients();
        mealIngredients.addAll(ingredients);
      }
    }
    return mealIngredients;
  }
}
