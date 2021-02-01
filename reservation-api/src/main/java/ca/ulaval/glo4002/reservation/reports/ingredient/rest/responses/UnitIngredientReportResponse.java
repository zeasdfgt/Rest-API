package ca.ulaval.glo4002.reservation.reports.ingredient.rest.responses;

import ca.ulaval.glo4002.reservation.date.domain.Date;
import ca.ulaval.glo4002.reservation.reports.ingredient.domain.IngredientReport;
import ca.ulaval.glo4002.reservation.reports.ingredient.domain.UnitIngredientReport;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class UnitIngredientReportResponse implements IngredientReportResponse {
  public final @JsonProperty(value = "dates") List<DateIngredientResponse> dateIngredientResponses =
      new ArrayList<>();

  @JsonCreator
  public UnitIngredientReportResponse(UnitIngredientReport report) {

    Map<Date, IngredientReport> ingredientReportMap = report.toMap();

    for (Map.Entry<Date, IngredientReport> entry : ingredientReportMap.entrySet()) {
      DateIngredientResponse dateIngredientResponse =
          new DateIngredientResponse(
              entry.getKey().toLocalDate(),
              entry.getValue().getMealIngredientsTotals(),
              entry.getValue().getTotalPrice());
      dateIngredientResponses.add(dateIngredientResponse);
    }

    dateIngredientResponses.sort(
        Comparator.comparing((DateIngredientResponse response) -> response.date));
  }
}
