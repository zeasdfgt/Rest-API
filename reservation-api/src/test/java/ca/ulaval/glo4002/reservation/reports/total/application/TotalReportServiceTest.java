package ca.ulaval.glo4002.reservation.reports.total.application;

import ca.ulaval.glo4002.reservation.reports.exceptions.InvalidReportDateException;
import ca.ulaval.glo4002.reservation.reports.total.domain.TotalReportFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class TotalReportServiceTest {
  private TotalReportService totalReportService;
  private TotalReportFactory totalReportFactory;

  @BeforeEach
  public void totalReportServiceFactoryInitialisation() {
    totalReportFactory = mock(TotalReportFactory.class);
    totalReportService = new TotalReportService(totalReportFactory);
  }

  @Test
  public void whenGeneratingTotalReport_thenUsesTotalReportFactory()
      throws InvalidReportDateException {
    totalReportService.generateTotalReport();

    verify(totalReportFactory).generateTotalReport();
  }
}
