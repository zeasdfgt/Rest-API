package ca.ulaval.glo4002.reservation.reservation.rest.assemblers;

import ca.ulaval.glo4002.reservation.meal.domain.Meal;
import ca.ulaval.glo4002.reservation.money.Money;
import ca.ulaval.glo4002.reservation.reservation.domain.*;
import ca.ulaval.glo4002.reservation.reservation.domain.exceptions.InvalidReservationQuantityException;
import ca.ulaval.glo4002.reservation.reservation.domain.exceptions.TooManyPeopleException;
import ca.ulaval.glo4002.reservation.reservation.rest.requests.FromRequest;
import ca.ulaval.glo4002.reservation.reservation.rest.requests.ReservationRequest;
import ca.ulaval.glo4002.reservation.reservation.rest.requests.TableRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

class ReservationRequestAssemblerTest {
  private final String A_CUSTOMER_NAME = "Pailuc";
  private final String ANOTHER_CUSTOMER_NAME = "Jeff";
  private ReservationRequestAssembler reservationRequestAssembler;
  private TableRequestAssembler tableRequestAssembler;
  private ReservationRequest reservationRequest;

  @BeforeEach
  public void reservationRequestAssembler() {
    tableRequestAssembler = mock(TableRequestAssembler.class);
    reservationRequestAssembler = new ReservationRequestAssembler(tableRequestAssembler);
  }

  @Test
  public void whenManyTablesOnAReservationRequest_thenAllTablesAreAddedToTheReservation()
      throws Exception {
    TableRequest aTableRequest = mock(TableRequest.class);
    TableRequest anotherTableRequest = mock(TableRequest.class);
    List<TableRequest> tableRequests = new ArrayList<>();
    tableRequests.add(aTableRequest);
    tableRequests.add(anotherTableRequest);

    createReservationRequest(tableRequests);

    Table anExpectedTable = createATable(A_CUSTOMER_NAME);
    Table anotherExpectedTable = createATable(ANOTHER_CUSTOMER_NAME);

    willReturn(anExpectedTable).given(tableRequestAssembler).fromRequest(aTableRequest);
    willReturn(anotherExpectedTable).given(tableRequestAssembler).fromRequest(anotherTableRequest);

    Reservation reservationResult = reservationRequestAssembler.fromRequest(reservationRequest);

    assertTrue(reservationResult.getTables().contains(anExpectedTable));
    assertTrue(reservationResult.getTables().contains(anotherExpectedTable));
    assertEquals(2, reservationResult.getTables().size());
  }

  private void createReservationRequest(List<TableRequest> tableRequests) {
    long id = 0;
    String aVendorCode = "TEAM";
    String aDinnerDate = "2150-02-01T01:01:01Z[GMT]";
    FromRequest aFromRequest = mock(FromRequest.class);
    BigDecimal aReservationPrice = new BigDecimal(1000);

    reservationRequest =
        new ReservationRequest(
            id, aVendorCode, aDinnerDate, aFromRequest, tableRequests, aReservationPrice);

    String aReservationDate = "2150-01-01T01:01:01Z[GMT]";
    willReturn(aReservationDate).given(reservationRequest.getFrom()).getReservationDate();
  }

  private Table createATable(String customerName)
      throws TooManyPeopleException, InvalidReservationQuantityException {
    List<Meal> aMealList = new ArrayList<>();
    aMealList.add(mock(Meal.class));

    RestrictionType aRestrictionType = RestrictionType.ALLERGIES;
    Restriction aRestriction = new Restriction(mock(Money.class), aRestrictionType);
    List<Restriction> restrictions = new ArrayList<>();
    restrictions.add(aRestriction);

    Customer customer = new Customer(customerName, restrictions, aMealList);
    List<Customer> customers = new ArrayList<>();
    customers.add(customer);

    return new Table(customers);
  }
}
