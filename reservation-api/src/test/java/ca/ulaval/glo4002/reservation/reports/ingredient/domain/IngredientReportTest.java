package ca.ulaval.glo4002.reservation.reports.ingredient.domain;

import ca.ulaval.glo4002.reservation.date.domain.Date;
import ca.ulaval.glo4002.reservation.ingredient.domain.Ingredient;
import ca.ulaval.glo4002.reservation.ingredient.domain.MealIngredient;
import ca.ulaval.glo4002.reservation.ingredient.domain.repositories.MealIngredientRepository;
import ca.ulaval.glo4002.reservation.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class IngredientReportTest {
  private Ingredient anIngredient;
  private Ingredient anOtherIngredient;
  private final MealIngredientRepository mealIngredientRepository =
      mock(MealIngredientRepository.class);
  private IngredientReport report;
  private Date aDate;
  private Date anOtherDate;
  private List<MealIngredient> mealIngredients;
  private List<MealIngredient> mealIngredientsForOtherDate;

  @BeforeEach
  public void ingredientReportInitialization() {
    Money pricePerWeightOfIngredient = new Money(5);
    Money anOtherPricePerWeightOfIngredient = new Money(9);
    anIngredient = new Ingredient("an ingredient", pricePerWeightOfIngredient);
    anOtherIngredient = new Ingredient("an other ingredient", anOtherPricePerWeightOfIngredient);

    aDate = Date.startOfDay(2150, 7, 21);
    BigDecimal firstWeight = new BigDecimal(7);
    mealIngredients = createIngredientForDate(anIngredient, firstWeight);

    anOtherDate = Date.startOfDay(2150, 7, 22);
    BigDecimal secondWeight = new BigDecimal(12);
    mealIngredientsForOtherDate =
        createIngredientsForDate(anIngredient, anOtherIngredient, secondWeight);

    report = new IngredientReport(mealIngredients);
  }

  @Test
  public void givenAnIngredient_whenCreatingReport_thenAddsIngredientToReport() {
    report = new IngredientReport(mealIngredients);
    MealIngredient totalMealIngredient = report.getMealIngredientsTotals().get(0);

    assertNotNull(totalMealIngredient);
  }

  @Test
  public void
      givenSameIngredientTwiceInList_whenCreatingReport_thenAddsWeightToTotalWeightOfIngredient() {
    mealIngredients.addAll(mealIngredientsForOtherDate);
    report = new IngredientReport(mealIngredients);
    MealIngredient totalMealIngredient = report.getMealIngredientsTotals().get(0);

    assertEquals(new Money(95).toBigDecimal(), totalMealIngredient.calculatePrice().toBigDecimal());
  }

  @Test
  public void whenMultipleIngredientsToReport_thenAddsWeightToTotalWeightOfIngredient() {
    mealIngredients.addAll(mealIngredientsForOtherDate);
    report = new IngredientReport(mealIngredients);
    Money totalPrice = report.getTotalPrice();

    assertEquals(new Money(203), totalPrice);
  }

  private List<MealIngredient> createIngredientForDate(Ingredient ingredient, BigDecimal weight) {
    BigDecimal weightOfIngredient = weight;
    MealIngredient mealIngredient = new MealIngredient(ingredient, weightOfIngredient);

    List<MealIngredient> mealIngredients = new ArrayList<>();
    mealIngredients.add(mealIngredient);
    return mealIngredients;
  }

  private List<MealIngredient> createIngredientsForDate(
      Ingredient anIngredient, Ingredient anOtherIngredient, BigDecimal weight) {
    BigDecimal weightOfIngredient = weight;
    MealIngredient mealIngredient = new MealIngredient(anIngredient, weightOfIngredient);
    MealIngredient anOtherMealIngredient =
        new MealIngredient(anOtherIngredient, weightOfIngredient);

    List<MealIngredient> mealIngredients = new ArrayList<>();
    mealIngredients.add(mealIngredient);
    mealIngredients.add(anOtherMealIngredient);
    return mealIngredients;
  }
}
