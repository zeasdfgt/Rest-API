package ca.ulaval.glo4002.reservation.event.application;

import ca.ulaval.glo4002.reservation.date.domain.DateInterval;
import ca.ulaval.glo4002.reservation.event.domain.EventDateConfiguration;
import ca.ulaval.glo4002.reservation.event.domain.repositories.EventDateConfigurationRepository;
import ca.ulaval.glo4002.reservation.event.infrastructure.persistence.InMemoryEventDateConfigurationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ConfigServiceTest {
  private ConfigService configService;
  private EventDateConfigurationRepository eventDateConfigurationRepository;

  private final EventDateConfiguration AN_EVENT_DATE_CONFIGURATION = new EventDateConfiguration();

  @BeforeEach
  public void configServiceInitialisation() {
    eventDateConfigurationRepository = mock(InMemoryEventDateConfigurationRepository.class);
    configService = new ConfigService(eventDateConfigurationRepository);

    willReturn(AN_EVENT_DATE_CONFIGURATION)
        .given(eventDateConfigurationRepository)
        .getCurrentEventDateConfiguration();
  }

  @Test
  public void
      whenSettingADateConfiguration_thenCallsSaveNewEventDateConfigurationFromEventDateConfigurationRepository() {
    EventDateConfiguration anEventDateConfiguration = mock(EventDateConfiguration.class);
    configService.setDateConfiguration(anEventDateConfiguration);
    verify(eventDateConfigurationRepository)
        .saveNewEventDateConfiguration(anEventDateConfiguration);
  }

  @Test
  public void whenGettingReservationDateInterval_thenUsesTheEventDateConfigurationRepository() {
    configService.getReservationDateInterval();

    verify(eventDateConfigurationRepository).getCurrentEventDateConfiguration();
  }

  @Test
  public void whenGettingDinnerDateInterval_thenUsesTheEventDateConfigurationRepository() {
    configService.getDinnerDateInterval();

    verify(eventDateConfigurationRepository).getCurrentEventDateConfiguration();
  }

  @Test
  public void whenGettingDinnerDateInterval_thenReturnsCurrentReservationDateInterval() {
    DateInterval expectedDinnerInterval = AN_EVENT_DATE_CONFIGURATION.getDinnerDateInterval();

    DateInterval returnedDinnerInterval = configService.getDinnerDateInterval();

    assertEquals(expectedDinnerInterval, returnedDinnerInterval);
  }
}
