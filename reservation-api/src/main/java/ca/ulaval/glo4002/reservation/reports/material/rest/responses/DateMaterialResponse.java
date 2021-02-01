package ca.ulaval.glo4002.reservation.reports.material.rest.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DateMaterialResponse {
  public final @JsonProperty(value = "date") LocalDate date;
  public final @JsonProperty(value = "cleaned") TablewareResponse cleanedResponse;
  public final @JsonProperty(value = "bought") TablewareResponse boughtResponse;
  public final @JsonProperty(value = "totalPrice") BigDecimal totalPrice;

  public DateMaterialResponse(
      LocalDate date,
      TablewareResponse cleanedResponse,
      TablewareResponse boughtResponse,
      BigDecimal totalPrice) {
    this.date = date;
    this.cleanedResponse = cleanedResponse;
    this.boughtResponse = boughtResponse;
    this.totalPrice = totalPrice;
  }
}
