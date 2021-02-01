package ca.ulaval.glo4002.reservation.event.domain;

import ca.ulaval.glo4002.reservation.reservation.domain.Customer;
import ca.ulaval.glo4002.reservation.reservation.domain.Reservation;
import ca.ulaval.glo4002.reservation.tableware.domain.TablewareFactory;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.*;

class EventDayTest {

  @Test
  public void whenGettingNumberOfCustomers_thenReturnTotalOfCustomersInAllDailyReservations() {
    List<Reservation> reservations = new ArrayList<>();
    Reservation aReservation = mock(Reservation.class);
    int anAmountOfCustomers = 3;
    willReturn(anAmountOfCustomers).given(aReservation).getNumberOfCustomers();
    Reservation anotherReservation = mock(Reservation.class);
    int anotherAmountOfCustomers = 2;
    willReturn(anotherAmountOfCustomers).given(anotherReservation).getNumberOfCustomers();
    reservations.add(aReservation);
    reservations.add(anotherReservation);
    int totalAmountOfCustomers = anAmountOfCustomers + anotherAmountOfCustomers;
    EventDay eventDay = new EventDay(reservations);

    assertEquals(totalAmountOfCustomers, eventDay.getNumberOfCustomersForDay());
  }

  @Test
  public void
      givenEventDayWithCustomers_whenGettingRequiredTableware_thenTablewareIsCreatedFromTablewareFactory() {
    List<Customer> customers = new ArrayList<>();
    Customer customer = mock(Customer.class);
    customers.add(customer);
    List<Reservation> reservations = new ArrayList<>();
    Reservation aReservation = mock(Reservation.class);
    willReturn(customers).given(aReservation).getCustomers();
    reservations.add(aReservation);
    TablewareFactory tablewareFactory = mock(TablewareFactory.class);
    EventDay eventDay = new EventDay(reservations, tablewareFactory);

    eventDay.getRequiredTableware();

    verify(tablewareFactory).createTablewareForCustomer(any());
  }

  @Test
  public void
      givenEventDayWithNoCustomer_whenGettingRequiredTableware_thenNoCallToTablewareFactory() {
    TablewareFactory tablewareFactory = mock(TablewareFactory.class);
    EventDay eventDay = new EventDay(new ArrayList<>(), tablewareFactory);

    eventDay.getRequiredTableware();

    verify(tablewareFactory, never()).createTablewareForCustomer(any());
  }
}
