package ca.ulaval.glo4002.reservation.meal.domain.repositories;

import ca.ulaval.glo4002.reservation.reservation.domain.RestrictionType;

import java.math.BigDecimal;
import java.util.HashMap;

public interface MenuRepository {
  HashMap<String, BigDecimal> getDefaultMeal();

  HashMap<String, BigDecimal> getMealByRestriction(RestrictionType restrictionType);
}
