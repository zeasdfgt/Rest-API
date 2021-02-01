package ca.ulaval.glo4002.reservation.reports.ingredient.domain;

import ca.ulaval.glo4002.reservation.ingredient.domain.Ingredient;
import ca.ulaval.glo4002.reservation.ingredient.domain.MealIngredient;
import ca.ulaval.glo4002.reservation.money.Money;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IngredientReport {
  private Map<Ingredient, MealIngredient> mealIngredientsMap;

  public IngredientReport(List<MealIngredient> mealIngredients) {
    this.mealIngredientsMap = new HashMap<>();
    calculateTotalWeight(mealIngredients);
  }

  public List<MealIngredient> getMealIngredientsTotals() {
    return new ArrayList<>(mealIngredientsMap.values());
  }

  public Money getTotalPrice() {
    Money totalPrice = new Money();
    for (MealIngredient ingredient : mealIngredientsMap.values()) {
      totalPrice = totalPrice.add(ingredient.calculatePrice());
    }
    return totalPrice;
  }

  private void calculateTotalWeight(List<MealIngredient> mealIngredients) {
    for (MealIngredient mealIngredient : mealIngredients) {
      Ingredient ingredient = mealIngredient.getIngredient();
      MealIngredient totalMealIngredient = mealIngredientsMap.get(ingredient);
      if (totalMealIngredient == null) {
        totalMealIngredient = new MealIngredient(ingredient, new BigDecimal(0));
      }
      MealIngredient newMealIngredient = totalMealIngredient.addWeight(mealIngredient.getWeight());
      mealIngredientsMap.put(ingredient, newMealIngredient);
    }
  }
}
