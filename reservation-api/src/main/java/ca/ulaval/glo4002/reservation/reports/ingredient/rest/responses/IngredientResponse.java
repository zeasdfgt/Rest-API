package ca.ulaval.glo4002.reservation.reports.ingredient.rest.responses;

import ca.ulaval.glo4002.reservation.ingredient.domain.MealIngredient;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.math.BigDecimal;

public class IngredientResponse {
  public final String name;
  public final BigDecimal totalPrice;
  public final BigDecimal quantity;

  @JsonCreator
  public IngredientResponse(MealIngredient mealIngredient) {
    this.name = mealIngredient.getIngredientName();
    this.totalPrice = mealIngredient.calculatePrice().toBigDecimal();
    this.quantity = mealIngredient.getWeight();
  }
}
