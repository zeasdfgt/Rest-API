package ca.ulaval.glo4002.reservation.reservation.domain;

import ca.ulaval.glo4002.reservation.rest.exceptions.InvalidFormatException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RestrictionTypeTest {
  private final String AN_INVALID_ENUM_STRING = "Je mange du gazon";
  private final String A_VALID_ENUM_STRING = "vegan";

  @Test
  public void
      givenAnInvalidEnumString_whenGettingValueFromString_thenThrowsInvalidFormatException() {

    assertThrows(
        InvalidFormatException.class, () -> RestrictionType.getEnum(AN_INVALID_ENUM_STRING));
  }

  @Test
  public void givenAnValidEnumString_whenGettingValueFromString_thenReturnsCorrespondingType()
      throws InvalidFormatException {
    RestrictionType returnedType = RestrictionType.getEnum(A_VALID_ENUM_STRING);
    RestrictionType expectedType = RestrictionType.VEGAN;
    assertEquals(expectedType, returnedType);
  }
}
