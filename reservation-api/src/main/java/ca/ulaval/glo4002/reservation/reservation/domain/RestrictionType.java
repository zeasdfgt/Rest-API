package ca.ulaval.glo4002.reservation.reservation.domain;

import ca.ulaval.glo4002.reservation.rest.exceptions.InvalidFormatException;

public enum RestrictionType {
  VEGETARIAN("vegetarian"),
  VEGAN("vegan"),
  ALLERGIES("allergies"),
  ILLNESS("illness"),
  NONE("none");

  private final String restrictionType;

  RestrictionType(String restrictionType) {
    this.restrictionType = restrictionType;
  }

  @Override
  public String toString() {
    return restrictionType;
  }

  public static RestrictionType getEnum(String name) throws InvalidFormatException {
    for (RestrictionType value : values()) {
      if (value.toString().equals(name)) return value;
    }
    throw new InvalidFormatException();
  }
}
