package ca.ulaval.glo4002.reservation.reservation.domain;

import ca.ulaval.glo4002.reservation.money.Money;

import java.util.Objects;

public class Restriction {
  private final Money price;
  private final RestrictionType type;

  public Restriction(Money price, RestrictionType type) {
    this.price = price;
    this.type = type;
  }

  public Money getPrice() {
    return price;
  }

  public RestrictionType getType() {
    return type;
  }

  public String getTypeToString() {
    return type.toString();
  }

  @Override
  public boolean equals(Object restriction) {
    if (restriction == this) {
      return true;
    }

    if (!(restriction instanceof Restriction)) {
      return false;
    }

    Restriction r = (Restriction) restriction;

    return this.getTypeToString().equals(r.getTypeToString());
  }

  @Override
  public int hashCode() {
    return Objects.hash(price, type);
  }
}
