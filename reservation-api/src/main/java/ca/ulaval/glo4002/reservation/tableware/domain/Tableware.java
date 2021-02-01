package ca.ulaval.glo4002.reservation.tableware.domain;

import ca.ulaval.glo4002.reservation.money.Money;

import java.util.Objects;

public class Tableware {
  private final TablewareType type;
  private final Money price;

  public Tableware(TablewareType tablewareType, Money price) {
    this.type = tablewareType;
    this.price = price;
  }

  public Money getPrice() {
    return price;
  }

  public TablewareType getType() {
    return type;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    Tableware tableware = (Tableware) object;
    return type == tableware.type && Objects.equals(price, tableware.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, price);
  }
}
