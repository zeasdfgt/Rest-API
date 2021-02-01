package ca.ulaval.glo4002.reservation.reservation.rest.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CountryRequest {
  private final String code;
  private final String fullname;
  private final String currency;

  @JsonCreator
  public CountryRequest(
      @JsonProperty(value = "code") String code,
      @JsonProperty(value = "fullname") String fullname,
      @JsonProperty(value = "currency") String currency) {
    this.code = code;
    this.fullname = fullname;
    this.currency = currency;
  }

  public String getCode() {
    return this.code;
  }

  public String getFullName() {
    return this.fullname;
  }

  public String getCurrency() {
    return this.currency;
  }
}
