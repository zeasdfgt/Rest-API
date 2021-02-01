package ca.ulaval.glo4002.reservation.reservation.domain.repositories;

import ca.ulaval.glo4002.reservation.date.domain.Date;
import ca.ulaval.glo4002.reservation.reservation.domain.Reservation;
import ca.ulaval.glo4002.reservation.reservation.domain.ReservationNumber;
import ca.ulaval.glo4002.reservation.reservation.domain.exceptions.ReservationNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ReservationRepository {
  Reservation getReservationByReservationNumber(ReservationNumber reservationNumber)
      throws ReservationNotFoundException;

  List<Reservation> getReservationsByDinnerDate(Date date);

  List<Reservation> getReservationsByDinnerDate(Date fromDate, Date toDate);

  List<Reservation> getAllReservations();

  Map<LocalDate, List<Reservation>> getAllReservationsGroupedByDate();

  void addReservation(Reservation reservation) throws ReservationNotFoundException;
}
