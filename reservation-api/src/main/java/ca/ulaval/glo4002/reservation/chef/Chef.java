package ca.ulaval.glo4002.reservation.chef;

import ca.ulaval.glo4002.reservation.money.Money;
import ca.ulaval.glo4002.reservation.reservation.domain.RestrictionType;

import java.util.List;
import java.util.Objects;

public class Chef {
  private String name;
  private Money price;

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    Chef chef = (Chef) object;
    return Objects.equals(name, chef.name)
        && Objects.equals(price, chef.price)
        && Objects.equals(specialities, chef.specialities);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, price, specialities);
  }

  private List<RestrictionType> specialities;

  public Chef(String name, Money price, List<RestrictionType> specialties) {
    this.name = name;
    this.price = price;
    this.specialities = specialties;
  }

  public String getName() {
    return name;
  }

  public Money getPrice() {
    return price;
  }

  public List<RestrictionType> getSpecialities() {
    return specialities;
  }

  public boolean hasThisSpeciality(RestrictionType restriction) {
    return specialities.contains(restriction);
  }
}
