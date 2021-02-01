package ca.ulaval.glo4002.reservation.reports.material.rest.factories;

import ca.ulaval.glo4002.reservation.date.domain.Date;
import ca.ulaval.glo4002.reservation.money.Money;
import ca.ulaval.glo4002.reservation.reports.material.domain.MaterialReport;
import ca.ulaval.glo4002.reservation.reports.material.rest.responses.DateMaterialResponse;
import ca.ulaval.glo4002.reservation.rest.exceptions.InvalidFormatException;
import ca.ulaval.glo4002.reservation.tableware.domain.Tableware;
import ca.ulaval.glo4002.reservation.tableware.domain.TablewareCabinet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

class DateMaterialResponseFactoryTest {
  private final String A_DATE = "2150-07-21";
  private final int A_PRICE = 10;
  private DateMaterialResponseFactory dateMaterialResponseFactory;
  private MaterialReport aMaterialReport;
  private Date aDate;
  private Money aPrice;

  @BeforeEach
  public void dateMaterialResponseFactoryInitialization() throws InvalidFormatException {
    dateMaterialResponseFactory = new DateMaterialResponseFactory();
    aDate = Date.parseLocalDate(A_DATE);
    aPrice = new Money(A_PRICE);
  }

  @Test
  public void
      givenMaterialReportWithCleanedButNoBoughtTableware_whenGeneratingResponse_thenBoughtTablewareResponseIsNull() {
    MaterialReport aMaterialReport = mock(MaterialReport.class);
    List<Tableware> someTableware = new ArrayList<>();
    Tableware aTableware = mock(Tableware.class);
    someTableware.add(aTableware);
    TablewareCabinet aTablewareCabinet = new TablewareCabinet(someTableware);
    TablewareCabinet anEmptyTablewareCabinet = new TablewareCabinet(new ArrayList<>());
    willReturn(anEmptyTablewareCabinet).given(aMaterialReport).getToBuy();
    willReturn(aTablewareCabinet).given(aMaterialReport).getToWash();
    willReturn(aPrice).given(aMaterialReport).getTotalPrice();

    DateMaterialResponse response =
        dateMaterialResponseFactory.generateDateMaterialResponse(aDate, aMaterialReport);

    assertNull(response.boughtResponse);
  }

  @Test
  public void
      givenMaterialReportWithBoughtButNoCleanedTableware_whenGeneratingResponse_thenCleanedTablewareResponseIsNull() {
    MaterialReport aMaterialReport = mock(MaterialReport.class);
    List<Tableware> someTableware = new ArrayList<>();
    Tableware aTableware = mock(Tableware.class);
    someTableware.add(aTableware);
    TablewareCabinet aTablewareCabinet = new TablewareCabinet(someTableware);
    TablewareCabinet anEmptyTablewareCabinet = new TablewareCabinet(new ArrayList<>());
    willReturn(aTablewareCabinet).given(aMaterialReport).getToBuy();
    willReturn(anEmptyTablewareCabinet).given(aMaterialReport).getToWash();
    willReturn(aPrice).given(aMaterialReport).getTotalPrice();

    DateMaterialResponse response =
        dateMaterialResponseFactory.generateDateMaterialResponse(aDate, aMaterialReport);

    assertNull(response.cleanedResponse);
  }

  @Test
  public void
      givenMaterialReportWithBoughtAndCleanedTableware_whenGeneratingResponse_thenBoughtAndCleanedTablewareResponsesAreNotNull() {
    MaterialReport aMaterialReport = mock(MaterialReport.class);
    List<Tableware> someTableware = new ArrayList<>();
    Tableware aTableware = mock(Tableware.class);
    someTableware.add(aTableware);
    TablewareCabinet aTablewareCabinet = new TablewareCabinet(someTableware);
    willReturn(aTablewareCabinet).given(aMaterialReport).getToBuy();
    willReturn(aTablewareCabinet).given(aMaterialReport).getToWash();
    willReturn(aPrice).given(aMaterialReport).getTotalPrice();

    DateMaterialResponse response =
        dateMaterialResponseFactory.generateDateMaterialResponse(aDate, aMaterialReport);

    assertNotNull(response.cleanedResponse);
    assertNotNull(response.boughtResponse);
  }

  @Test
  public void
      whenGeneratingResponse_thenDateMaterialResponseContainsGivenMaterialReportTotalPrice() {
    MaterialReport aMaterialReport = mock(MaterialReport.class);
    List<Tableware> someTableware = new ArrayList<>();
    Tableware aTableware = mock(Tableware.class);
    someTableware.add(aTableware);
    TablewareCabinet aTablewareCabinet = new TablewareCabinet(someTableware);
    willReturn(aTablewareCabinet).given(aMaterialReport).getToBuy();
    willReturn(aTablewareCabinet).given(aMaterialReport).getToWash();
    willReturn(aPrice).given(aMaterialReport).getTotalPrice();

    DateMaterialResponse response =
        dateMaterialResponseFactory.generateDateMaterialResponse(aDate, aMaterialReport);

    assertEquals(aPrice.toBigDecimal(), response.totalPrice);
  }
}
