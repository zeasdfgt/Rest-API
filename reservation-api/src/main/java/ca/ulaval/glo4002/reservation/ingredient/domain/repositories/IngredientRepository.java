package ca.ulaval.glo4002.reservation.ingredient.domain.repositories;

import ca.ulaval.glo4002.reservation.ingredient.domain.Ingredient;
import ca.ulaval.glo4002.reservation.ingredient.domain.exceptions.IngredientNotFoundException;

public interface IngredientRepository {

  Ingredient getIngredientByName(String IngredientName) throws IngredientNotFoundException;
}
