package ca.ulaval.glo4002.reservation.reservation.rest.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CustomerRequest {
  private final List<String> restrictions;
  private final String name;

  @JsonCreator
  public CustomerRequest(
      @JsonProperty(value = "name") String name,
      @JsonProperty(value = "restrictions") List<String> restrictions) {
    this.name = name;
    this.restrictions = restrictions;
  }

  public String getName() {
    return this.name;
  }

  public List<String> getRestrictions() {
    return this.restrictions;
  }
}
