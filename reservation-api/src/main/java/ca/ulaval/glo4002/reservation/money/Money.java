package ca.ulaval.glo4002.reservation.money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Money implements Comparable<Money> {
  private final BigDecimal value;

  public Money(BigDecimal value) {
    this.value = value;
  }

  public Money(int value) {
    this.value = new BigDecimal(value);
  }

  public Money() {
    this.value = new BigDecimal(0);
  }

  public Money add(Money amountToAdd) {
    return new Money(this.value.add(amountToAdd.toBigDecimal()));
  }

  public Money subtract(Money amountToSubtract) {
    return new Money(this.value.subtract(amountToSubtract.toBigDecimal()));
  }

  public BigDecimal toBigDecimal() {
    return value;
  }

  public BigDecimal toRoundedBigDecimal() {
    return roundedValue();
  }

  public Money multiply(BigDecimal multiplier) {
    return new Money(value.multiply(multiplier));
  }

  public Money getRoundedValue() {
    return new Money(roundedValue());
  }

  @Override
  public int compareTo(Money otherMoney) {
    return value.compareTo(otherMoney.toBigDecimal());
  }

  @Override
  public boolean equals(Object object) {
    if (getClass() != object.getClass()) {
      return false;
    }
    Money otherAmount = (Money) object;
    return roundedValue().equals(otherAmount.getRoundedValue().toBigDecimal());
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  private BigDecimal roundedValue() {
    return value.setScale(2, RoundingMode.HALF_UP);
  }
}
