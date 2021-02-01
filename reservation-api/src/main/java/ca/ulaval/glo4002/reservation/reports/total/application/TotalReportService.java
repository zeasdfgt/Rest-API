package ca.ulaval.glo4002.reservation.reports.total.application;

import ca.ulaval.glo4002.reservation.reports.exceptions.InvalidReportDateException;
import ca.ulaval.glo4002.reservation.reports.total.domain.TotalReport;
import ca.ulaval.glo4002.reservation.reports.total.domain.TotalReportFactory;

public class TotalReportService {
  TotalReportFactory totalReportFactory;

  public TotalReportService(TotalReportFactory totalReportFactory) {
    this.totalReportFactory = totalReportFactory;
  }

  public TotalReportService() {
    this.totalReportFactory = new TotalReportFactory();
  }

  public TotalReport generateTotalReport() throws InvalidReportDateException {
    return totalReportFactory.generateTotalReport();
  }
}
