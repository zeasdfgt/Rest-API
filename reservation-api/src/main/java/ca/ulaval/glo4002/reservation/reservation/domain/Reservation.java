package ca.ulaval.glo4002.reservation.reservation.domain;

import ca.ulaval.glo4002.reservation.date.domain.Date;
import ca.ulaval.glo4002.reservation.event.domain.Event;
import ca.ulaval.glo4002.reservation.ingredient.domain.MealIngredient;
import ca.ulaval.glo4002.reservation.meal.domain.Meal;
import ca.ulaval.glo4002.reservation.money.Money;
import ca.ulaval.glo4002.reservation.reservation.domain.exceptions.AllergyConflictException;
import ca.ulaval.glo4002.reservation.reservation.domain.exceptions.InvalidReservationQuantityException;
import ca.ulaval.glo4002.reservation.reservation.domain.exceptions.TooManyPeopleException;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Reservation {
  private static final int MAX_NUMBER_OF_CUSTOMERS_PER_RESERVATION = 6;
  private String vendorCode;
  private List<Table> tables;
  private Date dinnerDate;
  private Date reservationDate;
  private final ReservationNumber reservationNumber;

  public Reservation(
      ReservationNumber reservationNumber,
      String vendorCode,
      Date reservationDate,
      Date dinnerDate,
      List<Table> tables)
      throws AllergyConflictException, InvalidReservationQuantityException, TooManyPeopleException {
    this.reservationNumber = reservationNumber;
    this.tables = tables;
    this.dinnerDate = dinnerDate;
    this.reservationDate = reservationDate;
    this.vendorCode = vendorCode;
    validateConflictsWithSelf();
    validateTablesCount();
    validateCustomersCount();
  }

  public Date getDinnerDate() {
    return dinnerDate;
  }

  public boolean isSameDay(Date date) {
    return dinnerDate.isSameDay(date);
  }

  public boolean isDuringInterval(Date fromDate, Date toDate) {
    return dinnerDate.isDuringDayInterval(fromDate, toDate);
  }

  public Date getReservationDate() {
    return reservationDate;
  }

  public ZonedDateTime getDinnerDateAsZonedDateTime() {
    return dinnerDate.toZonedDateTime();
  }

  public Money getTotalPrice() {
    Money totalPrice = new Money();
    for (Table table : tables) {
      totalPrice = totalPrice.add(table.getTotalPrice());
    }
    return totalPrice;
  }

  public BigDecimal getTotalPriceAsRoundedBigDecimal() {
    return getTotalPrice().toRoundedBigDecimal();
  }

  public List<Table> getTables() {
    return tables;
  }

  public boolean hasTables() {
    return !tables.isEmpty();
  }

  public int getNumberOfCustomers() {
    int numberOfCustomers = 0;
    for (Table table : tables) {
      numberOfCustomers += table.getNumberOfCustomers();
    }
    return numberOfCustomers;
  }

  public List<MealIngredient> getMealIngredients() {
    List<MealIngredient> reservationMealIngredients = new ArrayList<>();
    for (Table table : tables) {
      reservationMealIngredients.addAll(table.getMealIngredients());
    }
    return reservationMealIngredients;
  }

  public List<Restriction> getUniqueReservationRestrictions() {
    HashMap<RestrictionType, Restriction> customerRestrictions = new HashMap<>();

    for (Table table : tables) {
      for (Restriction restriction : table.getCustomersRestrictions()) {
        customerRestrictions.putIfAbsent(restriction.getType(), restriction);
      }
    }

    return new ArrayList<>(customerRestrictions.values());
  }

  public boolean ingredientInAMeal(String ingredient) {
    for (Table table : tables) {
      if (table.ingredientInCustomersMeals(ingredient)) {
        return true;
      }
    }

    return false;
  }

  public List<RestrictionType> getUniqueReservationRestrictionsType() {
    HashMap<RestrictionType, Restriction> customerRestrictions = new HashMap<>();

    for (Table table : tables) {
      for (Restriction restriction : table.getCustomersRestrictions()) {
        customerRestrictions.putIfAbsent(restriction.getType(), restriction);
      }
    }

    return new ArrayList<>(customerRestrictions.keySet());
  }

  public List<Meal> getMeals() {
    List<Meal> meals = new ArrayList<>();
    for (Table table : tables) {
      meals.addAll(table.getMeals());
    }
    return meals;
  }

  public List<Customer> getCustomers() {
    ArrayList<Customer> customers = new ArrayList<>();
    for (Table table : tables) {
      customers.addAll(table.getCustomers());
    }
    return customers;
  }

  private void validateConflictsWithSelf() throws AllergyConflictException {
    if (this.getUniqueReservationRestrictionsType().contains(RestrictionType.ALLERGIES)
        && this.ingredientInAMeal(Event.ALLERGEN)) {
      throw new AllergyConflictException();
    }
  }

  private void validateTablesCount() throws InvalidReservationQuantityException {
    if (!this.hasTables()) {
      throw new InvalidReservationQuantityException();
    }
  }

  private void validateCustomersCount() throws TooManyPeopleException {
    if (this.getNumberOfCustomers() > MAX_NUMBER_OF_CUSTOMERS_PER_RESERVATION) {
      throw new TooManyPeopleException();
    }
  }

  public ReservationNumber getReservationNumber() {
    return reservationNumber;
  }
}
