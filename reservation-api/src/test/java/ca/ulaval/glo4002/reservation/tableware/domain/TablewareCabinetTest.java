package ca.ulaval.glo4002.reservation.tableware.domain;

import ca.ulaval.glo4002.reservation.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TablewareCabinetTest {
  private final Money A_TABLEWARE_PRICE = new Money(20);
  private final Money ANOTHER_TABLEWARE_PRICE = new Money(170);
  private final Money WASHING_PRICE_PER_BATCH = new Money(100);
  private final int ITEMS_PER_BATCH = 9;
  private TablewareCabinet cabinet;
  private List<Tableware> tableware;
  private Tableware firstTableware;
  private Tableware secondTableware;

  @BeforeEach
  public void tablewareCabinetInitialization() {
    tableware = new ArrayList<>();
    firstTableware = new Tableware(TablewareType.FORK, A_TABLEWARE_PRICE);
    secondTableware = new Tableware(TablewareType.BOWL, ANOTHER_TABLEWARE_PRICE);
    tableware.add(firstTableware);
    tableware.add(secondTableware);
    cabinet = new TablewareCabinet(tableware);
  }

  @Test
  public void whenGettingTablewareByType_thenReturnsAllTablewareOfType() {
    TablewareType requestedType = TablewareType.FORK;
    Tableware thirdTableware = new Tableware(requestedType, A_TABLEWARE_PRICE);
    tableware.add(thirdTableware);
    List<Tableware> expectedTableware = new ArrayList<>();
    expectedTableware.add(firstTableware);
    expectedTableware.add(thirdTableware);

    List<Tableware> returnedTableware = cabinet.getTablewareByType(requestedType);

    assertEquals(expectedTableware, returnedTableware);
  }

  @Test
  public void whenGettingWashableTableware_thenReturnsAllCommonTablewareBetweenBothCabinets() {
    List<Tableware> otherCabinetTableware = new ArrayList<>();
    otherCabinetTableware.add(firstTableware);
    TablewareCabinet otherCabinet = new TablewareCabinet(otherCabinetTableware);

    TablewareCabinet returnedCabinet = cabinet.getWashableTableware(otherCabinet);

    assertEquals(otherCabinet, returnedCabinet);
  }

  @Test
  public void
      whenGettingUnwashableTableware_thenReturnsTablewareFromCurrentCabinetNotInCommonWithOtherCabinet() {
    List<Tableware> otherCabinetTableware = new ArrayList<>();
    otherCabinetTableware.add(firstTableware);
    TablewareCabinet otherCabinet = new TablewareCabinet(otherCabinetTableware);
    List<Tableware> expectedTableware = new ArrayList<>();
    expectedTableware.add(secondTableware);
    TablewareCabinet expectedCabinet = new TablewareCabinet(expectedTableware);
    TablewareCabinet returnedCabinet = cabinet.getUnwashableTableware(otherCabinet);

    assertEquals(expectedCabinet, returnedCabinet);
  }

  @Test
  public void whenGettingWashingPrice_thenReturnsCorrectPrice() {
    double numberOfItems = cabinet.getAllTableware().size();
    int numberOfBatches = (int) Math.ceil(numberOfItems / ITEMS_PER_BATCH);
    Money expectedPrice = WASHING_PRICE_PER_BATCH.multiply(new BigDecimal(numberOfBatches));

    Money returnedPrice = cabinet.getWashingPrice();

    assertEquals(expectedPrice, returnedPrice);
  }

  @Test
  public void whenGettingBuyingPrice_thenReturnsTotalPriceOfTableware() {
    Money expectedPrice = firstTableware.getPrice().add(secondTableware.getPrice());

    Money returnedPrice = cabinet.getBuyingPrice();

    assertEquals(expectedPrice, returnedPrice);
  }
}
