package ca.ulaval.glo4002.reservation.reports.material.rest.resources;

import ca.ulaval.glo4002.reservation.date.domain.Date;
import ca.ulaval.glo4002.reservation.date.domain.DateInterval;
import ca.ulaval.glo4002.reservation.event.domain.Event;
import ca.ulaval.glo4002.reservation.reports.exceptions.InvalidReportDateException;
import ca.ulaval.glo4002.reservation.reports.material.rest.factories.MaterialReportResponseFactory;
import ca.ulaval.glo4002.reservation.reports.material.rest.responses.MaterialReportResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/reports/material")
@Produces(MediaType.APPLICATION_JSON)
public class MaterialReportResource {
  private final Event event;
  private final MaterialReportResponseFactory materialReportResponseFactory;

  public MaterialReportResource(
      Event event, MaterialReportResponseFactory materialReportResponseFactory) {
    this.event = event;
    this.materialReportResponseFactory = materialReportResponseFactory;
  }

  public MaterialReportResource() {
    this.event = new Event();
    this.materialReportResponseFactory = new MaterialReportResponseFactory();
  }

  @GET
  public MaterialReportResponse getMaterialReport(
      @QueryParam("startDate") String startDateString, @QueryParam("endDate") String endDateString)
      throws InvalidReportDateException {
    DateInterval interval;
    try {
      Date startDate = Date.parseLocalDate(startDateString);
      Date endDate = Date.parseLocalDate(endDateString);
      interval = new DateInterval(startDate, endDate);
    } catch (Exception exception) {
      throw new InvalidReportDateException();
    }

    return materialReportResponseFactory.generateMaterialReport(event, interval);
  }
}
