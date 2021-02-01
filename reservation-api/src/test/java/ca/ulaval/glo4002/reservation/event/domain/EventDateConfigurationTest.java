package ca.ulaval.glo4002.reservation.event.domain;

import ca.ulaval.glo4002.reservation.date.domain.Date;
import ca.ulaval.glo4002.reservation.date.domain.DateInterval;
import ca.ulaval.glo4002.reservation.date.domain.exceptions.InvalidTimeFrameException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class EventDateConfigurationTest {
  private static final Date A_DATE_TOO_SOON = new Date(2020, 12, 12, 12, 12, 12);
  private static final Date A_FIRST_DATE = new Date(2021, 1, 1, 1, 1, 1);
  private static final Date A_SECOND_DATE = new Date(2021, 2, 1, 1, 1, 1);
  private static final Date A_THIRD_DATE = new Date(2021, 3, 1, 1, 1, 1);
  private static final Date A_FOURTH_DATE = new Date(2021, 4, 1, 1, 1, 1);

  private static final DateInterval AN_INTERVAL_BEFORE_THE_OTHERS =
      new DateInterval(A_FIRST_DATE, A_SECOND_DATE);
  private static final DateInterval AN_INTERVAL_DURING_THE_OTHERS =
      new DateInterval(A_SECOND_DATE, A_THIRD_DATE);
  private static final DateInterval AN_INTERVAL_AFTER_THE_OTHERS =
      new DateInterval(A_THIRD_DATE, A_FOURTH_DATE);

  @Test
  public void whenReservationIntervalIsTooEarly_thenThrowsInvalidTimeFrameException() {
    DateInterval reservationDateIntervalTooSoon = new DateInterval(A_DATE_TOO_SOON, A_FIRST_DATE);
    DateInterval acceptableDinnerDateInterval = new DateInterval(A_THIRD_DATE, A_FOURTH_DATE);
    assertThrows(
        InvalidTimeFrameException.class,
        () ->
            new EventDateConfiguration(
                reservationDateIntervalTooSoon, acceptableDinnerDateInterval));
  }

  @Test
  public void
      whenDinnerIntervalStartsBeforeReservationInterval_thenThrowsInvalidTimeFrameException() {
    assertThrows(
        InvalidTimeFrameException.class,
        () ->
            new EventDateConfiguration(
                AN_INTERVAL_AFTER_THE_OTHERS, AN_INTERVAL_BEFORE_THE_OTHERS));
  }

  @Test
  public void whenDinnerIntervalIsDuringReservationInterval_thenThrowsInvalidTimeFrameException() {
    assertThrows(
        InvalidTimeFrameException.class,
        () ->
            new EventDateConfiguration(
                AN_INTERVAL_BEFORE_THE_OTHERS, AN_INTERVAL_DURING_THE_OTHERS));
  }

  @Test
  public void
      whenReservationIntervalEndsAfterDinnerIntervalBegins_thenThrowsInvalidTimeFrameException() {
    assertThrows(
        InvalidTimeFrameException.class,
        () ->
            new EventDateConfiguration(
                AN_INTERVAL_DURING_THE_OTHERS, AN_INTERVAL_BEFORE_THE_OTHERS));
  }

  @Test
  public void whenReservationIntervalHasSameStartAndEndDate_thenThrowsInvalidTimeFrameException() {
    DateInterval reservationInterval = new DateInterval(A_FIRST_DATE, A_FIRST_DATE);
    DateInterval dinnerInterval = new DateInterval(A_SECOND_DATE, A_THIRD_DATE);
    assertThrows(
        InvalidTimeFrameException.class,
        () -> new EventDateConfiguration(reservationInterval, dinnerInterval));
  }

  @Test
  public void whenDinnerIntervalHasSameStartAndEndDate_thenThrowsInvalidTimeFrameException() {
    DateInterval reservationInterval = new DateInterval(A_FIRST_DATE, A_SECOND_DATE);
    DateInterval dinnerInterval = new DateInterval(A_THIRD_DATE, A_THIRD_DATE);
    assertThrows(
        InvalidTimeFrameException.class,
        () -> new EventDateConfiguration(reservationInterval, dinnerInterval));
  }
}
