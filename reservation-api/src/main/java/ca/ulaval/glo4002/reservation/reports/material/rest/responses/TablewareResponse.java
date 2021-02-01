package ca.ulaval.glo4002.reservation.reports.material.rest.responses;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TablewareResponse {
  private final @JsonProperty(value = "knife") int knife;
  private final @JsonProperty(value = "fork") int fork;
  private final @JsonProperty(value = "spoon") int spoon;
  private final @JsonProperty(value = "bowl") int bowl;
  private final @JsonProperty(value = "plate") int plate;

  @JsonCreator
  public TablewareResponse(int knife, int fork, int spoon, int bowl, int plate) {
    this.knife = knife;
    this.fork = fork;
    this.spoon = spoon;
    this.bowl = bowl;
    this.plate = plate;
  }
}
