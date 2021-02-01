package ca.ulaval.glo4002.reservation.event.infrastructure.persistence;

import ca.ulaval.glo4002.reservation.event.domain.EventDateConfiguration;
import ca.ulaval.glo4002.reservation.event.domain.repositories.EventDateConfigurationRepository;

public class InMemoryEventDateConfigurationRepository implements EventDateConfigurationRepository {
  private static EventDateConfiguration eventDateConfiguration;

  public InMemoryEventDateConfigurationRepository() {
    if (InMemoryEventDateConfigurationRepository.eventDateConfiguration == null) {
      InMemoryEventDateConfigurationRepository.eventDateConfiguration =
          new EventDateConfiguration();
    }
  }

  @Override
  public EventDateConfiguration getCurrentEventDateConfiguration() {
    return InMemoryEventDateConfigurationRepository.eventDateConfiguration;
  }

  @Override
  public void saveNewEventDateConfiguration(EventDateConfiguration newEventDateConfiguration) {
    InMemoryEventDateConfigurationRepository.eventDateConfiguration = newEventDateConfiguration;
  }
}
