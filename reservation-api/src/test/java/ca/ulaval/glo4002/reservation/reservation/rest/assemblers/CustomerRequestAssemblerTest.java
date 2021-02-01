package ca.ulaval.glo4002.reservation.reservation.rest.assemblers;

import ca.ulaval.glo4002.reservation.ingredient.domain.exceptions.IngredientNotFoundException;
import ca.ulaval.glo4002.reservation.ingredient.domain.repositories.IngredientRepository;
import ca.ulaval.glo4002.reservation.meal.domain.Meal;
import ca.ulaval.glo4002.reservation.meal.domain.factories.MealsFactory;
import ca.ulaval.glo4002.reservation.meal.domain.repositories.MenuRepository;
import ca.ulaval.glo4002.reservation.money.Money;
import ca.ulaval.glo4002.reservation.reservation.domain.Customer;
import ca.ulaval.glo4002.reservation.reservation.domain.Restriction;
import ca.ulaval.glo4002.reservation.reservation.domain.RestrictionType;
import ca.ulaval.glo4002.reservation.reservation.domain.factories.RestrictionFactory;
import ca.ulaval.glo4002.reservation.reservation.rest.requests.CustomerRequest;
import ca.ulaval.glo4002.reservation.rest.exceptions.InvalidFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

class CustomerRequestAssemblerTest {
  private final String A_RESTRICTION_STRING = RestrictionType.ALLERGIES.toString();
  private final String ANOTHER_RESTRICTION_STRING = RestrictionType.ILLNESS.toString();
  private MenuRepository menuRepository;
  private IngredientRepository ingredientRepository;
  private RestrictionFactory restrictionFactory;
  private MealsFactory mealsFactory;
  private CustomerRequestAssembler customerRequestAssembler;

  @BeforeEach
  public void customerRequestAssemblerInitialization() {
    menuRepository = mock(MenuRepository.class);
    ingredientRepository = mock(IngredientRepository.class);
    restrictionFactory = new RestrictionFactory();
    mealsFactory = mock(MealsFactory.class);

    customerRequestAssembler =
        new CustomerRequestAssembler(
            menuRepository, ingredientRepository, restrictionFactory, mealsFactory);
  }

  @Test
  public void
      givenTwoIdenticalRestrictionsForACustomer_thenRestrictionIsAddedOnlyOnceToTheRestrictions()
          throws Exception {
    List<String> customerRestrictions =
        new ArrayList<>(Arrays.asList(A_RESTRICTION_STRING, A_RESTRICTION_STRING));
    CustomerRequest customerRequest = createAValidCustomerRequest(customerRestrictions);

    Restriction anExpectedRestriction = createAValidRestriction(A_RESTRICTION_STRING);

    createAValidMealList();

    Customer customerResult = customerRequestAssembler.fromRequest(customerRequest);

    assertEquals(anExpectedRestriction, customerResult.getRestrictions().get(0));
    assertEquals(1, customerResult.getRestrictions().size());
  }

  @Test
  public void whenManyRestrictionsForACustomer_thenAllRestrictionsAreAddedToTheRestrictions()
      throws Exception {
    List<String> customerRestrictions = new ArrayList<>();
    customerRestrictions.add(A_RESTRICTION_STRING);
    customerRestrictions.add(ANOTHER_RESTRICTION_STRING);
    CustomerRequest customerRequest = createAValidCustomerRequest(customerRestrictions);

    Restriction anExpectedRestriction = createAValidRestriction(A_RESTRICTION_STRING);
    Restriction anotherExpectedRestriction = createAValidRestriction(ANOTHER_RESTRICTION_STRING);

    createAValidMealList();

    Customer customerResult = customerRequestAssembler.fromRequest(customerRequest);

    assertTrue(customerResult.getRestrictions().contains(anExpectedRestriction));
    assertTrue(customerResult.getRestrictions().contains(anotherExpectedRestriction));
    assertEquals(2, customerResult.getRestrictions().size());
  }

  @Test
  public void whenNoRestrictionForACustomer_thenNoRestrictionIsAddedToTheRestrictions()
      throws Exception {
    List<String> anEmptyCustomerRestrictions = new ArrayList<>();
    CustomerRequest customerRequest = createAValidCustomerRequest(anEmptyCustomerRestrictions);
    createAValidMealList();

    Customer customerResult = customerRequestAssembler.fromRequest(customerRequest);

    assertTrue(customerResult.getRestrictions().isEmpty());
  }

  private CustomerRequest createAValidCustomerRequest(List<String> customerRestrictions) {
    String aCustomerName = "Pailuc";
    return new CustomerRequest(aCustomerName, customerRestrictions);
  }

  private Restriction createAValidRestriction(String restrictionName)
      throws InvalidFormatException {
    Money aPrice = new Money(10);
    RestrictionType restrictionType = RestrictionType.getEnum(restrictionName);

    return new Restriction(aPrice, restrictionType);
  }

  private void createAValidMealList() throws IngredientNotFoundException {
    List<Meal> meals = new ArrayList<>();
    meals.add(mock(Meal.class));

    willReturn(meals)
        .given(mealsFactory)
        .createCustomerMeal(anyList(), eq(ingredientRepository), eq(menuRepository));
  }
}
