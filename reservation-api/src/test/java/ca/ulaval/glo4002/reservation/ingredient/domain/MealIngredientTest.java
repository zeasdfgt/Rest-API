package ca.ulaval.glo4002.reservation.ingredient.domain;

import ca.ulaval.glo4002.reservation.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MealIngredientTest {
  private final String AN_INGREDIENT_NAME = "gazon";
  private final Money A_PRICE_PER_WEIGHT = new Money(15);
  private final BigDecimal A_WEIGHT = new BigDecimal("13");
  private Ingredient ingredient;
  private MealIngredient mealIngredient;

  @BeforeEach
  public void ingredientAndMealIngredientInitialization() {
    ingredient = new Ingredient(AN_INGREDIENT_NAME, A_PRICE_PER_WEIGHT);
    mealIngredient = new MealIngredient(ingredient, A_WEIGHT);
  }

  @Test
  public void givenAMenuIngredient_whenCalculatingPrice_thenReturnsRightPrice() {
    Money expectedPrice = A_PRICE_PER_WEIGHT.multiply(A_WEIGHT);
    Money price = mealIngredient.calculatePrice();

    assertEquals(expectedPrice, price);
  }
}
