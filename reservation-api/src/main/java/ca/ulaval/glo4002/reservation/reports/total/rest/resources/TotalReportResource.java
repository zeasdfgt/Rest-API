package ca.ulaval.glo4002.reservation.reports.total.rest.resources;

import ca.ulaval.glo4002.reservation.event.application.ConfigService;
import ca.ulaval.glo4002.reservation.event.domain.Event;
import ca.ulaval.glo4002.reservation.reports.chef.application.ChefReportService;
import ca.ulaval.glo4002.reservation.reports.exceptions.InvalidReportDateException;
import ca.ulaval.glo4002.reservation.reports.ingredient.application.IngredientReportService;
import ca.ulaval.glo4002.reservation.reports.material.application.MaterialReportService;
import ca.ulaval.glo4002.reservation.reports.total.application.TotalReportService;
import ca.ulaval.glo4002.reservation.reports.total.domain.TotalReportFactory;
import ca.ulaval.glo4002.reservation.reports.total.rest.factories.TotalReportResponseAssembler;
import ca.ulaval.glo4002.reservation.reports.total.rest.responses.TotalReportResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/reports/total")
@Produces(MediaType.APPLICATION_JSON)
public class TotalReportResource {
  private final Event event;
  private final ConfigService configService;
  private final TotalReportFactory totalReportFactory;
  private final TotalReportResponseAssembler totalReportResponseAssembler;
  private final MaterialReportService materialReportService;
  private final IngredientReportService ingredientReportService;
  private final ChefReportService chefReportService;
  private final TotalReportService totalReportService;

  public TotalReportResource() {
    this.event = new Event();
    this.configService = new ConfigService();
    this.materialReportService = new MaterialReportService();
    this.ingredientReportService = new IngredientReportService();
    this.chefReportService = new ChefReportService();
    this.totalReportFactory =
        new TotalReportFactory(
            event,
            configService,
            materialReportService,
            ingredientReportService,
            chefReportService);
    this.totalReportService = new TotalReportService(totalReportFactory);
    this.totalReportResponseAssembler = new TotalReportResponseAssembler(totalReportService);
  }

  public TotalReportResource(
      Event event,
      ConfigService configService,
      TotalReportFactory totalReportFactory,
      TotalReportResponseAssembler totalReportResponseAssembler,
      MaterialReportService materialReportService,
      IngredientReportService ingredientReportService,
      ChefReportService chefReportService,
      TotalReportService totalReportService) {
    this.event = event;
    this.configService = configService;
    this.totalReportFactory = totalReportFactory;
    this.totalReportResponseAssembler = totalReportResponseAssembler;
    this.materialReportService = materialReportService;
    this.ingredientReportService = ingredientReportService;
    this.chefReportService = chefReportService;
    this.totalReportService = totalReportService;
  }

  @GET
  public TotalReportResponse getTotalReport() throws InvalidReportDateException {
    return totalReportResponseAssembler.getTotalReportResponse();
  }
}
