package ca.ulaval.glo4002.reservation.date.domain;

import ca.ulaval.glo4002.reservation.date.domain.exceptions.InvalidDateIntervalException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DateIntervalTest {
  private final Date START_DATE = new Date(2012, 4, 9, 9, 9, 9);
  private final Date END_DATE = new Date(2014, 4, 9, 9, 9, 9);

  @Test
  public void
      givenStartDateAfterEndDate_whenCreating_thenShouldThrowInvalidDateIntervalException() {
    Date aStartDateAfterEndDate = new Date(2014, 4, 9, 9, 9, 9);
    Date anEndDateBeforeStartDate = new Date(2012, 4, 9, 9, 9, 9);

    assertThrows(
        InvalidDateIntervalException.class,
        () -> new DateInterval(aStartDateAfterEndDate, anEndDateBeforeStartDate));
  }

  @Test
  public void givenADateInDateInterval_whenValidatingIfInInterval_thenReturnTrue() {
    Date dateInInterval = new Date(2013, 4, 9, 9, 9, 9);
    DateInterval dateInterval = new DateInterval(START_DATE, END_DATE);

    assertTrue(dateInterval.isWithinInterval(dateInInterval));
  }

  @Test
  public void givenADateNotInDateInterval_whenValidatingIfInInterval_thenReturnFalse() {
    Date dateNotInInterval = new Date(2015, 4, 9, 9, 9, 9);
    DateInterval dateInterval = new DateInterval(START_DATE, END_DATE);

    assertFalse(dateInterval.isWithinInterval(dateNotInInterval));
  }

  @Test
  public void givenTwiceTheSameDate_whenCheckingIfStartDateIsSameAsEndDate_thenReturnsTrue() {
    Date aDate = new Date(2014, 4, 9, 9, 9, 9);
    DateInterval dateInterval = new DateInterval(aDate, aDate);

    assertTrue(dateInterval.startDateSameAsEndDate());
  }

  @Test
  public void
      givenIntervalWithDifferentDates_whenCheckingIfStartDateIsSameAsEndDate_thenReturnsFalse() {
    DateInterval dateInterval = new DateInterval(START_DATE, END_DATE);

    assertFalse(dateInterval.startDateSameAsEndDate());
  }
}
