package ca.ulaval.glo4002.reservation.event.domain;

import ca.ulaval.glo4002.reservation.date.domain.Date;
import ca.ulaval.glo4002.reservation.date.domain.DateInterval;
import ca.ulaval.glo4002.reservation.date.domain.exceptions.InvalidTimeFrameException;

public class EventDateConfiguration {
  private final Date DEFAULT_DINNER_START_DATE = Date.startOfDay(2150, 7, 20);
  private final Date DEFAULT_DINNER_END_DATE = Date.endOfDay(2150, 7, 30);
  private final Date DEFAULT_RESERVATION_START_DATE = Date.startOfDay(2150, 1, 1);
  private final Date DEFAULT_RESERVATION_END_DATE = Date.endOfDay(2150, 7, 16);
  private final Date MINIMUM_RESERVATION_START_DATE = Date.startOfDay(2021, 1, 1);

  private DateInterval dinnerDateInterval;
  private DateInterval reservationDateInterval;

  public EventDateConfiguration() {
    dinnerDateInterval = new DateInterval(DEFAULT_DINNER_START_DATE, DEFAULT_DINNER_END_DATE);
    reservationDateInterval =
        new DateInterval(DEFAULT_RESERVATION_START_DATE, DEFAULT_RESERVATION_END_DATE);
  }

  public EventDateConfiguration(
      DateInterval reservationDateInterval, DateInterval dinnerDateInterval)
      throws InvalidTimeFrameException {

    validateNewConfig(reservationDateInterval, dinnerDateInterval);
    this.reservationDateInterval = reservationDateInterval;
    this.dinnerDateInterval = dinnerDateInterval;
  }

  public DateInterval getDinnerDateInterval() {
    return dinnerDateInterval;
  }

  public DateInterval getReservationDateInterval() {
    return reservationDateInterval;
  }

  private void validateNewConfig(
      DateInterval reservationDateInterval, DateInterval dinnerDateInterval)
      throws InvalidTimeFrameException {

    boolean reservationIsAfterDinner =
        reservationDateInterval.getEndDate().isAfter(dinnerDateInterval.getStartDate());
    boolean reservationIsTooSoon =
        reservationDateInterval.getStartDate().isBefore(MINIMUM_RESERVATION_START_DATE);
    boolean reservationAndDinnerAreOnSameDay =
        reservationDateInterval.getEndDate().isSameDay(dinnerDateInterval.getStartDate());

    if (reservationIsTooSoon
        || reservationIsAfterDinner
        || reservationAndDinnerAreOnSameDay
        || reservationDateInterval.startDateSameAsEndDate()
        || dinnerDateInterval.startDateSameAsEndDate()) {
      throw new InvalidTimeFrameException();
    }
  }
}
