package ca.ulaval.glo4002.reservation.reservation.domain.exceptions;

import ca.ulaval.glo4002.reservation.date.domain.Date;
import ca.ulaval.glo4002.reservation.event.domain.EventDateConfiguration;
import ca.ulaval.glo4002.reservation.event.domain.repositories.EventDateConfigurationRepository;
import ca.ulaval.glo4002.reservation.event.infrastructure.persistence.InMemoryEventDateConfigurationRepository;

public class ReservationDatesException extends Exception {
  private Date reservationStartDate;
  private Date reservationEndDate;

  public ReservationDatesException() {
    EventDateConfigurationRepository eventDateConfigurationRepository =
        new InMemoryEventDateConfigurationRepository();
    EventDateConfiguration eventConfig =
        eventDateConfigurationRepository.getCurrentEventDateConfiguration();
    this.reservationStartDate = eventConfig.getReservationDateInterval().getStartDate();
    this.reservationEndDate = eventConfig.getReservationDateInterval().getEndDate();
  }

  public Date getEventStartDate() {
    return reservationStartDate;
  }

  public Date getEventEndDate() {
    return reservationEndDate;
  }
}
