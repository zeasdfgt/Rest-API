package ca.ulaval.glo4002.reservation.reservation.domain;

import ca.ulaval.glo4002.reservation.ingredient.domain.MealIngredient;
import ca.ulaval.glo4002.reservation.meal.domain.Meal;
import ca.ulaval.glo4002.reservation.money.Money;
import ca.ulaval.glo4002.reservation.reservation.domain.exceptions.InvalidReservationQuantityException;
import ca.ulaval.glo4002.reservation.reservation.domain.exceptions.TooManyPeopleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

public class TableTest {
  private final int MAX_NUMBER_OF_CUSTOMERS_PER_TABLE = 4;
  private List<Customer> customers;
  private Customer firstCustomer;
  private Customer secondCustomer;

  @BeforeEach
  private void customerListInitialization() {
    customers = new ArrayList<>();
    firstCustomer = mock(Customer.class);
    secondCustomer = mock(Customer.class);
    customers.add(firstCustomer);
    customers.add(secondCustomer);
  }

  @Test
  public void whenGettingPriceOfTable_thenPriceIsTheSumOfAllCustomers()
      throws InvalidReservationQuantityException, TooManyPeopleException {
    Money firstPrice = new Money(1000);
    Money secondPrice = new Money(2500);
    Money expectedPrice = new Money(3500);

    willReturn(firstPrice).given(firstCustomer).getTotalPrice();
    willReturn(secondPrice).given(secondCustomer).getTotalPrice();

    Table newTable = new Table(customers);

    assertEquals(expectedPrice, newTable.getTotalPrice());
  }

  @Test
  public void
      givenTwoCustomersWithCommonRestriction_whenGettingRestrictions_thenRestrictionIsOnlyPresentOnceInReturnedList()
          throws InvalidReservationQuantityException, TooManyPeopleException {
    Restriction restriction = mock(Restriction.class);
    ArrayList<Restriction> restrictions = new ArrayList<>();
    restrictions.add(restriction);
    Table table = new Table(customers);

    willReturn(restrictions).given(firstCustomer).getRestrictions();
    willReturn(restrictions).given(secondCustomer).getRestrictions();

    ArrayList<Restriction> expectedRestrictions = new ArrayList<>();
    expectedRestrictions.add(restriction);

    assertEquals(expectedRestrictions, table.getCustomersRestrictions());
  }

  @Test
  public void
      givenAnIngredientPresentAnyCustomersMeal_whenCheckingIfIngredientIsPresent_thenReturnsTrue()
          throws InvalidReservationQuantityException, TooManyPeopleException {
    String anIngredientName = "De l'acier de grade militaire utilisé dans les ford150";
    Table table = new Table(customers);

    willReturn(true).given(firstCustomer).hasIngredientInMeal(anIngredientName);

    assertTrue(table.ingredientInCustomersMeals(anIngredientName));
  }

  @Test
  public void
      givenAnIngredientNotPresentInAnyCustomersMeal_whenCheckingIfIngredientIsPresent_thenReturnsFalse()
          throws InvalidReservationQuantityException, TooManyPeopleException {
    String anIngredientName = "gallon de peinture";
    Table table = new Table(customers);

    willReturn(false).given(firstCustomer).hasIngredientInMeal(anIngredientName);
    willReturn(false).given(secondCustomer).hasIngredientInMeal(anIngredientName);

    assertFalse(table.ingredientInCustomersMeals(anIngredientName));
  }

  @Test
  public void whenGettingTableMealIngredients_thenReturnsAllIngredientsOfEveryCustomerMeal()
      throws InvalidReservationQuantityException, TooManyPeopleException {
    Table table = new Table(customers);

    MealIngredient firstMealIngredient = mock(MealIngredient.class);
    MealIngredient secondMealIngredient = mock(MealIngredient.class);
    MealIngredient thirdMealIngredient = mock(MealIngredient.class);
    List<MealIngredient> firstMealIngredientList = new ArrayList<>();
    List<MealIngredient> secondMealIngredientList = new ArrayList<>();

    firstMealIngredientList.add(firstMealIngredient);
    firstMealIngredientList.add(secondMealIngredient);
    secondMealIngredientList.add(thirdMealIngredient);

    willReturn(firstMealIngredientList).given(firstCustomer).getMealIngredients();
    willReturn(secondMealIngredientList).given(secondCustomer).getMealIngredients();

    List<MealIngredient> expectedMealIngredientList = new ArrayList<>();
    expectedMealIngredientList.add(firstMealIngredient);
    expectedMealIngredientList.add(secondMealIngredient);
    expectedMealIngredientList.add(thirdMealIngredient);

    assertEquals(expectedMealIngredientList, table.getMealIngredients());
  }

  @Test
  public void givenTableOfMoreThanMaxCustomers_thenThrowsATooManyPeopleException() {
    assertThrows(TooManyPeopleException.class, () -> createTableWithMoreThanMaxCustomers());
  }

  @Test
  public void givenTableWithoutCustomer_thenThrowsInvalidReservationQuantityException() {
    assertThrows(InvalidReservationQuantityException.class, () -> createTableWithoutCustomer());
  }

  @Test
  public void givenTableWithCustomers_whenGettingMeals_thenReturnsEveryMealsForEveryCustomers()
      throws InvalidReservationQuantityException, TooManyPeopleException {
    Table table = new Table(customers);

    List<Meal> mealsOfFirstCustomer = new ArrayList<>();
    Meal firstMeal = mock(Meal.class);
    mealsOfFirstCustomer.add(firstMeal);
    willReturn(mealsOfFirstCustomer).given(firstCustomer).getMeals();

    List<Meal> mealsOfSecondCustomer = new ArrayList<>();
    Meal secondMeal = mock(Meal.class);
    mealsOfSecondCustomer.add(secondMeal);
    willReturn(mealsOfSecondCustomer).given(secondCustomer).getMeals();

    List<Meal> mealsOfTable = table.getMeals();
    int numberOfMealsAtTable = 2;

    assertEquals(numberOfMealsAtTable, mealsOfTable.size());
    assertTrue(mealsOfTable.contains(firstMeal));
    assertTrue(mealsOfTable.contains(secondMeal));
  }

  private Table createTableWithMoreThanMaxCustomers()
      throws InvalidReservationQuantityException, TooManyPeopleException {
    List<Customer> customers = new ArrayList<>();

    for (int i = 0; i < MAX_NUMBER_OF_CUSTOMERS_PER_TABLE + 1; i++) {
      customers.add(mock(Customer.class));
    }

    return new Table(customers);
  }

  private Table createTableWithoutCustomer()
      throws InvalidReservationQuantityException, TooManyPeopleException {
    List<Customer> customers = new ArrayList<>();
    return new Table(customers);
  }
}
