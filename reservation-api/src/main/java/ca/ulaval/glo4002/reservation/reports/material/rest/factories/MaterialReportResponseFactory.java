package ca.ulaval.glo4002.reservation.reports.material.rest.factories;

import ca.ulaval.glo4002.reservation.date.domain.Date;
import ca.ulaval.glo4002.reservation.date.domain.DateInterval;
import ca.ulaval.glo4002.reservation.event.domain.Event;
import ca.ulaval.glo4002.reservation.reports.exceptions.InvalidReportDateException;
import ca.ulaval.glo4002.reservation.reports.material.application.MaterialReportService;
import ca.ulaval.glo4002.reservation.reports.material.domain.MaterialReport;
import ca.ulaval.glo4002.reservation.reports.material.rest.responses.DateMaterialResponse;
import ca.ulaval.glo4002.reservation.reports.material.rest.responses.MaterialReportResponse;

import java.util.ArrayList;

public class MaterialReportResponseFactory {
  private DateMaterialResponseFactory dateMaterialResponseFactory;
  private MaterialReportService materialReportService;

  public MaterialReportResponseFactory() {
    dateMaterialResponseFactory = new DateMaterialResponseFactory();
    materialReportService = new MaterialReportService();
  }

  public MaterialReportResponseFactory(
      DateMaterialResponseFactory dateMaterialResponseFactory,
      MaterialReportService materialReportService) {
    this.dateMaterialResponseFactory = dateMaterialResponseFactory;
    this.materialReportService = materialReportService;
  }

  public MaterialReportResponse generateMaterialReport(Event event, DateInterval dateInterval)
      throws InvalidReportDateException {
    ArrayList<DateMaterialResponse> dateMaterialResponses = new ArrayList<>();

    for (Date date = dateInterval.getStartDate();
        dateInterval.isWithinInterval(date);
        date = date.plusDays(1)) {
      MaterialReport materialReport = materialReportService.generateMaterialReport(date);

      if (materialReport.getToWash().numberOfTableware() > 0
          || materialReport.getToBuy().numberOfTableware() > 0) {
        dateMaterialResponses.add(
            dateMaterialResponseFactory.generateDateMaterialResponse(date, materialReport));
      }
    }

    return new MaterialReportResponse(dateMaterialResponses);
  }
}
