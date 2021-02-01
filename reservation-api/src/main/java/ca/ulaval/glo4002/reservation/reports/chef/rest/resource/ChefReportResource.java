package ca.ulaval.glo4002.reservation.reports.chef.rest.resource;

import ca.ulaval.glo4002.reservation.reports.chef.application.ChefReportService;
import ca.ulaval.glo4002.reservation.reports.chef.domain.DailyChefReport;
import ca.ulaval.glo4002.reservation.reports.chef.rest.responses.ChefReportResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/reports/chefs")
@Produces(MediaType.APPLICATION_JSON)
public class ChefReportResource {
  private final ChefReportService chefReportService;

  public ChefReportResource() {
    this.chefReportService = new ChefReportService();
  }

  public ChefReportResource(ChefReportService chefReportService) {
    this.chefReportService = chefReportService;
  }

  @GET
  public ChefReportResponse getChefReport() {
    List<DailyChefReport> report = chefReportService.getChefReports();
    return new ChefReportResponse(report);
  }
}
