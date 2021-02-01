package ca.ulaval.glo4002.reservation.reports.ingredient.rest.responses;

import ca.ulaval.glo4002.reservation.ingredient.domain.MealIngredient;
import ca.ulaval.glo4002.reservation.money.Money;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DateIngredientResponse {
  public final LocalDate date;
  public final @JsonProperty(value = "ingredients") List<IngredientResponse> ingredientResponses =
      new ArrayList<>();
  public final BigDecimal totalPrice;

  @JsonCreator
  public DateIngredientResponse(
      LocalDate date, List<MealIngredient> ingredients, Money totalPrice) {
    this.date = date;
    for (MealIngredient ingredient : ingredients) {
      ingredientResponses.add(new IngredientResponse(ingredient));
    }
    this.totalPrice = totalPrice.toRoundedBigDecimal();

    ingredientResponses.sort(
        Comparator.comparing((IngredientResponse ingredientResponse) -> ingredientResponse.name));
  }
}
