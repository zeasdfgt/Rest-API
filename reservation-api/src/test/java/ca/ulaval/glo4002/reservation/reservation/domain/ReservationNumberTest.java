package ca.ulaval.glo4002.reservation.reservation.domain;

import ca.ulaval.glo4002.reservation.reservation.domain.exceptions.ReservationNotFoundException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ReservationNumberTest {
  String A_RESERVATION_NUMBER_VALUE = "432";
  String TEAM_NAME = "YO";
  String A_RESERVATION_NUMBER = "YO-420";

  @Test
  public void givenTwoReservationNumberWithSameNumber_whenComparingThem_thenReturnsTrue()
      throws ReservationNotFoundException {
    ReservationNumber aReservationNumber = ReservationNumber.parseFromString(A_RESERVATION_NUMBER);
    ReservationNumber anotherReservationNumber =
        ReservationNumber.parseFromString(A_RESERVATION_NUMBER);

    assertEquals(aReservationNumber, anotherReservationNumber);
  }

  @Test
  public void whenComparingReservationNumberWithOtherClass_thenReturnsFalse() {
    ReservationNumber aReservationNumber = new ReservationNumber(TEAM_NAME);
    BigDecimal anOtherClass = new BigDecimal(0);

    boolean comparisonResult = aReservationNumber.equals(anOtherClass);

    assertFalse(comparisonResult);
  }

  @Test
  public void whenCreatingReservationNumbers_thenReservationNumbersAreDifferent() {
    ReservationNumber firstReservationNumber = new ReservationNumber(TEAM_NAME);
    ReservationNumber secondReservationNumber = new ReservationNumber(TEAM_NAME);

    assertNotEquals(firstReservationNumber, secondReservationNumber);
  }

  @Test
  public void givenTwoReservationNumbersWithSameValue_whenComparingEachOther_thenReturnsTrue()
      throws ReservationNotFoundException {
    HashMap<ReservationNumber, String> aMap = new HashMap<>();
    ReservationNumber aReservationNumber = ReservationNumber.parseFromString(A_RESERVATION_NUMBER);
    ReservationNumber anOtherReservationNumber =
        ReservationNumber.parseFromString(A_RESERVATION_NUMBER);
    String aString = "A String";

    aMap.put(aReservationNumber, aString);
    String theSameString = aMap.get(anOtherReservationNumber);

    assertEquals(theSameString, aString);
  }
}
