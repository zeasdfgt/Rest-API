package ca.ulaval.glo4002.reservation.reports.total.domain;

import java.util.Objects;

public class TotalReport {
  private final double expense;
  private final double income;
  private final double profits;

  public TotalReport(double expense, double income, double profits) {
    this.expense = expense;
    this.income = income;
    this.profits = profits;
  }

  public double getExpense() {
    return expense;
  }

  public double getIncome() {
    return income;
  }

  public double getProfits() {
    return profits;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TotalReport that = (TotalReport) o;
    return Double.compare(that.expense, expense) == 0
        && Double.compare(that.income, income) == 0
        && Double.compare(that.profits, profits) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(expense, income, profits);
  }
}
