package ca.ulaval.glo4002.reservation.reservation.infrastructure.persistence;

import ca.ulaval.glo4002.reservation.date.domain.Date;
import ca.ulaval.glo4002.reservation.reservation.domain.Reservation;
import ca.ulaval.glo4002.reservation.reservation.domain.ReservationNumber;
import ca.ulaval.glo4002.reservation.reservation.domain.exceptions.ReservationNotFoundException;
import ca.ulaval.glo4002.reservation.reservation.domain.repositories.ReservationRepository;

import java.time.LocalDate;
import java.util.*;

public class InMemoryReservationRepository implements ReservationRepository {
  private static Hashtable<ReservationNumber, Reservation> reservations;

  public InMemoryReservationRepository() {
    if (reservations == null) {
      reservations = new Hashtable<>();
    }
  }

  @Override
  public Reservation getReservationByReservationNumber(ReservationNumber reservationNumber)
      throws ReservationNotFoundException {
    Reservation reservation = reservations.get(reservationNumber);
    if (reservation == null) {
      throw new ReservationNotFoundException(reservationNumber.toString());
    }

    return reservation;
  }

  public List<Reservation> getAllReservations() {
    return new ArrayList<>(reservations.values());
  }

  @Override
  public List<Reservation> getReservationsByDinnerDate(Date date) {
    List<Reservation> reservationsOfDate = new ArrayList<>();
    for (Reservation reservation : reservations.values()) {
      if (reservation.isSameDay(date)) {
        reservationsOfDate.add(reservation);
      }
    }
    return reservationsOfDate;
  }

  @Override
  public List<Reservation> getReservationsByDinnerDate(Date fromDate, Date toDate) {
    List<Reservation> reservationsOfDate = new ArrayList<>();
    for (Reservation reservation : reservations.values()) {
      if (reservation.isDuringInterval(fromDate, toDate)) {
        reservationsOfDate.add(reservation);
      }
    }
    return reservationsOfDate;
  }

  @Override
  public void addReservation(Reservation reservation) {
    reservations.put(reservation.getReservationNumber(), reservation);
  }

  @Override
  public Map<LocalDate, List<Reservation>> getAllReservationsGroupedByDate() {
    Map<LocalDate, List<Reservation>> reservationByDate = new HashMap<>();
    for (Reservation reservation : reservations.values()) {
      if (reservationByDate.containsKey(reservation.getDinnerDate().toLocalDate())) {
        reservationByDate.get(reservation.getDinnerDate().toLocalDate()).add(reservation);
      } else {
        List<Reservation> newReservationList = new ArrayList<>();
        newReservationList.add(reservation);
        reservationByDate.put(reservation.getDinnerDate().toLocalDate(), newReservationList);
      }
    }

    return reservationByDate;
  }
}
