package ca.ulaval.glo4002.reservation.reservation.rest.assemblers;

import ca.ulaval.glo4002.reservation.ingredient.domain.exceptions.IngredientNotFoundException;
import ca.ulaval.glo4002.reservation.ingredient.domain.repositories.IngredientRepository;
import ca.ulaval.glo4002.reservation.meal.domain.Meal;
import ca.ulaval.glo4002.reservation.meal.domain.factories.MealsFactory;
import ca.ulaval.glo4002.reservation.meal.domain.repositories.MenuRepository;
import ca.ulaval.glo4002.reservation.reservation.domain.Customer;
import ca.ulaval.glo4002.reservation.reservation.domain.Restriction;
import ca.ulaval.glo4002.reservation.reservation.domain.RestrictionType;
import ca.ulaval.glo4002.reservation.reservation.domain.factories.RestrictionFactory;
import ca.ulaval.glo4002.reservation.reservation.rest.requests.CustomerRequest;
import ca.ulaval.glo4002.reservation.rest.exceptions.InvalidFormatException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomerRequestAssembler {
  private final MenuRepository menuRepository;
  private final IngredientRepository ingredientRepository;
  private final MealsFactory mealsFactory;
  private final RestrictionFactory restrictionFactory;

  public CustomerRequestAssembler(
      MenuRepository menuRepository, IngredientRepository ingredientRepository) {
    this.menuRepository = menuRepository;
    this.ingredientRepository = ingredientRepository;
    this.restrictionFactory = new RestrictionFactory();
    this.mealsFactory = new MealsFactory();
  }

  public CustomerRequestAssembler(
      MenuRepository menuRepository,
      IngredientRepository ingredientRepository,
      RestrictionFactory restrictionFactory,
      MealsFactory mealsFactory) {
    this.menuRepository = menuRepository;
    this.ingredientRepository = ingredientRepository;
    this.mealsFactory = mealsFactory;
    this.restrictionFactory = restrictionFactory;
  }

  public Customer fromRequest(CustomerRequest customerRequest)
      throws InvalidFormatException, IngredientNotFoundException {
    Set<Restriction> restrictionsSet = new HashSet<>();

    for (String restrictionName : customerRequest.getRestrictions()) {
      Restriction restriction =
          restrictionFactory.createRestriction(RestrictionType.getEnum(restrictionName));
      if (restriction.getType() == RestrictionType.NONE) {
        throw new InvalidFormatException();
      }
      restrictionsSet.add(restriction);
    }
    List<Restriction> restrictions = new ArrayList<>(restrictionsSet);

    List<Meal> meals =
        mealsFactory.createCustomerMeal(restrictions, ingredientRepository, menuRepository);

    return new Customer(customerRequest.getName(), restrictions, meals);
  }
}
