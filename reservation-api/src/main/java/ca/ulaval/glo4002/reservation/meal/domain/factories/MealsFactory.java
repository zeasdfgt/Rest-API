package ca.ulaval.glo4002.reservation.meal.domain.factories;

import ca.ulaval.glo4002.reservation.ingredient.domain.Ingredient;
import ca.ulaval.glo4002.reservation.ingredient.domain.MealIngredient;
import ca.ulaval.glo4002.reservation.ingredient.domain.exceptions.IngredientNotFoundException;
import ca.ulaval.glo4002.reservation.ingredient.domain.repositories.IngredientRepository;
import ca.ulaval.glo4002.reservation.meal.domain.Meal;
import ca.ulaval.glo4002.reservation.meal.domain.repositories.MenuRepository;
import ca.ulaval.glo4002.reservation.reservation.domain.Restriction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MealsFactory {
  public List<Meal> createCustomerMeal(
      List<Restriction> restrictions,
      IngredientRepository ingredientRepository,
      MenuRepository menuRepository)
      throws IngredientNotFoundException {
    List<Meal> meals = new ArrayList<>();
    if (restrictions.isEmpty()) {
      meals.add(new Meal(createDefaultMeal(ingredientRepository, menuRepository)));
    } else {
      for (Restriction customerRestriction : restrictions) {
        meals.add(
            new Meal(
                createRestrictionMeal(ingredientRepository, menuRepository, customerRestriction)));
      }
    }

    return meals;
  }

  private List<MealIngredient> createDefaultMeal(
      IngredientRepository ingredientRepository, MenuRepository menuRepository)
      throws IngredientNotFoundException {
    List<MealIngredient> mealIngredients = new ArrayList<>();
    HashMap<String, BigDecimal> ingredients = menuRepository.getDefaultMeal();
    createCustomerMeals(ingredientRepository, mealIngredients, ingredients);

    return mealIngredients;
  }

  private List<MealIngredient> createRestrictionMeal(
      IngredientRepository ingredientRepository,
      MenuRepository menuRepository,
      Restriction restriction)
      throws IngredientNotFoundException {
    List<MealIngredient> mealIngredients = new ArrayList<>();
    HashMap<String, BigDecimal> ingredients =
        menuRepository.getMealByRestriction(restriction.getType());
    createCustomerMeals(ingredientRepository, mealIngredients, ingredients);

    return mealIngredients;
  }

  private void createCustomerMeals(
      IngredientRepository ingredientRepository,
      List<MealIngredient> mealIngredients,
      HashMap<String, BigDecimal> ingredients)
      throws IngredientNotFoundException {
    for (String ingredientName : ingredients.keySet()) {
      Ingredient ingredientInfo = ingredientRepository.getIngredientByName(ingredientName);
      MealIngredient mealIngredient =
          new MealIngredient(ingredientInfo, ingredients.get(ingredientName));
      mealIngredients.add(mealIngredient);
    }
  }
}
