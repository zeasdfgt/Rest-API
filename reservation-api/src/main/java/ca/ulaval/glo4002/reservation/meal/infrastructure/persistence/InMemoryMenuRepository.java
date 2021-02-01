package ca.ulaval.glo4002.reservation.meal.infrastructure.persistence;

import ca.ulaval.glo4002.reservation.meal.domain.repositories.MenuRepository;
import ca.ulaval.glo4002.reservation.reservation.domain.RestrictionType;

import java.math.BigDecimal;
import java.util.HashMap;

public class InMemoryMenuRepository implements MenuRepository {

  public HashMap<String, BigDecimal> getDefaultMeal() {
    HashMap<String, BigDecimal> no_restriction_meal = new HashMap<>();

    no_restriction_meal.put("Pork loin", new BigDecimal("5"));
    no_restriction_meal.put("Carrots", new BigDecimal("8"));
    no_restriction_meal.put("Pepperoni", new BigDecimal("10"));
    no_restriction_meal.put("Roast beef", new BigDecimal("5"));
    no_restriction_meal.put("Water", new BigDecimal("0.1"));

    return no_restriction_meal;
  }

  public HashMap<String, BigDecimal> getMealByRestriction(RestrictionType restrictionType) {
    switch (restrictionType) {
      case VEGETARIAN:
        return getVegetarianMeal();
      case VEGAN:
        return getVeganMeal();
      case ALLERGIES:
        return getAllergiesMeal();
      case ILLNESS:
        return getIllnessMeal();
      default:
        return new HashMap<>();
    }
  }

  private HashMap<String, BigDecimal> getVegetarianMeal() {
    HashMap<String, BigDecimal> vegetarian_meal = new HashMap<>();

    vegetarian_meal.put("Pumpkin", new BigDecimal("5"));
    vegetarian_meal.put("Chocolate", new BigDecimal("8"));
    vegetarian_meal.put("Tuna", new BigDecimal("10"));
    vegetarian_meal.put("Mozzarella", new BigDecimal("5"));
    vegetarian_meal.put("Water", new BigDecimal("0.1"));

    return vegetarian_meal;
  }

  private HashMap<String, BigDecimal> getVeganMeal() {
    HashMap<String, BigDecimal> vegan_meal = new HashMap<>();

    vegan_meal.put("Tomato", new BigDecimal("5"));
    vegan_meal.put("Kiwi", new BigDecimal("8"));
    vegan_meal.put("Kimchi", new BigDecimal("10"));
    vegan_meal.put("Worcestershire sauce", new BigDecimal("5"));
    vegan_meal.put("Water", new BigDecimal("0.1"));

    return vegan_meal;
  }

  private HashMap<String, BigDecimal> getAllergiesMeal() {
    HashMap<String, BigDecimal> allergies_meal = new HashMap<>();

    allergies_meal.put("Marmalade", new BigDecimal("5"));
    allergies_meal.put("Plantain", new BigDecimal("8"));
    allergies_meal.put("Tofu", new BigDecimal("10"));
    allergies_meal.put("Bacon", new BigDecimal("5"));
    allergies_meal.put("Water", new BigDecimal("0.1"));

    return allergies_meal;
  }

  private HashMap<String, BigDecimal> getIllnessMeal() {
    HashMap<String, BigDecimal> illness_meal = new HashMap<>();

    illness_meal.put("Scallops", new BigDecimal("2"));
    illness_meal.put("Butternut squash", new BigDecimal("4"));
    illness_meal.put("Kiwi", new BigDecimal("5"));
    illness_meal.put("Pepperoni", new BigDecimal("2"));
    illness_meal.put("Water", new BigDecimal("0.1"));

    return illness_meal;
  }
}
