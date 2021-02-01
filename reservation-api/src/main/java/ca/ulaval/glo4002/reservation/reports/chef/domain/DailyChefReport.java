package ca.ulaval.glo4002.reservation.reports.chef.domain;

import ca.ulaval.glo4002.reservation.chef.Chef;
import ca.ulaval.glo4002.reservation.date.domain.Date;
import ca.ulaval.glo4002.reservation.money.Money;
import ca.ulaval.glo4002.reservation.reservation.domain.Restriction;

import java.util.List;
import java.util.Set;

public class DailyChefReport {
  private final Date date;
  private final ChefAssignator assignator;
  private final List<Restriction> restrictionsOfTheDay;

  public DailyChefReport(List<Chef> chefs, List<Restriction> restrictionsOfTheDay, Date date) {
    this.date = date;
    this.assignator = new ChefAssignator(chefs);
    this.restrictionsOfTheDay = restrictionsOfTheDay;
  }

  public Date getDate() {
    return date;
  }

  public Set<Chef> getChefsForDay() {
    return assignator.getChefsForDay(restrictionsOfTheDay);
  }

  public Money getPriceForTheDay() {
    return assignator.priceOfChefsForTheDay(restrictionsOfTheDay);
  }
}
