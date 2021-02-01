package ca.ulaval.glo4002.reservation.reservation.domain;

import ca.ulaval.glo4002.reservation.date.domain.Date;
import ca.ulaval.glo4002.reservation.event.domain.Event;
import ca.ulaval.glo4002.reservation.ingredient.domain.MealIngredient;
import ca.ulaval.glo4002.reservation.meal.domain.Meal;
import ca.ulaval.glo4002.reservation.money.Money;
import ca.ulaval.glo4002.reservation.reservation.domain.exceptions.AllergyConflictException;
import ca.ulaval.glo4002.reservation.reservation.domain.exceptions.InvalidReservationQuantityException;
import ca.ulaval.glo4002.reservation.reservation.domain.exceptions.TooManyPeopleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

class ReservationTest {
  private final String ALLERGEN = Event.ALLERGEN;
  private final ReservationNumber A_RESERVATION_NUMBER = new ReservationNumber("15314");
  private final String VENDOR_CODE = "VENDOR_CODE";
  private final Money A_TOTAL_PRICE = new Money(5000);
  private final Date A_RESERVATION_DATE = createDate(2150, 3, 1);
  private final Date A_DINNER_DATE = createDate(2150, 7, 25);
  private final Table A_TABLE = mock(Table.class);
  private List<Table> tables;

  @BeforeEach
  public void tableListInitialization() {
    tables = new ArrayList<>();
    willReturn(A_TOTAL_PRICE).given(A_TABLE).getTotalPrice();
    tables.add(A_TABLE);
  }

  @Test
  public void whenGettingTotalPrice_thenReturnSumOfTablesPrices()
      throws InvalidReservationQuantityException, AllergyConflictException, TooManyPeopleException {
    Money aPrice = new Money(3000);
    Money anotherPrice = new Money(5500);
    Money expectedPrice = new Money(8500);

    Table aTable = mock(Table.class);
    Table anotherTable = mock(Table.class);
    tables = new ArrayList<>();
    tables.add(aTable);
    tables.add(anotherTable);

    willReturn(aPrice).given(aTable).getTotalPrice();
    willReturn(anotherPrice).given(anotherTable).getTotalPrice();

    Reservation newReservation = createDefaultReservation();

    assertEquals(expectedPrice, newReservation.getTotalPrice());
  }

  @Test
  public void givenAnIngredientInATableMeal_whenQuestioningIfPresentInReservation_thenReturnsTrue()
      throws InvalidReservationQuantityException, AllergyConflictException, TooManyPeopleException {
    final String INGREDIENT_NAME = "anIngredient";
    Table aTable = mock(Table.class);
    willReturn(true).given(aTable).ingredientInCustomersMeals(INGREDIENT_NAME);

    tables = new ArrayList<>();
    tables.add(aTable);

    Reservation newReservation = createDefaultReservation();

    assertTrue(newReservation.ingredientInAMeal(INGREDIENT_NAME));
  }

  @Test
  public void whenGettingPresentIngredients_thenReturnsCombinationOfTablesMealIngredients()
      throws InvalidReservationQuantityException, AllergyConflictException, TooManyPeopleException {
    final String INGREDIENT_NAME = "anIngredient";

    MealIngredient aMealIngredient = mock(MealIngredient.class);
    willReturn(INGREDIENT_NAME).given(aMealIngredient).getIngredientName();
    List<MealIngredient> aMealIngredientList = new ArrayList<>();
    aMealIngredientList.add(aMealIngredient);

    MealIngredient anotherMealIngredient = mock(MealIngredient.class);
    willReturn(INGREDIENT_NAME).given(anotherMealIngredient).getIngredientName();
    List<MealIngredient> anotherMealIngredientList = new ArrayList<>();
    anotherMealIngredientList.add(anotherMealIngredient);

    Table aTable = mock(Table.class);
    willReturn(aMealIngredientList).given(aTable).getMealIngredients();

    Table anotherTable = mock(Table.class);
    willReturn(anotherMealIngredientList).given(anotherTable).getMealIngredients();

    tables = new ArrayList<>();
    tables.add(aTable);
    tables.add(anotherTable);

    Reservation newReservation = createDefaultReservation();

    List<MealIngredient> expectedResult = new ArrayList<>();
    expectedResult.add(aMealIngredient);
    expectedResult.add(anotherMealIngredient);

    assertEquals(expectedResult, newReservation.getMealIngredients());
  }

