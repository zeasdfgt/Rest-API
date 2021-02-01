package ca.ulaval.glo4002.reservation.tableware.domain;

import ca.ulaval.glo4002.reservation.rest.exceptions.InvalidFormatException;

public enum TablewareType {
  FORK("fork"),
  SPOON("spoon"),
  KNIFE("knife"),
  BOWL("bowl"),
  PLATE("plate");

  private final String name;

  TablewareType(String name) {
    this.name = name;
  }

  public static TablewareType fromString(String name) throws InvalidFormatException {
    for (TablewareType type : TablewareType.values()) {
      if (type.toString().equals(name)) {
        return type;
      }
    }
    throw new InvalidFormatException();
  }

  @Override
  public String toString() {
    return name;
  }
}
