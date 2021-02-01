package ca.ulaval.glo4002.reservation.chef;

import ca.ulaval.glo4002.reservation.money.Money;
import ca.ulaval.glo4002.reservation.reservation.domain.RestrictionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChefTest {
  private final Money A_PRICE = new Money(6000);
  private final RestrictionType SOME_SPECIALITY = RestrictionType.ALLERGIES;
  private final RestrictionType ANOTHER_SPECIALITY = RestrictionType.VEGAN;
  private final List<RestrictionType> SOME_SPECIALITIES = new ArrayList<>();
  private Chef someChef;

  @BeforeEach
  public void chefInitialization() {
    SOME_SPECIALITIES.add(RestrictionType.ALLERGIES);
    SOME_SPECIALITIES.add(RestrictionType.ILLNESS);
    this.someChef = new Chef("Gordon Ramsey", A_PRICE, SOME_SPECIALITIES);
  }

  @Test
  void
      givenAChefWithACertainSpeciality_whenCheckingIfChefHasTheCertainSpeciality_thenReturnsTrue() {
    assertTrue(this.someChef.hasThisSpeciality(this.SOME_SPECIALITY));
  }

  @Test
  void
      givenAChefWithoutACertainSpeciality_whenCheckingIfChefHasTheCertainSpeciality_thenReturnsFalse() {
    assertFalse(this.someChef.hasThisSpeciality(this.ANOTHER_SPECIALITY));
  }

  @Test
  void givenTwoEqualChefs_whenCheckingIfEquals_thenReturnsTrue() {
    SOME_SPECIALITIES.add(RestrictionType.ALLERGIES);
    SOME_SPECIALITIES.add(RestrictionType.ILLNESS);
    Chef equalChef = new Chef("Gordon Ramsey", A_PRICE, SOME_SPECIALITIES);

    assertTrue(someChef.equals(equalChef));
  }

  @Test
  void givenTwoChefsWithDifferentNames_whenCheckingIfEquals_thenReturnsFalse() {
    Chef differentNameChef = new Chef("Gorsey Ramdon", A_PRICE, SOME_SPECIALITIES);

    assertFalse(someChef.equals(differentNameChef));
  }

  @Test
  void givenTwoChefsWithDifferentPrices_whenCheckingIfEquals_thenReturnsFalse() {
    Money newPrice = A_PRICE.add(A_PRICE);
    Chef differentPriceChef = new Chef("Gordon Ramsey", newPrice, SOME_SPECIALITIES);

    assertFalse(someChef.equals(differentPriceChef));
  }

  @Test
  void givenTwoChefsWithDifferentSpecialities_whenCheckingIfEquals_thenReturnsFalse() {
    List<RestrictionType> differentSpecialities = new ArrayList<>();
    differentSpecialities.add(RestrictionType.NONE);
    Chef differentSpecialitiesChef = new Chef("Gordon Ramsey", A_PRICE, differentSpecialities);

    assertFalse(someChef.equals(differentSpecialitiesChef));
  }
}
