package ca.ulaval.glo4002.reservation.reservation.rest.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TableRequest {
  private final List<CustomerRequest> customers;

  @JsonCreator
  public TableRequest(@JsonProperty(value = "customers") List<CustomerRequest> customers) {
    this.customers = customers;
  }

  public List<CustomerRequest> getCustomers() {
    return this.customers;
  }

  public boolean hasCustomers() {
    return !customers.isEmpty();
  }

  public int getNumberOfCustomers() {
    return customers.size();
  }
}
