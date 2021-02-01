package ca.ulaval.glo4002.reservation.reports.material.domain;

import ca.ulaval.glo4002.reservation.money.Money;
import ca.ulaval.glo4002.reservation.tableware.domain.TablewareCabinet;

import java.util.Objects;

public class MaterialReport {
  private final TablewareCabinet toWash;
  private final TablewareCabinet toBuy;
  private final Money totalPrice;

  public MaterialReport(TablewareCabinet toWash, TablewareCabinet toBuy, Money totalPrice) {
    this.toWash = toWash;
    this.toBuy = toBuy;
    this.totalPrice = totalPrice;
  }

  public TablewareCabinet getToWash() {
    return toWash;
  }

  public TablewareCabinet getToBuy() {
    return toBuy;
  }

  public Money getTotalPrice() {
    return totalPrice;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    MaterialReport that = (MaterialReport) object;
    return Objects.equals(toWash, that.toWash)
        && Objects.equals(toBuy, that.toBuy)
        && Objects.equals(totalPrice, that.totalPrice);
  }

  @Override
  public int hashCode() {
    return Objects.hash(toWash, toBuy, totalPrice);
  }
}
