package ca.ulaval.glo4002.reservation.reports.chef.rest.responses;

import ca.ulaval.glo4002.reservation.reports.chef.domain.DailyChefReport;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ChefReportResponse {
  public final @JsonProperty(value = "dates") List<DailyChefReportResponse> days =
      new ArrayList<>();

  @JsonCreator
  public ChefReportResponse(List<DailyChefReport> reports) {
    List<DailyChefReportResponse> dailyReports = new ArrayList<>();
    for (DailyChefReport dailyReport : reports) {
      dailyReports.add(new DailyChefReportResponse(dailyReport));
    }
    this.days.addAll(dailyReports);

    this.days.sort(Comparator.comparing((DailyChefReportResponse response) -> response.date));
  }
}
