package ca.ulaval.glo4002.reservation.reservation.rest.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public class ReservationRequest {
  private final long id;
  private final String vendorCode;
  private final List<TableRequest> tables;
  private final BigDecimal price;
  private final String dinnerDate;
  private final FromRequest from;

  @JsonCreator
  public ReservationRequest(
      @JsonProperty(value = "id") long id,
      @JsonProperty(value = "vendorCode") String vendorCode,
      @JsonProperty(value = "dinnerDate") String dinnerDate,
      @JsonProperty(value = "from") FromRequest from,
      @JsonProperty(value = "tables") List<TableRequest> tables,
      @JsonProperty(value = "reservationPrice") BigDecimal price) {
    this.id = id;
    this.vendorCode = vendorCode;
    this.from = from;
    this.dinnerDate = dinnerDate;
    this.tables = tables;
    this.price = price;
  }

  public String getVendorCode() {
    return this.vendorCode;
  }

  public String getDinnerDate() {
    return this.dinnerDate;
  }

  public FromRequest getFrom() {
    return this.from;
  }

  public List<TableRequest> getTables() {
    return this.tables;
  }

  public boolean hasTables() {
    return !this.tables.isEmpty();
  }

  public int getNumberOfCustomers() {
    int numberOfCustomers = 0;
    for (TableRequest tableRequest : tables) {
      numberOfCustomers += tableRequest.getNumberOfCustomers();
    }
    return numberOfCustomers;
  }

  public BigDecimal getPrice() {
    return this.price;
  }
}
