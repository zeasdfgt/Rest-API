package ca.ulaval.glo4002.reservation.event.rest.requests;

import ca.ulaval.glo4002.reservation.date.domain.Date;
import ca.ulaval.glo4002.reservation.date.domain.DateInterval;
import ca.ulaval.glo4002.reservation.rest.exceptions.InvalidDateException;
import ca.ulaval.glo4002.reservation.rest.exceptions.InvalidFormatException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DatePeriodRequestTest {
  private final String A_BEGIN_DATE = "2150-02-20";
  private final String AN_END_DATE = "2150-02-23";

  @Test
  public void whenTransformingToDateInterval_thenReturnDateIntervalWithRequestDate()
      throws InvalidFormatException, InvalidDateException {
    DatePeriodRequest datePeriodRequest = new DatePeriodRequest(A_BEGIN_DATE, AN_END_DATE);
    DateInterval expectedInterval =
        new DateInterval(Date.parseLocalDate(A_BEGIN_DATE), Date.parseLocalDate(AN_END_DATE));

    DateInterval resultDateInterval = datePeriodRequest.toDateInterval();

    assertEquals(expectedInterval, resultDateInterval);
  }
}
