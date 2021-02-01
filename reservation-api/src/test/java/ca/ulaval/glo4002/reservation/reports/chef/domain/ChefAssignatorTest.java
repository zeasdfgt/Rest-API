package ca.ulaval.glo4002.reservation.reports.chef.domain;

import ca.ulaval.glo4002.reservation.chef.Chef;
import ca.ulaval.glo4002.reservation.chef.infrastructure.persistence.InMemoryChefRepository;
import ca.ulaval.glo4002.reservation.chef.repositories.ChefRepository;
import ca.ulaval.glo4002.reservation.money.Money;
import ca.ulaval.glo4002.reservation.reports.chef.domain.exceptions.NoPossibleChefException;
import ca.ulaval.glo4002.reservation.reservation.domain.Restriction;
import ca.ulaval.glo4002.reservation.reservation.domain.RestrictionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ChefAssignatorTest {
  private final Money A_PRICE = new Money(2222);
  private final ChefRepository chefRepository = new InMemoryChefRepository();
  private ChefAssignator chefAssignator;
  private Set<Chef> appropriateChefForTheDay;
  private final List<Restriction> restrictionsForTheDay = new ArrayList<>();

  @BeforeEach
  public void chefAssignatorInitialization() {
    chefAssignator = new ChefAssignator(chefRepository.getAllChefs());

    restrictionsForTheDay.add(new Restriction(A_PRICE, RestrictionType.ALLERGIES));
    restrictionsForTheDay.add(new Restriction(A_PRICE, RestrictionType.ILLNESS));

    appropriateChefForTheDay = new HashSet<>();
    appropriateChefForTheDay.add(
        chefRepository.getChefsBySpeciality(RestrictionType.ALLERGIES).get(0));
    appropriateChefForTheDay.add(
        chefRepository.getChefsBySpeciality(RestrictionType.ILLNESS).get(0));
  }

  @Test
  public void
      givenAListOfRestrictions_whenGettingTheChefToCookTheseRestrictions_thenReturnsTheAppropriateChefForTheseRestriction() {
    Set<Chef> chefForTheDay = chefAssignator.getChefsForDay(restrictionsForTheDay);

    assertEquals(appropriateChefForTheDay, chefForTheDay);
  }

  @Test
  public void
      givenAnImpossibleListOfRestrictions_whenCheckingIfChefSelectionIsPossible_thenThrowsNoPossibleChefException() {
    List<Restriction> restrictionForValidation = new ArrayList<>();
    int maxValueForVegetarian = 11;
    for (int i = 0; i < maxValueForVegetarian; i++) {
      restrictionForValidation.add(new Restriction(A_PRICE, RestrictionType.VEGETARIAN));
    }

    assertThrows(
        NoPossibleChefException.class,
        () -> {
          chefAssignator.validateChefsForTheDay(restrictionForValidation);
        });
  }

  @Test
  public void
      givenAListOfRestrictions_whenCalculatingTotalPriceOfChefForTheChef_thenReturnsAppropriatePrice() {
    Money appropriatePrice = new Money(12000);

    Money returnedPrice = chefAssignator.priceOfChefsForTheDay(restrictionsForTheDay);

    assertEquals(appropriatePrice, returnedPrice);
  }
}
