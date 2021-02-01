package ca.ulaval.glo4002.reservation.reports.ingredient.rest.responses;

import ca.ulaval.glo4002.reservation.ingredient.domain.MealIngredient;
import ca.ulaval.glo4002.reservation.reports.ingredient.domain.TotalIngredientReport;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TotalIngredientReportResponse implements IngredientReportResponse {

  public final @JsonProperty(value = "ingredients") List<IngredientResponse> ingredientResponses =
      new ArrayList<>();
  public final BigDecimal totalPrice;

  @JsonCreator
  public TotalIngredientReportResponse(TotalIngredientReport report) {
    this.totalPrice = report.getTotalPrice().toBigDecimal();

    for (MealIngredient ingredient : report.getMealIngredientsTotals()) {
      IngredientResponse ingredientResponse = new IngredientResponse(ingredient);
      ingredientResponses.add(ingredientResponse);
    }

    ingredientResponses.sort(
        Comparator.comparing((IngredientResponse ingredientResponse) -> ingredientResponse.name));
  }
}
