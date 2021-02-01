package ca.ulaval.glo4002.reservation.reservation.rest.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FromRequest {
  private final CountryRequest country;
  private String reservationDate;

  @JsonCreator
  public FromRequest(
      @JsonProperty(value = "country") CountryRequest country,
      @JsonProperty(value = "reservationDate") String reservationDate) {
    this.country = country;
    this.reservationDate = reservationDate;
  }

  public String getReservationDate() {
    return this.reservationDate;
  }

  public CountryRequest getCountry() {
    return this.country;
  }
}
