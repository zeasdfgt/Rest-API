package ca.ulaval.glo4002.reservation.reports.total.rest.factories;

import ca.ulaval.glo4002.reservation.reports.exceptions.InvalidReportDateException;
import ca.ulaval.glo4002.reservation.reports.total.application.TotalReportService;
import ca.ulaval.glo4002.reservation.reports.total.domain.TotalReport;
import ca.ulaval.glo4002.reservation.reports.total.rest.responses.TotalReportResponse;

public class TotalReportResponseAssembler {
  private TotalReportService totalReportService;

  public TotalReportResponseAssembler(TotalReportService totalReportService) {
    this.totalReportService = totalReportService;
  }

  public TotalReportResponse getTotalReportResponse() throws InvalidReportDateException {
    TotalReport totalReport = totalReportService.generateTotalReport();

    return new TotalReportResponse(
        totalReport.getExpense(), totalReport.getIncome(), totalReport.getProfits());
  }
}
