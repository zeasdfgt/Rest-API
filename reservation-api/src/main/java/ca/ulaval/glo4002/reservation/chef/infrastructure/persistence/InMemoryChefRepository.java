package ca.ulaval.glo4002.reservation.chef.infrastructure.persistence;

import ca.ulaval.glo4002.reservation.chef.Chef;
import ca.ulaval.glo4002.reservation.chef.repositories.ChefRepository;
import ca.ulaval.glo4002.reservation.money.Money;
import ca.ulaval.glo4002.reservation.reservation.domain.RestrictionType;

import java.util.ArrayList;
import java.util.List;

public class InMemoryChefRepository implements ChefRepository {
  private List<Chef> chefs;

  public InMemoryChefRepository() {
    Money price = new Money(6000);
    this.chefs = new ArrayList<>();

    List<RestrictionType> specialities = new ArrayList<>();
    specialities.add(RestrictionType.NONE);
    this.chefs.add(new Chef("Thierry Aki", price, specialities));

    specialities = new ArrayList<>();
    specialities.add(RestrictionType.VEGAN);
    this.chefs.add(new Chef("Bob Smarties", price, specialities));

    specialities = new ArrayList<>();
    specialities.add(RestrictionType.VEGETARIAN);
    this.chefs.add(new Chef("Bob Rossbeef", price, specialities));

    specialities = new ArrayList<>();
    specialities.add(RestrictionType.ALLERGIES);
    this.chefs.add(new Chef("Bill Adicion", price, specialities));

    specialities = new ArrayList<>();
    specialities.add(RestrictionType.ILLNESS);
    this.chefs.add(new Chef("Omar Calmar", price, specialities));

    specialities = new ArrayList<>();
    specialities.add(RestrictionType.ALLERGIES);
    specialities.add(RestrictionType.VEGAN);
    this.chefs.add(new Chef("Écharlotte Cardin", price, specialities));

    specialities = new ArrayList<>();
    specialities.add(RestrictionType.VEGETARIAN);
    specialities.add(RestrictionType.ILLNESS);
    this.chefs.add(new Chef("Éric Ardo", price, specialities));

    specialities = new ArrayList<>();
    specialities.add(RestrictionType.NONE);
    specialities.add(RestrictionType.ILLNESS);
    this.chefs.add(new Chef("Hans Riz", price, specialities));

    specialities = new ArrayList<>();
    specialities.add(RestrictionType.VEGAN);
    specialities.add(RestrictionType.ALLERGIES);
    this.chefs.add(new Chef("Amélie Mélo", price, specialities));
  }

  @Override
  public List<Chef> getAllChefs() {
    return this.chefs;
  }

  @Override
  public List<Chef> getChefsBySpeciality(RestrictionType speciality) {
    List<Chef> chefsWithSpeciality = new ArrayList<>();
    for (Chef chef : this.chefs) {
      if (chef.hasThisSpeciality(speciality)) {
        chefsWithSpeciality.add(chef);
      }
    }
    return chefsWithSpeciality;
  }
}
