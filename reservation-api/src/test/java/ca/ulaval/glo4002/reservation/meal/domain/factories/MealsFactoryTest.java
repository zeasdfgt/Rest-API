package ca.ulaval.glo4002.reservation.meal.domain.factories;

import ca.ulaval.glo4002.reservation.ingredient.domain.repositories.IngredientRepository;
import ca.ulaval.glo4002.reservation.meal.domain.repositories.MenuRepository;
import ca.ulaval.glo4002.reservation.meal.infrastructure.persistence.InMemoryMenuRepository;
import ca.ulaval.glo4002.reservation.money.Money;
import ca.ulaval.glo4002.reservation.reservation.domain.Restriction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static ca.ulaval.glo4002.reservation.reservation.domain.RestrictionType.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.times;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class MealsFactoryTest {
  private MealsFactory aMealsFactory;
  private MenuRepository aMenuRepository;
  private List<Restriction> restrictions;
  private IngredientRepository anIngredientRepository;

  @BeforeEach
  void mealsFactoryInitialization() {
    aMealsFactory = new MealsFactory();
    aMenuRepository = mock(InMemoryMenuRepository.class);
    anIngredientRepository = mock(IngredientRepository.class);
    restrictions = new ArrayList<>();
  }

  @Test
  void givenNoRestrictions_whenCreatingCustomerMeal_thenCallsOnlyAppropriateMealFromRepository()
      throws Exception {
    aMealsFactory.createCustomerMeal(restrictions, anIngredientRepository, aMenuRepository);

    verify(aMenuRepository, times(1)).getDefaultMeal();
    verify(aMenuRepository, times(0)).getMealByRestriction(any());
  }

  @Test
  void
      givenVegetarianRestriction_whenCreatingCustomerMeal_thenCallsOnlyAppropriateMealFromRepository()
          throws Exception {
    Restriction vegetarianRestriction = new Restriction(new Money(10), VEGETARIAN);
    restrictions.add(vegetarianRestriction);

    aMealsFactory.createCustomerMeal(restrictions, anIngredientRepository, aMenuRepository);

    verify(aMenuRepository, times(1)).getMealByRestriction(VEGETARIAN);
    verify(aMenuRepository, times(0)).getDefaultMeal();
  }

  @Test
  void givenVeganRestriction_whenCreatingCustomerMeal_thenCallsOnlyAppropriateMealFromRepository()
      throws Exception {
    Restriction veganRestriction = new Restriction(new Money(10), VEGAN);
    restrictions.add(veganRestriction);

    aMealsFactory.createCustomerMeal(restrictions, anIngredientRepository, aMenuRepository);

    verify(aMenuRepository, times(1)).getMealByRestriction(VEGAN);
    verify(aMenuRepository, times(0)).getDefaultMeal();
  }

  @Test
  void
      givenAllergiesRestriction_whenCreatingCustomerMeal_thenCallsOnlyAppropriateMealFromRepository()
          throws Exception {
    Restriction allergiesRestriction = new Restriction(new Money(10), ALLERGIES);
    restrictions.add(allergiesRestriction);

    aMealsFactory.createCustomerMeal(restrictions, anIngredientRepository, aMenuRepository);

    verify(aMenuRepository, times(1)).getMealByRestriction(ALLERGIES);
    verify(aMenuRepository, times(0)).getDefaultMeal();
  }

  @Test
  void givenIllnessRestriction_whenCreatingCustomerMeal_thenCallsOnlyAppropriateMealFromRepository()
      throws Exception {
    Restriction illnessRestriction = new Restriction(new Money(10), ILLNESS);
    restrictions.add(illnessRestriction);

    aMealsFactory.createCustomerMeal(restrictions, anIngredientRepository, aMenuRepository);

    verify(aMenuRepository, times(1)).getMealByRestriction(ILLNESS);
    verify(aMenuRepository, times(0)).getDefaultMeal();
  }

  @Test
  void
      givenMultipleRestrictions_whenCreatingCustomerMeal_thenCallsEveryAppropriateMealsFromRepository()
          throws Exception {
    Restriction illnessRestriction = new Restriction(new Money(10), ILLNESS);
    Restriction allergiesRestriction = new Restriction(new Money(10), ALLERGIES);
    Restriction veganRestriction = new Restriction(new Money(10), VEGAN);
    restrictions.add(illnessRestriction);
    restrictions.add(allergiesRestriction);
    restrictions.add(veganRestriction);

    aMealsFactory.createCustomerMeal(restrictions, anIngredientRepository, aMenuRepository);

    verify(aMenuRepository, times(1)).getMealByRestriction(ILLNESS);
    verify(aMenuRepository, times(1)).getMealByRestriction(ALLERGIES);
    verify(aMenuRepository, times(1)).getMealByRestriction(VEGAN);
    verify(aMenuRepository, times(0)).getDefaultMeal();
  }
}
