package ca.ulaval.glo4002.reservation.reservation.rest.responses;

import ca.ulaval.glo4002.reservation.reservation.domain.Customer;
import ca.ulaval.glo4002.reservation.reservation.domain.Reservation;
import ca.ulaval.glo4002.reservation.reservation.domain.Table;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationResponse {
  public final BigDecimal reservationPrice;
  public final ZonedDateTime dinnerDate;
  public final List<CustomerResponse> customers = new ArrayList<>();

  @JsonCreator
  public ReservationResponse(Reservation reservation) {
    this.reservationPrice = reservation.getTotalPriceAsRoundedBigDecimal();
    this.dinnerDate = reservation.getDinnerDateAsZonedDateTime();
    for (Table table : reservation.getTables()) {
      for (Customer customer : table.getCustomers()) {
        this.customers.add(new CustomerResponse(customer));
      }
    }
  }
}
