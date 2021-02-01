package ca.ulaval.glo4002.reservation.event.domain;

import ca.ulaval.glo4002.reservation.chef.repositories.ChefRepository;
import ca.ulaval.glo4002.reservation.date.domain.Date;
import ca.ulaval.glo4002.reservation.date.domain.DateInterval;
import ca.ulaval.glo4002.reservation.event.application.ConfigService;
import ca.ulaval.glo4002.reservation.reports.chef.domain.ChefAssignator;
import ca.ulaval.glo4002.reservation.reservation.domain.Reservation;
import ca.ulaval.glo4002.reservation.reservation.domain.ReservationNumber;
import ca.ulaval.glo4002.reservation.reservation.domain.exceptions.InvalidDinnerDateException;
import ca.ulaval.glo4002.reservation.reservation.domain.exceptions.InvalidReservationDateException;
import ca.ulaval.glo4002.reservation.reservation.domain.exceptions.ReservationNotFoundException;
import ca.ulaval.glo4002.reservation.reservation.domain.exceptions.TomatoDateConflictException;
import ca.ulaval.glo4002.reservation.reservation.domain.repositories.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class EventTest {
  private final int DAYS_FOR_TOMATO_BAN = 5;
  private final String TOMATO = "Tomato";

  private final EventDateConfiguration AN_EVENT_DATE_CONFIGURATION = new EventDateConfiguration();
  private final DateInterval RESERVATION_DATE_INTERVAL =
      AN_EVENT_DATE_CONFIGURATION.getReservationDateInterval();
  private final DateInterval DINNER_DATE_INTERVAL =
      AN_EVENT_DATE_CONFIGURATION.getDinnerDateInterval();

  private final Date A_VALID_RESERVATION_DATE = RESERVATION_DATE_INTERVAL.getEndDate();
  private final Date A_RESERVATION_DATE_TOO_SOON =
      RESERVATION_DATE_INTERVAL.getStartDate().minusDays(1);
  private final Date A_RESERVATION_DATE_TOO_LATE =
      RESERVATION_DATE_INTERVAL.getEndDate().plusDays(1);

  private final Date A_VALID_DINNER_DATE = DINNER_DATE_INTERVAL.getEndDate();
  private final Date A_DINNER_DATE_TOO_SOON = DINNER_DATE_INTERVAL.getStartDate().minusDays(1);
  private final Date A_DINNER_DATE_TOO_LATE = DINNER_DATE_INTERVAL.getEndDate().plusDays(1);
  private final Date A_VALID_TOMATO_DINNER_DATE =
      DINNER_DATE_INTERVAL.getStartDate().plusDays(DAYS_FOR_TOMATO_BAN);
  private final Date AN_INVALID_TOMATO_DINNER_DATE =
      DINNER_DATE_INTERVAL.getStartDate().plusDays(DAYS_FOR_TOMATO_BAN - 1);

  private final ReservationNumber AN_ID = ReservationNumber.parseFromString("POUETTE-123123");

  private ReservationRepository reservationRepository;
  private ChefRepository chefRepository;
  private Reservation reservation;
  private ChefAssignator chefAssignator;
  private Event event;
  private ConfigService configService;

  EventTest() throws ReservationNotFoundException {}

  @BeforeEach
  public void eventInitialization() {
    reservationRepository = mock(ReservationRepository.class);
    reservation = mock(Reservation.class);
    configService = mock(ConfigService.class);

    chefRepository = mock(ChefRepository.class);
    chefAssignator = mock(ChefAssignator.class);

    willReturn(DINNER_DATE_INTERVAL).given(configService).getDinnerDateInterval();
    willReturn(RESERVATION_DATE_INTERVAL).given(configService).getReservationDateInterval();

    event = new Event(reservationRepository, chefRepository, chefAssignator, configService);
  }

  @Test
  public void whenFindingAReservationInEvent_thenSearchesInRepository()
      throws ReservationNotFoundException {
    event.findReservation(AN_ID);

    verify(reservationRepository).getReservationByReservationNumber(AN_ID);
  }

  @Test
  public void
      givenValidReservationAndDinnerDates_whenCreatingAReservation_thenCallsAddReservationFromReservationRepository()
          throws Exception {
    setAReservationWithReservationAndDinnerDates(A_VALID_RESERVATION_DATE, A_VALID_DINNER_DATE);
    event.bookReservation(reservation);
    verify(reservationRepository).addReservation(reservation);
  }

  @Test
  public void
      givenValidReservationAndDinnerDates_whenValidatingTheReservationDate_ThenDoesNotThrowAnException() {
    setAReservationWithReservationAndDinnerDates(A_VALID_RESERVATION_DATE, A_VALID_DINNER_DATE);
    assertDoesNotThrow(() -> event.bookReservation(reservation));
  }

  @Test
  public void
      givenAReservationDateTooSoon_whenValidatingTheReservationDate_thenThrowsInvalidReservationDateException() {
    setAReservationWithReservationAndDinnerDates(A_RESERVATION_DATE_TOO_SOON, A_VALID_DINNER_DATE);
    assertThrows(InvalidReservationDateException.class, () -> event.bookReservation(reservation));
  }

  @Test
  public void
      givenAReservationDateTooLate_whenValidatingTheReservationDate_thenThrowsInvalidReservationDateException() {
    setAReservationWithReservationAndDinnerDates(A_RESERVATION_DATE_TOO_LATE, A_VALID_DINNER_DATE);
    assertThrows(InvalidReservationDateException.class, () -> event.bookReservation(reservation));
  }

  @Test
  public void
      givenADinnerDateTooSoon_whenValidatingTheDinnerDate_thenThrowsInvalidDinnerDateException() {
    setAReservationWithReservationAndDinnerDates(A_VALID_RESERVATION_DATE, A_DINNER_DATE_TOO_SOON);
    assertThrows(InvalidDinnerDateException.class, () -> event.bookReservation(reservation));
  }

  @Test
  public void
      givenADinnerDateTooLate_whenValidatingTheDinnerDate_thenThrowsInvalidDinnerDateException() {
    setAReservationWithReservationAndDinnerDates(A_VALID_RESERVATION_DATE, A_DINNER_DATE_TOO_LATE);
    assertThrows(InvalidDinnerDateException.class, () -> event.bookReservation(reservation));
  }

  @Test
  public void
      givenADinnerDateAfterTomatoBanThreshold_whenValidatingTomatoAvailability_thenDoesNotThrowAnException() {
    setAReservationWithReservationAndDinnerDates(
        A_VALID_RESERVATION_DATE, A_VALID_TOMATO_DINNER_DATE);
    willReturn(true).given(reservation).ingredientInAMeal(TOMATO);

    assertDoesNotThrow(() -> event.bookReservation(reservation));
  }

  @Test
  public void
      givenADinnerDateBeforeTomatoBanThreshold_whenValidatingTomatoAvailability_thenThrowsATomatoDateConflictException() {
    setAReservationWithReservationAndDinnerDates(
        A_VALID_RESERVATION_DATE, AN_INVALID_TOMATO_DINNER_DATE);
    willReturn(true).given(reservation).ingredientInAMeal(TOMATO);

    assertThrows(TomatoDateConflictException.class, () -> event.bookReservation(reservation));
  }

  private void setAReservationWithReservationAndDinnerDates(
      Date aReservationDate, Date aDinnerDate) {
    willReturn(aReservationDate).given(reservation).getReservationDate();
    willReturn(aDinnerDate).given(reservation).getDinnerDate();
  }
}
