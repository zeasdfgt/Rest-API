package ca.ulaval.glo4002.reservation.reports.material.domain;

import ca.ulaval.glo4002.reservation.money.Money;
import ca.ulaval.glo4002.reservation.tableware.domain.Tableware;
import ca.ulaval.glo4002.reservation.tableware.domain.TablewareCabinet;
import ca.ulaval.glo4002.reservation.tableware.domain.TablewareType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaterialReportFactoryTest {
  private final TablewareType A_TABLEWARE_TYPE = TablewareType.FORK;
  private final Money A_TABLEWARE_PRICE = new Money(20);

  private MaterialReportFactory materialReportFactory;

  @BeforeEach
  public void materialReportAssemblerInitialization() {
    materialReportFactory = new MaterialReportFactory();
  }

  @Test
  public void
      whenGeneratingReport_thenTotalPriceIsPreviousDayWashableWashingPriceAndCurrentDayRemainingBuyingPrice() {
    Tableware aTableware = new Tableware(A_TABLEWARE_TYPE, A_TABLEWARE_PRICE);
    List<Tableware> twoTableware = new ArrayList<>();
    twoTableware.add(aTableware);
    twoTableware.add(aTableware);

    List<Tableware> oneTableware = new ArrayList<>();
    oneTableware.add(aTableware);

    List<Tableware> expectedWashedTableware = new ArrayList<>();
    expectedWashedTableware.add(aTableware);
    List<Tableware> expectedBoughtTableware = new ArrayList<>();
    expectedBoughtTableware.add(aTableware);
    Money expectedWashingCost = new TablewareCabinet(expectedWashedTableware).getWashingPrice();
    Money expectedBuyingCost = new TablewareCabinet(expectedBoughtTableware).getBuyingPrice();
    Money expectedTotalCost = expectedBuyingCost.add(expectedWashingCost);

    MaterialReport result =
        materialReportFactory.generateMaterialReport(oneTableware, twoTableware);

    assertEquals(expectedTotalCost, result.getTotalPrice());
  }
}
