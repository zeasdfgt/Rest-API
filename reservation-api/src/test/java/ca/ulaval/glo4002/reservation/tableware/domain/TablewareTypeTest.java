package ca.ulaval.glo4002.reservation.tableware.domain;

import ca.ulaval.glo4002.reservation.rest.exceptions.InvalidFormatException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TablewareTypeTest {
  private final String A_VALID_ENUM_STRING = "fork";
  private final String AN_INVALID_ENUM_STRING = "Trump bad";

  @Test
  public void
      givenAValidEnumString_whenGettingEnumFromString_thenReturnsCorrespondingTablewareType()
          throws InvalidFormatException {
    TablewareType returnedType = TablewareType.fromString(A_VALID_ENUM_STRING);
    TablewareType expectedType = TablewareType.FORK;
    assertEquals(expectedType, returnedType);
  }

  @Test
  public void givenAInvalidEnumString_whenGettingEnumFromString_thenThrowsRuntimeException() {
    assertThrows(
        InvalidFormatException.class, () -> TablewareType.fromString(AN_INVALID_ENUM_STRING));
  }
}
