package ca.ulaval.glo4002.reservation.date.domain;

import ca.ulaval.glo4002.reservation.rest.exceptions.InvalidFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DateTest {
  private Date aDate;
  private final int A_MONTH = 12;
  private final int A_DAY = 10;
  private final int A_YEAR = 2150;
  private final int AN_HOUR = 11;
  private final int A_MINUTE = 22;
  private final int A_SECOND = 55;

  @BeforeEach
  public void dateInitialization() {
    this.aDate = new Date(A_YEAR, A_MONTH, A_DAY, AN_HOUR, A_MINUTE, A_SECOND);
  }

  @Test
  public void whenParsingAnEmptyStringAsDate_thenThrowsInvalidFormatException() {
    String anEmptyString = "";

    assertThrows(InvalidFormatException.class, () -> Date.parseZonedDateTime(anEmptyString));
  }

  @Test
  public void whenParsingAnInvalidStringAsDate_thenThrowsInvalidFormatException() {
    String anInvalidDate = "invalid_date";

    assertThrows(InvalidFormatException.class, () -> Date.parseZonedDateTime(anInvalidDate));
  }

  @Test
  public void whenParsingAValidStringAsDate_thenReturnsADate() throws InvalidFormatException {
    String aValidDate = ZonedDateTime.now().toString();

    assertEquals(Date.class, Date.parseZonedDateTime(aValidDate).getClass());
  }

  @Test
  public void whenParsingAnInvalidStringAsLocalDate_thenThrowsInvalidFormatException() {
    String anInvalidLocalDate = "invalidLocalDate";

    assertThrows(InvalidFormatException.class, () -> Date.parseLocalDate(anInvalidLocalDate));
  }

  @Test
  public void whenParsingAnValidStringAsDate_thenReturnsADate() throws InvalidFormatException {
    String aValidDate = LocalDate.now().toString();

    assertEquals(Date.class, Date.parseLocalDate(aValidDate).getClass());
  }

  @Test
  public void givenOtherDateWithDifferentYear_whenAskingIfSameDay_thenReturnsFalse() {
    int anotherYear = A_YEAR - 1;
    Date otherDate = new Date(anotherYear, A_MONTH, A_DAY, AN_HOUR, A_MINUTE, A_SECOND);

    assertFalse(aDate.isSameDay(otherDate));
  }

  @Test
  public void givenOtherDateWithDifferentDay_whenAskingIfSameDay_thenReturnsFalse() {
    int anotherDay = A_DAY - 3;
    Date otherDate = new Date(A_YEAR, A_MONTH, anotherDay, AN_HOUR, A_MINUTE, A_SECOND);

    assertFalse(aDate.isSameDay(otherDate));
  }

  @Test
  public void givenOtherDateWithDifferentMonth_whenAskingIfSameDay_thenReturnsFalse() {
    int anotherMonth = A_MONTH - 3;
    Date otherDate = new Date(A_YEAR, anotherMonth, A_DAY, AN_HOUR, A_MINUTE, A_SECOND);

    assertFalse(aDate.isSameDay(otherDate));
  }

  @Test
  public void givenSameDate_whenAskingIfSameDay_thenReturnsTrue() {
    Date otherDate = new Date(A_YEAR, A_MONTH, A_DAY, AN_HOUR, A_MINUTE, A_SECOND);

    assertTrue(aDate.isSameDay(otherDate));
  }

  @Test
  public void givenSameDateAsIntervalStartDate_whenAskingIfDateSameDayInterval_thenReturnsTrue() {
    Date fromDate = new Date(A_YEAR, A_MONTH, A_DAY, AN_HOUR, A_MINUTE, A_SECOND);
    Date toDate = this.aLaterDay();

    assertTrue(aDate.isDuringDayInterval(fromDate, toDate));
  }

  @Test
  public void givenSameDateAsIntervalEndDate_whenAskingIfDateSameDayInterval_thenReturnsTrue() {
    Date fromDate = this.aPriorDate();
    Date toDate = new Date(A_YEAR, A_MONTH, A_DAY, AN_HOUR, A_MINUTE, A_SECOND);

    assertTrue(aDate.isDuringDayInterval(fromDate, toDate));
  }

  @Test
  public void givenDateInInterval_whenAskingIfDateSameDayInterval_thenReturnsTrue() {
    Date fromDate = this.aPriorDate();
    Date toDate = this.aLaterDay();

    assertTrue(aDate.isDuringDayInterval(fromDate, toDate));
  }

  @Test
  public void whenGettingStartOfDay_thenReturnsDateSetToStartOfDay() {
    Date startOfDay = aDate.startOfDay();
    ZonedDateTime returnedDate = startOfDay.toZonedDateTime();

    ZonedDateTime expectedDate =
        ZonedDateTime.of(A_YEAR, A_MONTH, A_DAY, 0, 0, 0, 0, ZoneId.of("GMT"));

    assertEquals(expectedDate, returnedDate);
  }

  @Test
  public void whenGettingEndOfDay_thenReturnsDateSetToEndOfDay() {
    Date endOfDay = aDate.endOfDay();
    ZonedDateTime returnedDate = endOfDay.toZonedDateTime();

    ZonedDateTime expectedDate =
        ZonedDateTime.of(A_YEAR, A_MONTH, A_DAY, 23, 59, 59, 999999999, ZoneId.of("GMT"));

    assertEquals(expectedDate, returnedDate);
  }

  @Test
  public void whenFormatting_thenReturnsCorrectlyFormattedString() {
    String aFormatPattern = "yyyy-MM-dd";
    String returnedFormattedStringDate = aDate.format(aFormatPattern);

    String expectFormattedStringDate = "2150-12-10";

    assertEquals(expectFormattedStringDate, returnedFormattedStringDate);
  }

  @Test
  public void whenComparingADateToSameDate_thenReturnsZero() {
    Date priorDate = new Date(A_YEAR, A_MONTH, A_DAY, AN_HOUR, A_MINUTE, A_SECOND);

    int comparison = aDate.compareTo(priorDate);

    assert (comparison == 0);
  }

  @Test
  public void whenComparingADateToAPriorDate_thenReturnsPositiveValue() {
    int aPriorDay = A_DAY - 3;
    Date priorDate = new Date(A_YEAR, A_MONTH, aPriorDay, AN_HOUR, A_MINUTE, A_SECOND);

    int comparison = aDate.compareTo(priorDate);

    assert (comparison > 0);
  }

  @Test
  public void whenComparingADateToAnLaterDate_thenReturnsNegativeValue() {
    int anAnteriorDay = A_DAY + 3;
    Date anAnteriorDate = new Date(A_YEAR, A_MONTH, anAnteriorDay, AN_HOUR, A_MINUTE, A_SECOND);

    int comparison = aDate.compareTo(anAnteriorDate);

    assert (comparison < 0);
  }

  private Date aPriorDate() {
    int aPriorDay = A_DAY - 3;
    return new Date(A_YEAR, A_MONTH, aPriorDay, AN_HOUR, A_MINUTE, A_SECOND);
  }

  private Date aLaterDay() {
    int aLaterDay = A_DAY + 2;
    return new Date(A_YEAR, A_MONTH, aLaterDay, AN_HOUR, A_MINUTE, A_SECOND);
  }
}
