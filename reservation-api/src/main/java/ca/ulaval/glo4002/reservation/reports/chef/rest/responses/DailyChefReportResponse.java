package ca.ulaval.glo4002.reservation.reports.chef.rest.responses;

import ca.ulaval.glo4002.reservation.chef.Chef;
import ca.ulaval.glo4002.reservation.reports.chef.domain.DailyChefReport;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DailyChefReportResponse {
  public LocalDate date;
  public List<String> chefs = new ArrayList<>();
  public BigDecimal totalPrice;

  @JsonCreator
  public DailyChefReportResponse(DailyChefReport report) {
    this.date = report.getDate().toLocalDate();
    for (Chef chef : report.getChefsForDay()) {
      this.chefs.add(chef.getName());
    }
    this.totalPrice = report.getPriceForTheDay().toBigDecimal();

    Collections.sort(
        this.chefs,
        (aChef, anotherChef) -> {
          aChef = Normalizer.normalize(aChef, Normalizer.Form.NFD);
          anotherChef = Normalizer.normalize(anotherChef, Normalizer.Form.NFD);
          return aChef.compareTo(anotherChef);
        });
  }
}
