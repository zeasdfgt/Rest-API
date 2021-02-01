package ca.ulaval.glo4002.reservation.reports.ingredient.rest.validators;

import ca.ulaval.glo4002.reservation.reports.ingredient.domain.IngredientReportType;
import ca.ulaval.glo4002.reservation.reports.exceptions.InvalidReportDateException;
import ca.ulaval.glo4002.reservation.reports.exceptions.InvalidReportTypeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IngredientReportRequestValidatorTest {
  private final String A_VALID_START_DATE = "2150-07-20";
  private final String A_VALID_END_DATE = "2150-07-30";
  private final String AN_INVALID_DATE = "Diplodocus";
  private final String AN_INVALID_TYPE = String.valueOf(Integer.MAX_VALUE);
  private final String AN_EMPTY_STRING = "";
  private final String A_VALID_TYPE = IngredientReportType.values()[0].toString();
  private IngredientReportRequestValidator ingredientReportRequestValidator;

  @BeforeEach
  public void ingredientReportRequestValidatorInitialization() {
    ingredientReportRequestValidator = new IngredientReportRequestValidator();
  }

  @Test
  public void whenStartDateIsAnInvalidString_thenThrowsInvalidDateException() {
    assertThrows(
        InvalidReportDateException.class,
        () ->
            ingredientReportRequestValidator.validateIngredientReport(
                AN_INVALID_DATE, A_VALID_END_DATE, A_VALID_TYPE));
  }

  @Test
  public void whenStartDateIsAnEmptyString_thenThrowsInvalidDateException() {
    assertThrows(
        InvalidReportDateException.class,
        () ->
            ingredientReportRequestValidator.validateIngredientReport(
                AN_EMPTY_STRING, A_VALID_END_DATE, A_VALID_TYPE));
  }

  @Test
  public void whenEndDateIsAnInvalidString_thenThrowsInvalidDateException() {
    assertThrows(
        InvalidReportDateException.class,
        () ->
            ingredientReportRequestValidator.validateIngredientReport(
                A_VALID_START_DATE, AN_INVALID_DATE, A_VALID_TYPE));
  }

  @Test
  public void whenEndDateIsAnEmptyString_thenThrowsInvalidDateException() {
    assertThrows(
        InvalidReportDateException.class,
        () ->
            ingredientReportRequestValidator.validateIngredientReport(
                A_VALID_START_DATE, AN_EMPTY_STRING, A_VALID_TYPE));
  }

  @Test
  public void whenTypeIsAnEmptyString_thenThrowsInvalidTypeException() {
    assertThrows(
        InvalidReportTypeException.class,
        () ->
            ingredientReportRequestValidator.validateIngredientReport(
                A_VALID_START_DATE, A_VALID_END_DATE, AN_EMPTY_STRING));
  }

  @Test
  public void whenTypeIsInvalid_thenThrowsInvalidTypeException() {
    assertThrows(
        InvalidReportTypeException.class,
        () ->
            ingredientReportRequestValidator.validateIngredientReport(
                A_VALID_START_DATE, A_VALID_END_DATE, AN_INVALID_TYPE));
  }

  @Test
  public void whenValidStartAndEndDatesAndType_thenDoesNotThrowAnyException() {
    assertDoesNotThrow(
        () ->
            ingredientReportRequestValidator.validateIngredientReport(
                A_VALID_START_DATE, A_VALID_END_DATE, A_VALID_TYPE));
  }
}
