package ca.ulaval.glo4002.reservation.reports.total.domain;

import ca.ulaval.glo4002.reservation.date.domain.Date;
import ca.ulaval.glo4002.reservation.date.domain.DateInterval;
import ca.ulaval.glo4002.reservation.event.application.ConfigService;
import ca.ulaval.glo4002.reservation.event.domain.Event;
import ca.ulaval.glo4002.reservation.money.Money;
import ca.ulaval.glo4002.reservation.reports.chef.application.ChefReportService;
import ca.ulaval.glo4002.reservation.reports.chef.domain.DailyChefReport;
import ca.ulaval.glo4002.reservation.reports.exceptions.InvalidReportDateException;
import ca.ulaval.glo4002.reservation.reports.ingredient.application.IngredientReportService;
import ca.ulaval.glo4002.reservation.reports.ingredient.domain.TotalIngredientReport;
import ca.ulaval.glo4002.reservation.reports.material.application.MaterialReportService;
import ca.ulaval.glo4002.reservation.reports.material.domain.MaterialReport;
import ca.ulaval.glo4002.reservation.reservation.domain.Reservation;

import java.util.List;

public class TotalReportFactory {
  Event event;
  ConfigService configService;
  MaterialReportService materialReportService;
  IngredientReportService ingredientReportService;
  ChefReportService chefReportService;

  public TotalReportFactory(
      Event event,
      ConfigService configService,
      MaterialReportService materialReportService,
      IngredientReportService ingredientReportService,
      ChefReportService chefReportService) {
    this.event = event;
    this.configService = configService;
    this.materialReportService = materialReportService;
    this.ingredientReportService = ingredientReportService;
    this.chefReportService = chefReportService;
  }

  public TotalReportFactory() {
    this.event = new Event();
    this.configService = new ConfigService();
    this.materialReportService = new MaterialReportService();
    this.ingredientReportService = new IngredientReportService();
    this.chefReportService = new ChefReportService();
  }

  public TotalReport generateTotalReport() throws InvalidReportDateException {
    Money expense = new Money(0);
    Money income = getReservationIncome(event.getAllReservations());

    Money ingredientsPrice = getIngredientsPrice();
    expense = expense.add(ingredientsPrice);

    Money chefsPrice = getChefsPrice(chefReportService.getChefReports());
    expense = expense.add(chefsPrice);

    Money materialPrice = getMaterialPrice();
    expense = expense.add(materialPrice);

    Money profits = income.subtract(expense);

    double expenseDouble = expense.toRoundedBigDecimal().doubleValue();
    double incomeDouble = income.toRoundedBigDecimal().doubleValue();
    double profitDouble = profits.toRoundedBigDecimal().doubleValue();

    return new TotalReport(expenseDouble, incomeDouble, profitDouble);
  }

  private Money getReservationIncome(List<Reservation> reservations) {
    Money income = new Money(0);
    for (Reservation reservation : reservations) {
      income = income.add(reservation.getTotalPrice());
    }

    return income;
  }

  private Money getIngredientsPrice() throws InvalidReportDateException {
    DateInterval dinnerDateInterval = configService.getDinnerDateInterval();
    TotalIngredientReport report =
        ingredientReportService.generateTotalIngredientReport(dinnerDateInterval);
    return report.getTotalPrice();
  }

  private Money getChefsPrice(List<DailyChefReport> chefReports) {
    Money price = new Money(0);
    for (DailyChefReport report : chefReports) {
      price = price.add(report.getPriceForTheDay());
    }

    return price;
  }

  private Money getMaterialPrice() throws InvalidReportDateException {
    Money price = new Money(0);
    DateInterval dinnerDateInterval = configService.getDinnerDateInterval();

    for (Date date = dinnerDateInterval.getStartDate();
        dinnerDateInterval.isWithinInterval(date);
        date = date.plusDays(1)) {
      MaterialReport materialReport = materialReportService.generateMaterialReport(date);
      price = price.add(materialReport.getTotalPrice());
    }

    return price;
  }
}
