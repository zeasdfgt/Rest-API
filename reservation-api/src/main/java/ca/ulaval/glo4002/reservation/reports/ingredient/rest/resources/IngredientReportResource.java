package ca.ulaval.glo4002.reservation.reports.ingredient.rest.resources;

import ca.ulaval.glo4002.reservation.date.domain.Date;
import ca.ulaval.glo4002.reservation.date.domain.DateInterval;
import ca.ulaval.glo4002.reservation.reports.exceptions.InvalidReportDateException;
import ca.ulaval.glo4002.reservation.reports.exceptions.InvalidReportTypeException;
import ca.ulaval.glo4002.reservation.reports.ingredient.application.IngredientReportService;
import ca.ulaval.glo4002.reservation.reports.ingredient.domain.IngredientReportType;
import ca.ulaval.glo4002.reservation.reports.ingredient.rest.factories.IngredientReportResponseFactory;
import ca.ulaval.glo4002.reservation.reports.ingredient.rest.responses.IngredientReportResponse;
import ca.ulaval.glo4002.reservation.reports.ingredient.rest.validators.IngredientReportRequestValidator;
import ca.ulaval.glo4002.reservation.rest.exceptions.InvalidFormatException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/reports/ingredients")
@Produces(MediaType.APPLICATION_JSON)
public class IngredientReportResource {
  private final IngredientReportService ingredientReportService;
  private final IngredientReportRequestValidator ingredientReportRequestValidator;
  private final IngredientReportResponseFactory ingredientReportResponseFactory;

  public IngredientReportResource() {
    this.ingredientReportService = new IngredientReportService();
    this.ingredientReportRequestValidator = new IngredientReportRequestValidator();
    this.ingredientReportResponseFactory =
        new IngredientReportResponseFactory(ingredientReportService);
  }

  public IngredientReportResource(
      IngredientReportService ingredientReportService,
      IngredientReportRequestValidator ingredientReportRequestValidator,
      IngredientReportResponseFactory ingredientReportResponseFactory) {
    this.ingredientReportService = ingredientReportService;
    this.ingredientReportRequestValidator = ingredientReportRequestValidator;
    this.ingredientReportResponseFactory = ingredientReportResponseFactory;
  }

  @GET
  public IngredientReportResponse getIngredientReport(
      @QueryParam("startDate") String startDateString,
      @QueryParam("endDate") String endDateString,
      @QueryParam("type") String typeString)
      throws InvalidReportDateException, InvalidReportTypeException, InvalidFormatException {
    ingredientReportRequestValidator.validateIngredientReport(
        startDateString, endDateString, typeString);

    Date startDate = Date.parseLocalDate(startDateString);
    Date endDate = Date.parseLocalDate(endDateString);
    DateInterval dateInterval = new DateInterval(startDate, endDate);
    IngredientReportType type = IngredientReportType.getEnum(typeString);

    return ingredientReportResponseFactory.generateReport(dateInterval, type);
  }
}
