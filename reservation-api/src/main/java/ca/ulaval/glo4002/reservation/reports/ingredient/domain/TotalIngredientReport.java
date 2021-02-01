package ca.ulaval.glo4002.reservation.reports.ingredient.domain;

import ca.ulaval.glo4002.reservation.ingredient.domain.MealIngredient;
import ca.ulaval.glo4002.reservation.money.Money;

import java.util.List;

public class TotalIngredientReport {
  private IngredientReport report;

  public TotalIngredientReport(IngredientReport report) {
    this.report = report;
  }

  public IngredientReport getReport() {
    return report;
  }

  public List<MealIngredient> getMealIngredientsTotals() {
    return report.getMealIngredientsTotals();
  }

  public Money getTotalPrice() {
    return report.getTotalPrice();
  }
}
