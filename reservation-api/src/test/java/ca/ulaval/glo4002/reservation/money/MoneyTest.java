package ca.ulaval.glo4002.reservation.money;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {
  private final BigDecimal A_VALUE = new BigDecimal(10);
  private final BigDecimal ANOTHER_VALUE = new BigDecimal(20);
  private Money aMoney;
  private Money anotherMoney;

  @BeforeEach
  void moneyInitialization() {
    aMoney = new Money(A_VALUE);
    anotherMoney = new Money(ANOTHER_VALUE);
  }

  @Test
  void givenAMoneyValue_whenAddingAnotherMoneyValue_thenReturnsAMoneyWithBothValuesAdded() {
    Money expectedAddedMoney = new Money(A_VALUE.add(ANOTHER_VALUE));

    Money addedMoney = aMoney.add(anotherMoney);

    assertEquals(addedMoney, expectedAddedMoney);
  }

  @Test
  void
      givenAMoneyValue_whenSubstractingAnotherMoneyValue_thenReturnsAMoneyEqualToTheReminderOfTheValue() {
    Money expectedSubstractedMoney = new Money(A_VALUE.subtract(ANOTHER_VALUE));

    Money substractedMoney = aMoney.subtract(anotherMoney);

    assertEquals(expectedSubstractedMoney, substractedMoney);
  }

  @Test
  void givenAMoneyValue_whenMultiplyingWithAValue_thenReturnsAMoneyWithBothValuesMultiplied() {
    BigDecimal expectedMultipliedValue = A_VALUE.multiply(ANOTHER_VALUE);

    Money multipliedMoney = aMoney.multiply(ANOTHER_VALUE);
    BigDecimal multipliedValue = multipliedMoney.toBigDecimal();

    assertEquals(expectedMultipliedValue, multipliedValue);
  }

  @Test
  void givenAMoney_whenComparingEqualityWithAnotherMoneyOfSameValue_thenReturnsTrue() {
    Money sameValueMoney = new Money(A_VALUE);

    assertTrue(aMoney.equals(sameValueMoney));
  }

  @Test
  void givenAMoney_whenComparingEqualityWithAnotherMoneyOfOtherValue_thenReturnsFalse() {
    assertFalse(aMoney.equals(anotherMoney));
  }

  @Test
  void givenAMoney_whenRoundingTheValue_thenReturnsTheValueRoundedToTwoDecimals() {
    BigDecimal aValueWithLotsOfDecimals = new BigDecimal(15.123456789101112131415);
    BigDecimal expectedRoundedValue = new BigDecimal(15.12).setScale(2, RoundingMode.HALF_UP);
    Money aMoneyWithLotsOfDecimals = new Money(aValueWithLotsOfDecimals);

    BigDecimal roundedValueOfMoney = aMoneyWithLotsOfDecimals.toRoundedBigDecimal();

    assertEquals(roundedValueOfMoney, expectedRoundedValue);
  }

  @Test
  void givenAMoney_whenGettingTheRoundedValue_thenReturnsAMoneyWithTheValueRoundedToTwoDecimals() {
    BigDecimal aValueWithLotsOfDecimals = new BigDecimal(15.123456789101112131415);
    Money aMoneyWithLotsOfDecimals = new Money(aValueWithLotsOfDecimals);
    Money expectedMoneyWithRoundedValue = new Money(new BigDecimal(15.12));

    Money roundedValueMoney = aMoneyWithLotsOfDecimals.getRoundedValue();

    assertEquals(roundedValueMoney, expectedMoneyWithRoundedValue);
  }
}
