package ca.ulaval.glo4002.reservation.reports.material.rest.responses;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MaterialReportResponse {
  public final @JsonProperty(value = "dates") List<DateMaterialResponse> dateMaterialResponses;

  @JsonCreator
  public MaterialReportResponse(List<DateMaterialResponse> dateMaterialResponses) {
    this.dateMaterialResponses = dateMaterialResponses;
  }
}
