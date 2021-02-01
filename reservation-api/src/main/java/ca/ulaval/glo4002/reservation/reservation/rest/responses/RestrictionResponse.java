package ca.ulaval.glo4002.reservation.reservation.rest.responses;

import ca.ulaval.glo4002.reservation.reservation.domain.Restriction;
import com.fasterxml.jackson.annotation.JsonValue;

public class RestrictionResponse implements Comparable<RestrictionResponse> {
  public final String type;

  public RestrictionResponse(Restriction restriction) {
    this.type = restriction.getTypeToString();
  }

  @JsonValue
  public String toString() {
    return type;
  }

  @Override
  public int compareTo(RestrictionResponse otherRestriction) {
    return this.type.compareTo(otherRestriction.type);
  }
}
