package ca.ulaval.glo4002.reservation.reservation.domain.exceptions;

public class ReservationNotFoundException extends Exception {
  private String reservationNumber;

  public ReservationNotFoundException(String reservationNumber) {
    this.reservationNumber = reservationNumber;
  }

  public String getReservationNumber() {
    return this.reservationNumber;
  }
}
