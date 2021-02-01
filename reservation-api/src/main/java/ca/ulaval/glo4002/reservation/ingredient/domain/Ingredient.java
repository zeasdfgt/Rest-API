package ca.ulaval.glo4002.reservation.ingredient.domain;

import ca.ulaval.glo4002.reservation.money.Money;

import java.util.Objects;

public class Ingredient {
  private final String name;
  private final Money pricePerWeight;

  public Ingredient(String name, Money pricePerWeight) {
    this.name = name;
    this.pricePerWeight = pricePerWeight;
  }

  public String getName() {
    return name;
  }

  public Money getPricePerWeight() {
    return pricePerWeight;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    Ingredient that = (Ingredient) object;
    return Objects.equals(name, that.name) && Objects.equals(pricePerWeight, that.pricePerWeight);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, pricePerWeight);
  }
}
