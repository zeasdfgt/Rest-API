package ca.ulaval.glo4002.reservation.chef.infrastructure.persistence;

import ca.ulaval.glo4002.reservation.chef.Chef;
import ca.ulaval.glo4002.reservation.reservation.domain.RestrictionType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryChefRepositoryTest {
  private final RestrictionType A_SPECIALITY = RestrictionType.ALLERGIES;
  private final InMemoryChefRepository REPOSITORY = new InMemoryChefRepository();
  String A_CHEF_NAME = "Bill Adicion";
  String SOME_CHEF_NAME = "Écharlotte Cardin";
  String ANOTHER_CHEF_NAME = "Amélie Mélo";

  @Test
  void
      givenARepositoryWithChefs_whenSearchingBySpeciality_thenReturnsAListOfChefsWithSaidSpeciality() {
    List<Chef> chefList = REPOSITORY.getChefsBySpeciality(A_SPECIALITY);

    assertEquals(chefList.get(0).getName(), A_CHEF_NAME);
    assertEquals(chefList.get(1).getName(), SOME_CHEF_NAME);
    assertEquals(chefList.get(2).getName(), ANOTHER_CHEF_NAME);
  }
}
