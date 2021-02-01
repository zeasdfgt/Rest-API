package ca.ulaval.glo4002.reservation.reports.ingredient.domain;

import ca.ulaval.glo4002.reservation.reports.exceptions.InvalidReportTypeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IngredientReportTypeTest {
  @Test
  public void givenAValidEnumStringType_whenGettingTypeEnum_thenReturnsExpectedTypeEnum()
      throws InvalidReportTypeException {
    String aValidTypeEnumString = IngredientReportType.TOTAL.toString();
    IngredientReportType expectedTypeEnum = IngredientReportType.TOTAL;

    IngredientReportType result = IngredientReportType.getEnum(aValidTypeEnumString);

    assertEquals(expectedTypeEnum, result);
  }

  @Test
  public void
      givenAInvalidUpperCaseEnumStringType_whenGettingTypeEnum_thenThrowsInvalidReportTypeException() {
    String aValidTypeEnumString = IngredientReportType.TOTAL.toString().toUpperCase();

    assertThrows(
        InvalidReportTypeException.class, () -> IngredientReportType.getEnum(aValidTypeEnumString));
  }

  @Test
  public void
      givenAnEmptyEnumStringType_whenGettingValueFromString_thenThrowsInvalidReportTypeException() {
    String anEmptyStringType = "";

    assertThrows(
        InvalidReportTypeException.class, () -> IngredientReportType.getEnum(anEmptyStringType));
  }

  @Test
  public void
      givenAnInvalidEnumStringType_whenGettingTypeEnum_thenThrowsInvalidReportTypeException() {
    String anInvalidTypeEnumString = String.valueOf(Integer.MAX_VALUE);

    assertThrows(
        InvalidReportTypeException.class,
        () -> IngredientReportType.getEnum(anInvalidTypeEnumString));
  }
}
