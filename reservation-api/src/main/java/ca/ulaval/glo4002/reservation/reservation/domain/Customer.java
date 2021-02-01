package ca.ulaval.glo4002.reservation.reservation.domain;

import ca.ulaval.glo4002.reservation.ingredient.domain.MealIngredient;
import ca.ulaval.glo4002.reservation.meal.domain.Meal;
import ca.ulaval.glo4002.reservation.money.Money;

import java.util.ArrayList;
import java.util.List;

public class Customer {
  private final Money BASE_PRICE = new Money(1000);
  private String name;
  private List<Restriction> restrictions;
  private List<Meal> meals;

  public Customer(String name, List<Restriction> restrictions, List<Meal> meals) {
    this.name = name;
    this.restrictions = restrictions;
    this.meals = meals;
  }

  public String getName() {
    return name;
  }

  public List<Restriction> getRestrictions() {
    return restrictions;
  }

  public Money getTotalPrice() {
    Money total = BASE_PRICE;
    for (Restriction restriction : restrictions) {
      total = total.add(restriction.getPrice());
    }

    return total;
  }

  public List<MealIngredient> getMealIngredients() {
    List<MealIngredient> ingredients = new ArrayList<>();
    for (Meal meal : meals) {
      ingredients.addAll(meal.getIngredients());
    }
    return ingredients;
  }

  public boolean hasIngredientInMeal(String ingredient) {
    for (Meal meal : meals) {
      if (meal.containsIngredient(ingredient)) {
        return true;
      }
    }

    return false;
  }

  public List<Meal> getMeals() {
    return meals;
  }
}
