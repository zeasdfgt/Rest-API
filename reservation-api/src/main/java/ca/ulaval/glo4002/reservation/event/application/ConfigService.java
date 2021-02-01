package ca.ulaval.glo4002.reservation.event.application;

import ca.ulaval.glo4002.reservation.date.domain.DateInterval;
import ca.ulaval.glo4002.reservation.event.domain.EventDateConfiguration;
import ca.ulaval.glo4002.reservation.event.domain.repositories.EventDateConfigurationRepository;
import ca.ulaval.glo4002.reservation.event.infrastructure.persistence.InMemoryEventDateConfigurationRepository;

public class ConfigService {
  private final EventDateConfigurationRepository eventDateConfigurationRepository;

  public ConfigService() {
    this.eventDateConfigurationRepository = new InMemoryEventDateConfigurationRepository();
  }

  public ConfigService(EventDateConfigurationRepository eventDateConfigurationRepository) {
    this.eventDateConfigurationRepository = eventDateConfigurationRepository;
  }

  public void setDateConfiguration(EventDateConfiguration eventDateConfiguration) {
    eventDateConfigurationRepository.saveNewEventDateConfiguration(eventDateConfiguration);
  }

  public DateInterval getReservationDateInterval() {
    return eventDateConfigurationRepository
        .getCurrentEventDateConfiguration()
        .getReservationDateInterval();
  }

  public DateInterval getDinnerDateInterval() {
    return eventDateConfigurationRepository
        .getCurrentEventDateConfiguration()
        .getDinnerDateInterval();
  }
}
