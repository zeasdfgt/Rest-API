package ca.ulaval.glo4002.reservation.event.rest.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ConfigRequest {
  public DatePeriodRequest reservationPeriod;
  public DatePeriodRequest hoppeningPeriod;

  @JsonCreator
  public ConfigRequest(
      @JsonProperty(value = "reservationPeriod") DatePeriodRequest reservationPeriod,
      @JsonProperty(value = "hoppening") DatePeriodRequest hoppeningPeriod) {
    this.reservationPeriod = reservationPeriod;
    this.hoppeningPeriod = hoppeningPeriod;
  }

  public DatePeriodRequest getReservationPeriod() {
    return reservationPeriod;
  }

  public DatePeriodRequest getHoppeningPeriod() {
    return hoppeningPeriod;
  }
}
