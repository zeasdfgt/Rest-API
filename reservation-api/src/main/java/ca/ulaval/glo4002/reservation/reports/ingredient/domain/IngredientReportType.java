package ca.ulaval.glo4002.reservation.reports.ingredient.domain;

import ca.ulaval.glo4002.reservation.reports.exceptions.InvalidReportTypeException;

public enum IngredientReportType {
  TOTAL("total"),
  UNIT("unit");

  private final String ingredientReportType;

  IngredientReportType(String restrictionType) {
    this.ingredientReportType = restrictionType;
  }

  @Override
  public String toString() {
    return this.ingredientReportType;
  }

  public static IngredientReportType getEnum(String type) throws InvalidReportTypeException {
    for (IngredientReportType value : values()) {
      if (value.toString().equals(type)) return value;
    }
    throw new InvalidReportTypeException();
  }
}
