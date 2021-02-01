package ca.ulaval.glo4002.reservation.meal.domain;

import ca.ulaval.glo4002.reservation.ingredient.domain.MealIngredient;
import ca.ulaval.glo4002.reservation.money.Money;

import java.util.List;

public class Meal {
  private List<MealIngredient> ingredients;

  public Meal(List<MealIngredient> ingredients) {
    this.ingredients = ingredients;
  }

  public Money calculatePrice() {
    Money price = new Money();
    for (MealIngredient ingredient : ingredients) {
      price = price.add(ingredient.calculatePrice());
    }
    return price;
  }

  public List<MealIngredient> getIngredients() {
    return ingredients;
  }

  public boolean containsIngredient(String ingredient) {
    for (MealIngredient comparedIngredient : ingredients) {
      if (comparedIngredient.hasSameName(ingredient)) {
        return true;
      }
    }
    return false;
  }
}
