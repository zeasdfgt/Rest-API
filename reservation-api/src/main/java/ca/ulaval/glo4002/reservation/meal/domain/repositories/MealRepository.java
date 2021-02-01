package ca.ulaval.glo4002.reservation.meal.domain.repositories;

import ca.ulaval.glo4002.reservation.date.domain.Date;
import ca.ulaval.glo4002.reservation.meal.domain.Meal;

import java.util.List;

public interface MealRepository {
  List<Meal> getAllMeals();

  List<Meal> getMealsByDate(Date date);
}
