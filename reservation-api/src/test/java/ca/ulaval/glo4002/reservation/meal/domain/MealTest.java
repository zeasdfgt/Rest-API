package ca.ulaval.glo4002.reservation.meal.domain;

import ca.ulaval.glo4002.reservation.ingredient.domain.MealIngredient;
import ca.ulaval.glo4002.reservation.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

class MealTest {
  private final Money FIRST_PRICE = new Money(54);
  private final Money SECOND_PRICE = new Money(33);
  private Meal meal;
  private List<MealIngredient> ingredients;
  private MealIngredient aMealIngredient;
  private MealIngredient anotherMealIngredient;

  @BeforeEach
  public void mealInitialization() {
    aMealIngredient = mock(MealIngredient.class);
    anotherMealIngredient = mock(MealIngredient.class);
    ingredients = new ArrayList<>();
    ingredients.add(aMealIngredient);
    ingredients.add(anotherMealIngredient);
    meal = new Meal(ingredients);
  }

  @Test
  public void givenAnExistingMenu_whenCalculatingPrice_thenReturnsSumOfIndividualIngredients() {
    Money expectedPrice = FIRST_PRICE.add(SECOND_PRICE);

    willReturn(FIRST_PRICE).given(aMealIngredient).calculatePrice();
    willReturn(SECOND_PRICE).given(anotherMealIngredient).calculatePrice();
    Money totalPrice = meal.calculatePrice();

    assertEquals(expectedPrice, totalPrice);
  }

  @Test
  public void givenAnIngredientPresentInMeal_whenCheckingIfIngredientIsPresent_thenReturnsTrue() {
    String anIngredientName = "horse manure";
    willReturn(true).given(aMealIngredient).hasSameName(anIngredientName);

    assertTrue(meal.containsIngredient(anIngredientName));
  }

  @Test
  public void
      givenAnIngredientNotPresentInMeal_whenCheckingIfIngredientIsPresent_thenReturnsFalse() {
    String anIngredientNameInMeal = "horse manure";
    String anIngredientNameNotInMeal = "Une délectable pelletée de sable";

    willReturn(anIngredientNameInMeal).given(aMealIngredient).getIngredientName();
    willReturn(anIngredientNameInMeal).given(anotherMealIngredient).getIngredientName();

    assertFalse(meal.containsIngredient(anIngredientNameNotInMeal));
  }
}
