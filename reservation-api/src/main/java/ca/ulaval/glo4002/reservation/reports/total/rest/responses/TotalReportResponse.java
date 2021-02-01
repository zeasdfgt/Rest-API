package ca.ulaval.glo4002.reservation.reports.total.rest.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TotalReportResponse {
    public final @JsonProperty(value = "expense") double expense;
    public final @JsonProperty(value = "income") double income;
    public final @JsonProperty(value = "profits") double profits;

    public TotalReportResponse(double expense, double income, double profits) {
        this.expense = expense;
        this.income = income;
        this.profits = profits;
    }
}
