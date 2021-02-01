package ca.ulaval.glo4002.reservation.ingredient.infrastructure.assemblers;

import ca.ulaval.glo4002.reservation.ingredient.domain.Ingredient;
import ca.ulaval.glo4002.reservation.ingredient.infrastructure.APIIngredient;
import ca.ulaval.glo4002.reservation.money.Money;

public class IngredientAssembler {
  public Ingredient assembleIngredient(APIIngredient ingredient) {
    return new Ingredient(ingredient.getName(), new Money(ingredient.getPricePerKg()));
  }
}
