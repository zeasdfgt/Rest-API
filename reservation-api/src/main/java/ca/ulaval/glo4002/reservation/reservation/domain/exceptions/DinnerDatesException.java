package ca.ulaval.glo4002.reservation.reservation.domain.exceptions;

import ca.ulaval.glo4002.reservation.date.domain.Date;
import ca.ulaval.glo4002.reservation.event.domain.EventDateConfiguration;
import ca.ulaval.glo4002.reservation.event.domain.repositories.EventDateConfigurationRepository;
import ca.ulaval.glo4002.reservation.event.infrastructure.persistence.InMemoryEventDateConfigurationRepository;

public class DinnerDatesException extends Exception {
  private Date eventStartDate;
  private Date eventEndDate;

  public DinnerDatesException() {
    EventDateConfigurationRepository eventDateConfigurationRepository =
        new InMemoryEventDateConfigurationRepository();
    EventDateConfiguration eventConfig =
        eventDateConfigurationRepository.getCurrentEventDateConfiguration();
    this.eventStartDate = eventConfig.getDinnerDateInterval().getStartDate();
    this.eventEndDate = eventConfig.getDinnerDateInterval().getEndDate();
  }

  public Date getEventStartDate() {
    return eventStartDate;
  }

  public Date getEventEndDate() {
    return eventEndDate;
  }
}
