package ca.ulaval.glo4002.reservation.reservation.rest.assemblers;

import ca.ulaval.glo4002.reservation.ingredient.domain.exceptions.IngredientNotFoundException;
import ca.ulaval.glo4002.reservation.ingredient.domain.repositories.IngredientRepository;
import ca.ulaval.glo4002.reservation.meal.domain.repositories.MenuRepository;
import ca.ulaval.glo4002.reservation.reservation.domain.Customer;
import ca.ulaval.glo4002.reservation.reservation.domain.Table;
import ca.ulaval.glo4002.reservation.reservation.domain.exceptions.InvalidReservationQuantityException;
import ca.ulaval.glo4002.reservation.reservation.domain.exceptions.TooManyPeopleException;
import ca.ulaval.glo4002.reservation.reservation.rest.requests.CustomerRequest;
import ca.ulaval.glo4002.reservation.reservation.rest.requests.TableRequest;
import ca.ulaval.glo4002.reservation.rest.exceptions.InvalidFormatException;

import java.util.ArrayList;
import java.util.List;

public class TableRequestAssembler {
  private final CustomerRequestAssembler customerRequestAssembler;

  public TableRequestAssembler(
      MenuRepository menuRepository, IngredientRepository ingredientRepository) {
    this.customerRequestAssembler =
        new CustomerRequestAssembler(menuRepository, ingredientRepository);
  }

  public TableRequestAssembler(CustomerRequestAssembler customerRequestAssembler) {
    this.customerRequestAssembler = customerRequestAssembler;
  }

  public Table fromRequest(TableRequest tableRequest)
      throws InvalidReservationQuantityException, TooManyPeopleException, InvalidFormatException,
          IngredientNotFoundException {
    List<Customer> customers = new ArrayList<>();

    for (CustomerRequest customerRequest : tableRequest.getCustomers()) {
      customers.add(customerRequestAssembler.fromRequest(customerRequest));
    }

    return new Table(customers);
  }
}
