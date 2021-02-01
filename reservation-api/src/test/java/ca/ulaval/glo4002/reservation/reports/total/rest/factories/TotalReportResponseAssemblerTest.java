package ca.ulaval.glo4002.reservation.reports.total.rest.factories;

import ca.ulaval.glo4002.reservation.reports.exceptions.InvalidReportDateException;
import ca.ulaval.glo4002.reservation.reports.total.application.TotalReportService;
import ca.ulaval.glo4002.reservation.reports.total.domain.TotalReport;
import ca.ulaval.glo4002.reservation.reports.total.rest.responses.TotalReportResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class TotalReportResponseAssemblerTest {
  private final TotalReport TOTAL_REPORT = new TotalReport(5000, 10000, 5000);

  private TotalReportResponseAssembler totalReportResponseAssembler;
  private TotalReportService totalReportService;

  @BeforeEach
  public void totalReportResponseAssemblerInitialisation() throws InvalidReportDateException {
    totalReportService = mock(TotalReportService.class);
    totalReportResponseAssembler = new TotalReportResponseAssembler(totalReportService);
    willReturn(TOTAL_REPORT).given(totalReportService).generateTotalReport();
  }

  @Test
  public void whenCreatingTotalReportResponse_thenAssignsExpenseCorrectly()
      throws InvalidReportDateException {
    TotalReport totalReport = totalReportService.generateTotalReport();
    double expense = totalReport.getExpense();
    TotalReportResponse totalReportResponse = totalReportResponseAssembler.getTotalReportResponse();

    assertEquals(expense, totalReportResponse.expense);
  }

  @Test
  public void whenCreatingTotalReportResponse_thenAssignsIncomeCorrectly()
      throws InvalidReportDateException {
    TotalReport totalReport = totalReportService.generateTotalReport();
    double income = totalReport.getIncome();
    TotalReportResponse totalReportResponse = totalReportResponseAssembler.getTotalReportResponse();

    assertEquals(income, totalReportResponse.income);
  }

  @Test
  public void whenCreatingTotalReportResponse_thenAssignsProfitsCorrectly()
      throws InvalidReportDateException {
    TotalReport totalReport = totalReportService.generateTotalReport();
    double profits = totalReport.getProfits();
    TotalReportResponse totalReportResponse = totalReportResponseAssembler.getTotalReportResponse();

    assertEquals(profits, totalReportResponse.profits);
  }

  @Test
  public void whenCreatingTotalReportResponse_thenUsesFactoryToGenerateTotalReport()
      throws InvalidReportDateException {
    totalReportResponseAssembler.getTotalReportResponse();

    verify(totalReportService).generateTotalReport();
  }
}
