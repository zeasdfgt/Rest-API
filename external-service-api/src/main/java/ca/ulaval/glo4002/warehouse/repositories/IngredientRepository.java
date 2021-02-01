package ca.ulaval.glo4002.warehouse.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RestResource;

import ca.ulaval.glo4002.warehouse.domain.Ingredient;

public interface IngredientRepository extends Repository<Ingredient, Integer> {
  @RestResource(exported = false)
  @Query("select distinct i from Ingredient i join fetch i.shipments")
  List<Ingredient> findAll();
}
