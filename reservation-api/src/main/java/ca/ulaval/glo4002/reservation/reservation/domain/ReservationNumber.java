package ca.ulaval.glo4002.reservation.reservation.domain;

import ca.ulaval.glo4002.reservation.reservation.domain.exceptions.ReservationNotFoundException;

public class ReservationNumber {
  private static long nextId = 0;
  private final String id;

  public ReservationNumber(String team) {
    id = team + "-" + nextId;
    nextId = nextId + 1;
  }

  public String toString() {
    return id;
  }

  public static ReservationNumber parseFromString(String id) throws ReservationNotFoundException {
    if (validateId(id)) {
      String[] parts = id.split("-");
      return new ReservationNumber(parts[0], Long.parseLong(parts[1]));
    } else {
      throw new ReservationNotFoundException(id);
    }
  }

  private static boolean validateId(String id) {
    if (!id.contains("-")) {
      return false;
    } else {
      String[] parts = id.split("-");
      return parts[1].matches("[0-9]+");
    }
  }

  @Override
  public boolean equals(Object object) {
    if (getClass() != object.getClass()) {
      return false;
    }

    ReservationNumber otherReservationNumber = (ReservationNumber) object;
    return toString().equals(otherReservationNumber.toString());
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  private ReservationNumber(String team, long number) {
    id = team + "-" + number;
  }
}
