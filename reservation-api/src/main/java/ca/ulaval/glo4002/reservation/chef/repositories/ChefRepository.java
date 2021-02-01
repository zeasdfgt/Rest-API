package ca.ulaval.glo4002.reservation.chef.repositories;

import ca.ulaval.glo4002.reservation.chef.Chef;
import ca.ulaval.glo4002.reservation.reservation.domain.RestrictionType;

import java.util.List;

public interface ChefRepository {
  List<Chef> getAllChefs();

  List<Chef> getChefsBySpeciality(RestrictionType speciality);
}
