package ca.ulaval.glo4002.reservation.reservation.domain.factories;

import ca.ulaval.glo4002.reservation.money.Money;
import ca.ulaval.glo4002.reservation.reservation.domain.Restriction;
import ca.ulaval.glo4002.reservation.reservation.domain.RestrictionType;
import ca.ulaval.glo4002.reservation.rest.exceptions.InvalidFormatException;

public class RestrictionFactory {
  public Restriction createRestriction(RestrictionType restrictionType) {
    return new Restriction(getPrice(restrictionType), restrictionType);
  }

  private Money getPrice(RestrictionType restrictionType) {
    switch (restrictionType) {
      case VEGETARIAN:
        return new Money(500);
      case VEGAN:
        return new Money(1000);
      default:
        return new Money();
    }
  }
}
