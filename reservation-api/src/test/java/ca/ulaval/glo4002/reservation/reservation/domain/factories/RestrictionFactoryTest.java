package ca.ulaval.glo4002.reservation.reservation.domain.factories;

import ca.ulaval.glo4002.reservation.reservation.domain.Restriction;
import ca.ulaval.glo4002.reservation.reservation.domain.RestrictionType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RestrictionFactoryTest {
  @Test
  public void
      givenARestrictionType_whenCreatingRestriction_thenRestrictionIsCreatedWithCorrespondingType() {
    RestrictionFactory restrictionFactory = new RestrictionFactory();
    RestrictionType aRestrictionType = RestrictionType.ALLERGIES;

    Restriction createdRestriction = restrictionFactory.createRestriction(aRestrictionType);

    assertEquals(aRestrictionType, createdRestriction.getType());
  }
}
