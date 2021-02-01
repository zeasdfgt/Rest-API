package ca.ulaval.glo4002.reservation.ingredient.domain;

import ca.ulaval.glo4002.reservation.money.Money;

import java.math.BigDecimal;

public class MealIngredient {
  private Ingredient ingredient;
  private BigDecimal weight;

  public MealIngredient(Ingredient ingredient, BigDecimal weight) {
    this.ingredient = ingredient;
    this.weight = weight;
  }

  public Money calculatePrice() {
    return ingredient.getPricePerWeight().multiply(weight);
  }

  public String getIngredientName() {
    return ingredient.getName();
  }

  public Ingredient getIngredient() {
    return ingredient;
  }

  public BigDecimal getWeight() {
    return weight;
  }

  public MealIngredient addWeight(BigDecimal weightToAdd) {
    BigDecimal newWeight = weight.add(weightToAdd);
    return new MealIngredient(ingredient, newWeight);
  }

  public Money getIngredientPricePerWeight() {
    return ingredient.getPricePerWeight();
  }

  public boolean hasSameName(String comparedName) {
    return getIngredientName().equals(comparedName);
  }

  @Override
  public boolean equals(Object object) {
    if (getClass() != object.getClass()) {
      return false;
    }
    MealIngredient otherMealIngredient = (MealIngredient) object;
    boolean isSameName = ingredient.equals(otherMealIngredient.getIngredient());
    boolean isSameWeight = getWeight().equals(otherMealIngredient.getWeight());
    return isSameName && isSameWeight;
  }

  @Override
  public int hashCode() {
    Integer ingredientHash = getIngredient().hashCode();
    String IngredientNameAndWeight = ingredientHash.toString() + "-" + getWeight().toString();
    return IngredientNameAndWeight.hashCode();
  }
}
