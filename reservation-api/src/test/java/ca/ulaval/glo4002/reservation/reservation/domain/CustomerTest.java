package ca.ulaval.glo4002.reservation.reservation.domain;

import ca.ulaval.glo4002.reservation.ingredient.domain.MealIngredient;
import ca.ulaval.glo4002.reservation.meal.domain.Meal;
import ca.ulaval.glo4002.reservation.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

class CustomerTest {
  private final String A_CUSTOMER_NAME = "Bob";
  private final Money BASE_PRICE = new Money(1000);
  private Customer customer;
  private List<Restriction> restrictions;
  private List<Meal> meals;
  private Meal aMeal;
  private Restriction aRestriction;
  private Restriction anotherRestriction;

  @BeforeEach
  public void customerInitialization() {
    restrictions = new ArrayList<>();
    aRestriction = mock(Restriction.class);
    anotherRestriction = mock(Restriction.class);
    restrictions.add(aRestriction);
    restrictions.add(anotherRestriction);
    meals = new ArrayList<>();
    aMeal = mock(Meal.class);
    meals.add(aMeal);

    customer = new Customer(A_CUSTOMER_NAME, restrictions, meals);
  }

  @Test
  public void givenNoRestriction_whenCalculatingCustomerTotalPrice_thenReturnsBasePrice() {
    customer = new Customer(A_CUSTOMER_NAME, new ArrayList<>(), meals);

    assertEquals(BASE_PRICE, customer.getTotalPrice());
  }

  @Test
  public void whenCalculatingCustomerTotalPrice_thenReturnsSumOfRestrictionsAndBasePrice() {
    Money price1 = new Money(1000);
    Money price2 = new Money(500);
    Money expectedPrice = new Money(2500);

    willReturn(price1).given(aRestriction).getPrice();
    willReturn(price2).given(anotherRestriction).getPrice();

    assertEquals(expectedPrice, customer.getTotalPrice());
  }

  @Test
  public void whenGettingMealIngredients_thenReturnsAllIngredientsOfEveryMeal() {
    ArrayList<MealIngredient> firstMealList = createMealListWithTwoMeals();
    ArrayList<MealIngredient> secondMealList = createMealListWithOneMeal();
    Meal anotherMeal = mock(Meal.class);
    meals.add(anotherMeal);
    willReturn(firstMealList).given(aMeal).getIngredients();
    willReturn(secondMealList).given(anotherMeal).getIngredients();
    ArrayList<MealIngredient> expectedList = createMergedList(firstMealList, secondMealList);

    List<MealIngredient> returnedList = customer.getMealIngredients();

    assertEquals(expectedList, returnedList);
  }

  @Test
  public void
      givenAnIngredientPresentInAnyMeal_whenCheckingIfIngredientIsPresent_thenReturnsTrue() {
    String anIngredientName = "bouette";
    Meal anotherMeal = mock(Meal.class);
    meals.add(anotherMeal);

    willReturn(true).given(anotherMeal).containsIngredient(anIngredientName);

    assertTrue(customer.hasIngredientInMeal(anIngredientName));
  }

  @Test
  public void
      givenAnIngredientNotPresentInAnyMeal_whenCheckingIfIngredientIsPresent_thenReturnsFalse() {
    String anIngredientName = "Un baril d'essence";
    Meal anotherMeal = mock(Meal.class);
    meals.add(anotherMeal);

    willReturn(false).given(aMeal).containsIngredient(anIngredientName);
    willReturn(false).given(anotherMeal).containsIngredient(anIngredientName);

    assertFalse(customer.hasIngredientInMeal(anIngredientName));
  }

  private ArrayList<MealIngredient> createMealListWithTwoMeals() {
    MealIngredient firstMealIngredient = mock(MealIngredient.class);
    MealIngredient secondMealIngredient = mock(MealIngredient.class);
    ArrayList<MealIngredient> firstMealList = new ArrayList<>();
    firstMealList.add(firstMealIngredient);
    firstMealList.add(secondMealIngredient);
    return firstMealList;
  }

  private ArrayList<MealIngredient> createMealListWithOneMeal() {
    MealIngredient aMealIngredient = mock(MealIngredient.class);
    ArrayList<MealIngredient> aMealList = new ArrayList<>();
    aMealList.add(aMealIngredient);
    return aMealList;
  }

  private ArrayList<MealIngredient> createMergedList(
      ArrayList<MealIngredient> firstList, ArrayList<MealIngredient> secondList) {
    ArrayList<MealIngredient> expectedList = new ArrayList<>();
    expectedList.addAll(firstList);
    expectedList.addAll(secondList);
    return expectedList;
  }
}
