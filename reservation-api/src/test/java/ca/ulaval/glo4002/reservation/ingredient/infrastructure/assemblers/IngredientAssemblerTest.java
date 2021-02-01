package ca.ulaval.glo4002.reservation.ingredient.infrastructure.assemblers;

import ca.ulaval.glo4002.reservation.ingredient.domain.Ingredient;
import ca.ulaval.glo4002.reservation.ingredient.infrastructure.APIIngredient;
import ca.ulaval.glo4002.reservation.money.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

class IngredientAssemblerTest {
  private final String AN_INGREDIENT_NAME = "Potato";
  private final BigDecimal A_PRICE_PER_KG = new BigDecimal("10.0");

  @Test
  public void givenAnAPIIngredient_thenCreatesDomainIngredient() {
    APIIngredient anApiIngredient = mock(APIIngredient.class);
    Ingredient anIngredient;
    IngredientAssembler assembler = new IngredientAssembler();

    willReturn(AN_INGREDIENT_NAME).given(anApiIngredient).getName();
    willReturn(A_PRICE_PER_KG).given(anApiIngredient).getPricePerKg();

    anIngredient = assembler.assembleIngredient(anApiIngredient);
    Money expectedPrice = new Money(A_PRICE_PER_KG);

    assertEquals(AN_INGREDIENT_NAME, anIngredient.getName());
    assertEquals(expectedPrice, anIngredient.getPricePerWeight());
  }
}
