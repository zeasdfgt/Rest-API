package ca.ulaval.glo4002.reservation.reports.ingredient.rest.validators;

import ca.ulaval.glo4002.reservation.date.domain.Date;

import ca.ulaval.glo4002.reservation.reports.ingredient.domain.IngredientReportType;
import ca.ulaval.glo4002.reservation.reports.exceptions.InvalidReportDateException;
import ca.ulaval.glo4002.reservation.reports.exceptions.InvalidReportTypeException;

public class IngredientReportRequestValidator {
  public void validateIngredientReport(String startDate, String endDate, String type)
      throws InvalidReportDateException, InvalidReportTypeException {
    checkValidReportDate(startDate, endDate);
    checkValidReportType(type);
  }

  private void checkValidReportDate(String startDate, String endDate)
      throws InvalidReportDateException {
    try {
      Date.parseLocalDate(startDate);
      Date.parseLocalDate(endDate);
    } catch (Exception exception) {
      throw new InvalidReportDateException();
    }
  }

  private void checkValidReportType(String type) throws InvalidReportTypeException {
    try {
      IngredientReportType.getEnum(type);
    } catch (Exception exception) {
      throw new InvalidReportTypeException();
    }
  }
}
