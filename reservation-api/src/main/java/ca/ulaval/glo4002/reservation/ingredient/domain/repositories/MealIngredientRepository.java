package ca.ulaval.glo4002.reservation.ingredient.domain.repositories;

import ca.ulaval.glo4002.reservation.date.domain.Date;
import ca.ulaval.glo4002.reservation.date.domain.DateInterval;
import ca.ulaval.glo4002.reservation.ingredient.domain.MealIngredient;

import java.util.List;

public interface MealIngredientRepository {
  List<MealIngredient> getMealIngredientsByDate(Date date);

  List<MealIngredient> getMealIngredientsByDateInterval(DateInterval dateInterval);
}
