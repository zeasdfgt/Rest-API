package ca.ulaval.glo4002.reservation.event.rest.requests;

import ca.ulaval.glo4002.reservation.date.domain.Date;
import ca.ulaval.glo4002.reservation.date.domain.DateInterval;
import ca.ulaval.glo4002.reservation.rest.exceptions.InvalidDateException;
import ca.ulaval.glo4002.reservation.rest.exceptions.InvalidFormatException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DatePeriodRequest {
  public String beginDate;
  public String endDate;

  @JsonCreator
  public DatePeriodRequest(
      @JsonProperty(value = "beginDate") String beginDate,
      @JsonProperty(value = "endDate") String endDate) {
    this.beginDate = beginDate;
    this.endDate = endDate;
  }

  public String getBeginDate() {
    return beginDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public DateInterval toDateInterval() throws InvalidFormatException, InvalidDateException {
    try {
      Date beginDate = Date.parseLocalDate(this.beginDate);
      Date endDate = Date.parseLocalDate(this.endDate);
      return new DateInterval(beginDate, endDate);
    } catch (InvalidFormatException invalidFormatException) {
      throw new InvalidDateException();
    }
  }
}
