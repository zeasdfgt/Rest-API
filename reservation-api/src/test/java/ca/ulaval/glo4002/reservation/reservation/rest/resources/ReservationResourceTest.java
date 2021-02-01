package ca.ulaval.glo4002.reservation.reservation.rest.resources;

import ca.ulaval.glo4002.reservation.event.domain.Event;
import ca.ulaval.glo4002.reservation.reservation.domain.Reservation;
import ca.ulaval.glo4002.reservation.reservation.domain.ReservationNumber;
import ca.ulaval.glo4002.reservation.reservation.rest.assemblers.ReservationRequestAssembler;
import ca.ulaval.glo4002.reservation.reservation.rest.requests.ReservationRequest;
import ca.ulaval.glo4002.reservation.reservation.rest.validators.ReservationRequestValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ReservationResourceTest {
  private final ReservationNumber A_RESERVATION_NUMBER = new ReservationNumber("564");
  private ReservationResource resource;
  private Event event;
  private ReservationRequestValidator validator;
  private ReservationRequestAssembler reservationRequestAssembler;
  private ReservationRequest reservationRequest;

  @BeforeEach
  public void resourceInitialization() throws Exception {
    event = mock(Event.class);
    validator = mock(ReservationRequestValidator.class);
    reservationRequestAssembler = mock(ReservationRequestAssembler.class);
    resource = new ReservationResource(event, validator, reservationRequestAssembler);
    reservationRequest = mock(ReservationRequest.class);

    willReturn(mock(Reservation.class)).given(event).findReservation(A_RESERVATION_NUMBER);

    resource = new ReservationResource(event, validator, reservationRequestAssembler);
  }

  @Test
  public void whenGettingRequestOnReservationWithId_thenRetrievesReservationWithCorrespondingID()
      throws Exception {
    resource.getReservationById(A_RESERVATION_NUMBER.toString());

    verify(event).findReservation(A_RESERVATION_NUMBER);
  }

  @Test
  public void whenCreatingAReservation_thenValidatesRequest() throws Exception {
    resource.createReservation(reservationRequest);

    verify(validator).validateReservationRequest(reservationRequest);
  }
}
