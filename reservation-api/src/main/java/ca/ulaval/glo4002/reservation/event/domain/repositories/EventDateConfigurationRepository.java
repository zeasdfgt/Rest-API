package ca.ulaval.glo4002.reservation.event.domain.repositories;

import ca.ulaval.glo4002.reservation.event.domain.EventDateConfiguration;

public interface EventDateConfigurationRepository {
  EventDateConfiguration getCurrentEventDateConfiguration();

  void saveNewEventDateConfiguration(EventDateConfiguration newEventDateConfiguration);
}
