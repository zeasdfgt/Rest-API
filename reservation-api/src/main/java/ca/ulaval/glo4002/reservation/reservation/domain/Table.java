package ca.ulaval.glo4002.reservation.reservation.domain;

import ca.ulaval.glo4002.reservation.ingredient.domain.MealIngredient;
import ca.ulaval.glo4002.reservation.meal.domain.Meal;
import ca.ulaval.glo4002.reservation.money.Money;
import ca.ulaval.glo4002.reservation.reservation.domain.exceptions.InvalidReservationQuantityException;
import ca.ulaval.glo4002.reservation.reservation.domain.exceptions.TooManyPeopleException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Table {
  private final int MAX_NUMBER_OF_CUSTOMERS_PER_TABLE = 4;
  private final List<Customer> customers;

  public Table(List<Customer> customers)
      throws InvalidReservationQuantityException, TooManyPeopleException {
    validateCustomersNotEmpty(customers);
    validateMaxCustomersCount(customers);
    this.customers = customers;
  }

  public Money getTotalPrice() {
    Money total = new Money();
    for (Customer customer : customers) {
      total = total.add(customer.getTotalPrice());
    }
    return total;
  }

  public int getNumberOfCustomers() {
    return customers.size();
  }

  public List<Customer> getCustomers() {
    return customers;
  }

  public List<MealIngredient> getMealIngredients() {
    List<MealIngredient> tableMealIngredients = new ArrayList<>();
    for (Customer customer : customers) {
      tableMealIngredients.addAll(customer.getMealIngredients());
    }
    return tableMealIngredients;
  }

  public List<Restriction> getCustomersRestrictions() {
    HashMap<RestrictionType, Restriction> customerRestrictions = new HashMap<>();

    for (Customer customer : customers) {
      for (Restriction restriction : customer.getRestrictions()) {
        customerRestrictions.putIfAbsent(restriction.getType(), restriction);
      }
    }

    return new ArrayList<>(customerRestrictions.values());
  }

  public boolean ingredientInCustomersMeals(String ingredient) {
    for (Customer customer : customers) {
      if (customer.hasIngredientInMeal(ingredient)) {
        return true;
      }
    }

    return false;
  }

  public List<Meal> getMeals() {
    List<Meal> meals = new ArrayList<>();
    for (Customer customer : customers) {
      meals.addAll(customer.getMeals());
    }
    return meals;
  }

  private void validateCustomersNotEmpty(List<Customer> customers)
      throws InvalidReservationQuantityException {
    if (customers.isEmpty()) {
      throw new InvalidReservationQuantityException();
    }
  }

  private void validateMaxCustomersCount(List<Customer> customers) throws TooManyPeopleException {
    if (customers.size() > MAX_NUMBER_OF_CUSTOMERS_PER_TABLE) {
      throw new TooManyPeopleException();
    }
  }
}
