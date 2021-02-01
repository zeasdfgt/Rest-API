package ca.ulaval.glo4002.reservation.chef.application;

import ca.ulaval.glo4002.reservation.chef.infrastructure.persistence.InMemoryChefRepository;
import ca.ulaval.glo4002.reservation.chef.repositories.ChefRepository;
import ca.ulaval.glo4002.reservation.event.domain.Event;
import ca.ulaval.glo4002.reservation.reports.chef.application.ChefReportService;
import ca.ulaval.glo4002.reservation.reports.chef.domain.ChefAssignator;
import ca.ulaval.glo4002.reservation.reports.chef.domain.factories.DailyChefReportFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ChefReportServiceTest {
  private ChefReportService chefReportService;
  private DailyChefReportFactory dailyChefReportFactory;
  private Event event;
  private ChefAssignator chefAssignator;
  private ChefRepository chefRepository;

  @BeforeEach
  public void chefReportServiceInitialisation() {
    dailyChefReportFactory = mock(DailyChefReportFactory.class);
    event = mock(Event.class);
    chefAssignator = mock(ChefAssignator.class);
    chefRepository = mock(InMemoryChefRepository.class);
    chefReportService =
        new ChefReportService(chefRepository, dailyChefReportFactory, event, chefAssignator);
  }

  @Test
  public void whenGettingChefReports_thenCallsGenerateChefReportsFromDailyChefReportFactory() {
    chefReportService.getChefReports();

    verify(dailyChefReportFactory).generateChefReports(any(), any());
  }
}
