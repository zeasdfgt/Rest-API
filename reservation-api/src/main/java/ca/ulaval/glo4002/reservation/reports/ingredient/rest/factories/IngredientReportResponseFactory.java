package ca.ulaval.glo4002.reservation.reports.ingredient.rest.factories;

import ca.ulaval.glo4002.reservation.date.domain.DateInterval;
import ca.ulaval.glo4002.reservation.reports.exceptions.InvalidReportDateException;
import ca.ulaval.glo4002.reservation.reports.exceptions.InvalidReportTypeException;
import ca.ulaval.glo4002.reservation.reports.ingredient.application.IngredientReportService;
import ca.ulaval.glo4002.reservation.reports.ingredient.domain.IngredientReportType;
import ca.ulaval.glo4002.reservation.reports.ingredient.domain.TotalIngredientReport;
import ca.ulaval.glo4002.reservation.reports.ingredient.domain.UnitIngredientReport;
import ca.ulaval.glo4002.reservation.reports.ingredient.rest.responses.IngredientReportResponse;
import ca.ulaval.glo4002.reservation.reports.ingredient.rest.responses.TotalIngredientReportResponse;
import ca.ulaval.glo4002.reservation.reports.ingredient.rest.responses.UnitIngredientReportResponse;

public class IngredientReportResponseFactory {

  private IngredientReportService ingredientReportService;

  public IngredientReportResponseFactory(IngredientReportService ingredientReportService) {
    this.ingredientReportService = ingredientReportService;
  }

  public IngredientReportResponse generateReport(
      DateInterval dateInterval, IngredientReportType type)
      throws InvalidReportTypeException, InvalidReportDateException {

    switch (type) {
      case UNIT:
        return generateUnitReport(dateInterval);
      case TOTAL:
        return generateTotalReport(dateInterval);
    }

    throw new InvalidReportTypeException();
  }

  private UnitIngredientReportResponse generateUnitReport(DateInterval dateInterval)
      throws InvalidReportDateException {
    UnitIngredientReport unitReport =
        ingredientReportService.generateUnitIngredientReport(dateInterval);

    return new UnitIngredientReportResponse(unitReport);
  }

  private TotalIngredientReportResponse generateTotalReport(DateInterval dateInterval)
      throws InvalidReportDateException {
    TotalIngredientReport totalReport =
        ingredientReportService.generateTotalIngredientReport(dateInterval);

    return new TotalIngredientReportResponse(totalReport);
  }
}
