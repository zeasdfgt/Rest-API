package ca.ulaval.glo4002.reservation.tableware.domain;

import ca.ulaval.glo4002.reservation.money.Money;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TablewareTest {
  private final TablewareType A_TABLEWARE_TYPE = TablewareType.FORK;
  private final TablewareType ANOTHER_TABLEWARE_TYPE = TablewareType.BOWL;
  private final Money A_TABLEWARE_PRICE = new Money(20);
  private final Money ANOTHER_TABLEWARE_PRICE = new Money(30);

  @Test
  public void whenCheckingIfTwoIdenticalTablewareAreEqual_thenReturnsTrue() {
    Tableware aTableware = new Tableware(A_TABLEWARE_TYPE, A_TABLEWARE_PRICE);
    Tableware anotherTableware = new Tableware(A_TABLEWARE_TYPE, A_TABLEWARE_PRICE);

    assertEquals(anotherTableware, aTableware);
  }

  @Test
  public void whenCheckingIfTwoTablewareOfTheSameInstanceAreEqual_thenReturnsTrue() {
    Tableware aTableware = new Tableware(A_TABLEWARE_TYPE, A_TABLEWARE_PRICE);
    Tableware anotherTableware = aTableware;

    assertEquals(anotherTableware, aTableware);
  }

  @Test
  public void whenCheckingIfTwoTablewareOfDifferentTypeAreEqual_thenReturnsFalse() {
    Tableware aTableware = new Tableware(A_TABLEWARE_TYPE, A_TABLEWARE_PRICE);
    Tableware anotherTableware = new Tableware(ANOTHER_TABLEWARE_TYPE, A_TABLEWARE_PRICE);

    assertNotEquals(anotherTableware, aTableware);
  }

  @Test
  public void whenCheckingIfTwoTablewareOfDifferentPriceAreEqual_thenReturnsFalse() {
    Tableware aTableware = new Tableware(A_TABLEWARE_TYPE, A_TABLEWARE_PRICE);
    Tableware anotherTableware = new Tableware(A_TABLEWARE_TYPE, ANOTHER_TABLEWARE_PRICE);

    assertNotEquals(anotherTableware, aTableware);
  }

  @Test
  public void whenCheckingIfTwoTablewareOfDifferentPropertiesAreEqual_thenReturnsFalse() {
    Tableware aTableware = new Tableware(A_TABLEWARE_TYPE, A_TABLEWARE_PRICE);
    Tableware anotherTableware = new Tableware(ANOTHER_TABLEWARE_TYPE, ANOTHER_TABLEWARE_PRICE);

    assertNotEquals(anotherTableware, aTableware);
  }
}
