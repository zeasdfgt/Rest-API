package ca.ulaval.glo4002.reservation.reports.ingredient.domain;

import ca.ulaval.glo4002.reservation.date.domain.Date;
import ca.ulaval.glo4002.reservation.ingredient.domain.repositories.MealIngredientRepository;

import java.util.HashMap;
import java.util.Map;

public class UnitIngredientReport {
  private final MealIngredientRepository mealIngredientRepository;
  private Map<Date, IngredientReport> report;

  public UnitIngredientReport(MealIngredientRepository mealIngredientRepository) {
    this.mealIngredientRepository = mealIngredientRepository;
    this.report = new HashMap<>();
  }

  public Map<Date, IngredientReport> toMap() {
    return report;
  }

  public void addDate(Date date, IngredientReport dailyIngredientReport) {
    report.put(date, dailyIngredientReport);
  }
}