  @Test
  public void
      whenGettingCustomersRestrictions_thenReturnsAllUniqueRestrictionsPresentInReservation()
          throws InvalidReservationQuantityException, AllergyConflictException,
              TooManyPeopleException {
    Restriction anAllergyRestriction = mock(Restriction.class);
    willReturn(RestrictionType.ALLERGIES).given(anAllergyRestriction).getType();
    List<Restriction> aRestrictionsList = new ArrayList<>();
    aRestrictionsList.add(anAllergyRestriction);

    Restriction aVeganRestriction = mock(Restriction.class);
    willReturn(RestrictionType.VEGAN).given(aVeganRestriction).getType();
    aRestrictionsList.add(aVeganRestriction);

    List<Restriction> anotherRestrictionsList = new ArrayList<>();
    anotherRestrictionsList.add(anAllergyRestriction);

    Table aTable = mock(Table.class);
    willReturn(aRestrictionsList).given(aTable).getCustomersRestrictions();

    Table anotherTable = mock(Table.class);
    willReturn(anotherRestrictionsList).given(anotherTable).getCustomersRestrictions();

    tables = new ArrayList<>();
    tables.add(aTable);
    tables.add(anotherTable);

    Reservation newReservation = createDefaultReservation();

    List<Restriction> expectedResult = new ArrayList<>();
    expectedResult.add(anAllergyRestriction);
    expectedResult.add(aVeganRestriction);

    assertEquals(expectedResult.size(), newReservation.getUniqueReservationRestrictions().size());
  }

  @Test
  public void givenReservationWithTables_whenGettingMeals_thenReturnsEveryMealsOfEveryTables()
      throws InvalidReservationQuantityException, AllergyConflictException, TooManyPeopleException {
    List<Meal> aListOfMeals = new ArrayList<>();
    Meal aMeal = mock(Meal.class);
    aListOfMeals.add(aMeal);

    List<Meal> anOtherListOfMeals = new ArrayList<>();
    Meal anOtherMeal = mock(Meal.class);
    anOtherListOfMeals.add(anOtherMeal);
    Table anOtherTable = mock(Table.class);
    tables.add(anOtherTable);

    willReturn(aListOfMeals).given(A_TABLE).getMeals();
    willReturn(anOtherListOfMeals).given(anOtherTable).getMeals();

    Reservation reservation = createDefaultReservation();

    List<Meal> mealsOfTable = reservation.getMeals();
    int numberOfMealsAtTable = 2;

    assertEquals(numberOfMealsAtTable, mealsOfTable.size());
    assertTrue(mealsOfTable.contains(aMeal));
    assertTrue(mealsOfTable.contains(anOtherMeal));
  }

  @Test
  public void givenAReservationOnSameDate_whenCheckingIfSameDay_thenReturnsTrue()
      throws InvalidReservationQuantityException, AllergyConflictException, TooManyPeopleException {
    Reservation aReservation = createDefaultReservation();

    boolean sameDate = aReservation.isSameDay(A_DINNER_DATE);

    assertTrue(sameDate);
  }

  @Test
  public void givenAReservationOnDifferentDate_whenCheckingIfSameDay_thenReturnsFalse()
      throws InvalidReservationQuantityException, AllergyConflictException, TooManyPeopleException {
    Reservation aReservation = createDefaultReservation();
    Date aDifferentDate = createDate(2150, 7, 26);

    boolean sameDate = aReservation.isSameDay(aDifferentDate);

    assertFalse(sameDate);
  }

  @Test
  public void givenAReservationWithinTheInterval_whenCheckingIfWithinTheInterval_theReturnsTrue()
      throws InvalidReservationQuantityException, AllergyConflictException, TooManyPeopleException {
    Reservation aReservation = createDefaultReservation();
    Date aStartDate = createDate(2150, 7, 23);
    Date anEndDate = createDate(2150, 7, 29);

    boolean withinInterval = aReservation.isDuringInterval(aStartDate, anEndDate);

    assertTrue(withinInterval);
  }

  @Test
  public void
      givenAReservationNotWithinTheInterval_whenCheckingIfWithinTheInterval_theReturnsFalse()
          throws InvalidReservationQuantityException, AllergyConflictException,
              TooManyPeopleException {
    Reservation aReservation = createDefaultReservation();
    Date aStartDate = createDate(2150, 7, 27);
    Date anEndDate = createDate(2150, 7, 29);

    boolean withinInterval = aReservation.isDuringInterval(aStartDate, anEndDate);

    assertFalse(withinInterval);
  }

