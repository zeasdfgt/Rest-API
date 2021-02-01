package ca.ulaval.glo4002.reservation.reservation.rest.responses;

import ca.ulaval.glo4002.reservation.reservation.domain.Customer;
import ca.ulaval.glo4002.reservation.reservation.domain.Restriction;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomerResponse {
  public final String name;
  public final List<RestrictionResponse> restrictions = new ArrayList<>();

  @JsonCreator
  public CustomerResponse(Customer customer) {
    this.name = customer.getName();
    for (Restriction restriction : customer.getRestrictions()) {
      this.restrictions.add(new RestrictionResponse(restriction));
    }

    Collections.sort(restrictions);
  }
}
