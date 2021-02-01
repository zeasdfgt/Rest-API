package ca.ulaval.glo4002.reservation.reports.chef.domain.factories;

import ca.ulaval.glo4002.reservation.chef.Chef;
import ca.ulaval.glo4002.reservation.event.domain.EventDay;
import ca.ulaval.glo4002.reservation.reports.chef.domain.DailyChefReport;

import java.util.ArrayList;
import java.util.List;

public class DailyChefReportFactory {
  public List<DailyChefReport> generateChefReports(List<EventDay> eventDays, List<Chef> chefs) {
    List<DailyChefReport> dailyReports = new ArrayList<>();
    for (EventDay eventDay : eventDays) {
      dailyReports.add(
          new DailyChefReport(chefs, eventDay.getRestrictionsForChefs(), eventDay.getDate()));
    }
    return dailyReports;
  }
}
