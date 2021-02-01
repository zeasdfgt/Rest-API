package ca.ulaval.glo4002.reservation.reservation.infrastructure.persistence;

import ca.ulaval.glo4002.reservation.reservation.domain.Reservation;
import ca.ulaval.glo4002.reservation.reservation.domain.ReservationNumber;
import ca.ulaval.glo4002.reservation.reservation.domain.exceptions.ReservationNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

class InMemoryReservationRepositoryTest {
  private final String A_RESERVATION_NUMBER_VALUE = "PATATE-123";
  private final ReservationNumber A_RESERVATION_NUMBER =
      ReservationNumber.parseFromString(A_RESERVATION_NUMBER_VALUE);
  private final ReservationNumber ANOTHER_RESERVATION_NUMBER =
      ReservationNumber.parseFromString("PATATE-654");
  private InMemoryReservationRepository inMemoryReservationRepository;
  private Reservation aReservation;

  InMemoryReservationRepositoryTest() throws ReservationNotFoundException {}

  @BeforeEach
  public void inMemoryReservationRepositoryInitialization() {
    inMemoryReservationRepository = new InMemoryReservationRepository();
    aReservation = mock(Reservation.class);
    willReturn(A_RESERVATION_NUMBER).given(aReservation).getReservationNumber();

    inMemoryReservationRepository.addReservation(aReservation);
  }

  @Test
  public void
      givenARepositoryWithAReservation_whenSearchingByID_thenReturnsCorrespondingReservation()
          throws ReservationNotFoundException {
    ReservationNumber aNewReservationNumber =
        ReservationNumber.parseFromString(A_RESERVATION_NUMBER_VALUE);
    Reservation resultReservation =
        inMemoryReservationRepository.getReservationByReservationNumber(aNewReservationNumber);

    assertEquals(aReservation, resultReservation);
  }

  @Test
  public void givenAnEmptyRepository_whenSearchingByID_thenThrowsException() {
    ReservationNumber aReservationNumberNotInRepository = new ReservationNumber("420");

    assertThrows(
        ReservationNotFoundException.class,
        () ->
            inMemoryReservationRepository.getReservationByReservationNumber(
                aReservationNumberNotInRepository));
  }

  @Test
  public void givenAReservation_whenAddingToRepository_thenContainsGivenReservation()
      throws ReservationNotFoundException {
    Reservation anotherReservation = mock(Reservation.class);
    willReturn(ANOTHER_RESERVATION_NUMBER).given(anotherReservation).getReservationNumber();

    inMemoryReservationRepository.addReservation(anotherReservation);
    Reservation resultReservation =
        inMemoryReservationRepository.getReservationByReservationNumber(ANOTHER_RESERVATION_NUMBER);

    assertEquals(anotherReservation, resultReservation);
  }

  @Test
  public void
      givenReservationsInTheRepository_whenGettingAllReservations_thenReturnedListContainsAllReservations() {
    Reservation anotherReservation = mock(Reservation.class);
    willReturn(ANOTHER_RESERVATION_NUMBER).given(anotherReservation).getReservationNumber();
    inMemoryReservationRepository.addReservation(anotherReservation);

    List<Reservation> reservations = inMemoryReservationRepository.getAllReservations();

    assertTrue(reservations.contains(aReservation));
    assertTrue(reservations.contains(anotherReservation));
  }
}