  @Test
  public void
      givenAReservationOnSameDayAsIntervalStartDate_henCheckingIfWithinTheInterval_theReturnsTrue()
          throws InvalidReservationQuantityException, AllergyConflictException,
              TooManyPeopleException {
    Reservation aReservation = createDefaultReservation();
    Date aStartDate = createDate(2150, 7, 25);
    Date anEndDate = createDate(2150, 7, 29);

    boolean withinInterval = aReservation.isDuringInterval(aStartDate, anEndDate);

    assertTrue(withinInterval);
  }

  @Test
  public void
      givenAReservationOnSameDayAsIntervalEndDate_henCheckingIfWithinTheInterval_thenReturnsTrue()
          throws InvalidReservationQuantityException, AllergyConflictException,
              TooManyPeopleException {
    Reservation aReservation = createDefaultReservation();
    Date aStartDate = createDate(2150, 7, 22);
    Date anEndDate = createDate(2150, 7, 25);

    boolean withinInterval = aReservation.isDuringInterval(aStartDate, anEndDate);

    assertTrue(withinInterval);
  }

  @Test
  public void
      givenAReservationWithNoTables_whenCreatingReservation_thenThrowsInvalidReservationQuantityException() {
    List<Table> reservationTables = new ArrayList<>();

    assertThrows(
        InvalidReservationQuantityException.class,
        () ->
            new Reservation(
                A_RESERVATION_NUMBER,
                VENDOR_CODE,
                A_RESERVATION_DATE,
                A_DINNER_DATE,
                reservationTables));
  }

  @Test
  public void
      givenAReservationWithMoreThanMaxAmountOfCustomers_whenCreatingReservation_thenThrowsTooManyPeopleException() {
    int numberOfCustomerInReservations = 4;
    Table anotherTable = mock(Table.class);
    tables.add(anotherTable);
    willReturn(numberOfCustomerInReservations).given(A_TABLE).getNumberOfCustomers();
    willReturn(numberOfCustomerInReservations).given(anotherTable).getNumberOfCustomers();

    assertThrows(TooManyPeopleException.class, this::createDefaultReservation);
  }

  @Test
  public void
      givenAReservationWithBothAllergiesAndAllergen_whenCreatingReservation_thenThrowsAllergyConflictException() {
    willReturn(true).given(A_TABLE).ingredientInCustomersMeals(ALLERGEN);
    List<Restriction> restrictions = new ArrayList<>();
    RestrictionType restrictionType = RestrictionType.ALLERGIES;
    Restriction restriction = mock(Restriction.class);
    restrictions.add(restriction);
    willReturn(restrictionType).given(restriction).getType();
    willReturn(restrictions).given(A_TABLE).getCustomersRestrictions();

    assertThrows(AllergyConflictException.class, this::createDefaultReservation);
  }

  @Test
  public void givenAReservationWithOneTableOrMore_whenCheckingIfHasTables_thenReturnTrue()
      throws InvalidReservationQuantityException, AllergyConflictException, TooManyPeopleException {
    Reservation reservation = createDefaultReservation();

    boolean hasTables = reservation.hasTables();

    assertTrue(hasTables);
  }

  @Test
  public void whenGettingCustomers_thenReturnsAllCustomersFromEveryTable()
      throws InvalidReservationQuantityException, AllergyConflictException, TooManyPeopleException {
    Customer firstCustomer = mock(Customer.class);
    Customer secondCustomer = mock(Customer.class);
    Customer thirdCustomer = mock(Customer.class);
    List<Customer> firstCustomerList = new ArrayList<>();
    List<Customer> secondCustomerList = new ArrayList<>();
    firstCustomerList.add(firstCustomer);
    firstCustomerList.add(secondCustomer);
    secondCustomerList.add(thirdCustomer);
    Table secondTable = mock(Table.class);
    willReturn(firstCustomerList).given(A_TABLE).getCustomers();
    willReturn(secondCustomerList).given(secondTable).getCustomers();
    tables.add(secondTable);
    Reservation reservation = createDefaultReservation();

    List<Customer> returnedList = reservation.getCustomers();

    assertTrue(returnedList.contains(firstCustomer));
    assertTrue(returnedList.contains(secondCustomer));
    assertTrue(returnedList.contains(thirdCustomer));
  }

  private Reservation createDefaultReservation()
      throws InvalidReservationQuantityException, AllergyConflictException, TooManyPeopleException {
    return new Reservation(
        A_RESERVATION_NUMBER, VENDOR_CODE, A_RESERVATION_DATE, A_DINNER_DATE, tables);
  }

  private Date createDate(int year, int month, int day) {
    return Date.startOfDay(year, month, day);
  }
}
