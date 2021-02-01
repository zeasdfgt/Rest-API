package ca.ulaval.glo4002.reservation.reports.material.rest.factories;

import ca.ulaval.glo4002.reservation.date.domain.Date;
import ca.ulaval.glo4002.reservation.date.domain.DateInterval;
import ca.ulaval.glo4002.reservation.event.domain.Event;
import ca.ulaval.glo4002.reservation.reports.exceptions.InvalidReportDateException;
import ca.ulaval.glo4002.reservation.reports.material.application.MaterialReportService;
import ca.ulaval.glo4002.reservation.reports.material.domain.MaterialReport;
import ca.ulaval.glo4002.reservation.reports.material.rest.responses.DateMaterialResponse;
import ca.ulaval.glo4002.reservation.rest.exceptions.InvalidFormatException;
import ca.ulaval.glo4002.reservation.tableware.domain.Tableware;
import ca.ulaval.glo4002.reservation.tableware.domain.TablewareCabinet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.*;

class MaterialReportResponseFactoryTest {
  private final String A_START_DATE = "2150-07-21";
  private final String AN_END_DATE = "2150-07-24";
  private MaterialReportResponseFactory materialReportResponseFactory;
  private DateMaterialResponseFactory dateMaterialResponseFactory;
  private DateInterval aDateInterval;
  private Event anEvent;
  private MaterialReportService materialReportService;

  @BeforeEach
  public void materialReportResponseFactoryInitialization() throws InvalidFormatException {
    dateMaterialResponseFactory = mock(DateMaterialResponseFactory.class);
    materialReportService = mock(MaterialReportService.class);
    willReturn(mock(DateMaterialResponse.class))
        .given(dateMaterialResponseFactory)
        .generateDateMaterialResponse(any(), any());
    materialReportResponseFactory =
        new MaterialReportResponseFactory(dateMaterialResponseFactory, materialReportService);

    aDateInterval =
        new DateInterval(Date.parseLocalDate(A_START_DATE), Date.parseLocalDate(AN_END_DATE));

    anEvent = mock(Event.class);
  }

  @Test
  public void
      givenMaterialReportsWithTableware_whenGeneratingMaterialReport_thenADateMaterialResponseIsGeneratedPerDateWithReservationsInInterval()
          throws InvalidReportDateException {
    MaterialReport aMaterialReport = generateMaterialReportWithTableware();
    willReturn(aMaterialReport).given(materialReportService).generateMaterialReport(any());

    materialReportResponseFactory.generateMaterialReport(anEvent, aDateInterval);

    int numberOfExpectedDateMaterial = 4;
    verify(dateMaterialResponseFactory, times(numberOfExpectedDateMaterial))
        .generateDateMaterialResponse(any(), any());
  }

  @Test
  public void
      givenMaterialReportsWithoutTableware_whenGeneratingMaterialReportResponse_thenNoDateMaterialResponsesAreGenerated()
          throws InvalidReportDateException {
    MaterialReport aMaterialReport = generateMaterialReportWithoutTableware();
    willReturn(aMaterialReport).given(materialReportService).generateMaterialReport(any());

    materialReportResponseFactory.generateMaterialReport(anEvent, aDateInterval);

    verify(dateMaterialResponseFactory, never()).generateDateMaterialResponse(any(), any());
  }

  private MaterialReport generateMaterialReportWithTableware() {
    MaterialReport aMaterialReport = mock(MaterialReport.class);
    List<Tableware> someTableware = new ArrayList<>();
    Tableware aTableware = mock(Tableware.class);
    someTableware.add(aTableware);
    TablewareCabinet aTablewareCabinet = new TablewareCabinet(someTableware);
    willReturn(aTablewareCabinet).given(aMaterialReport).getToBuy();
    willReturn(aTablewareCabinet).given(aMaterialReport).getToWash();

    return aMaterialReport;
  }

  private MaterialReport generateMaterialReportWithoutTableware() {
    MaterialReport aMaterialReport = mock(MaterialReport.class);
    TablewareCabinet anEmptyTablewareCabinet = new TablewareCabinet(new ArrayList<>());
    willReturn(anEmptyTablewareCabinet).given(aMaterialReport).getToBuy();
    willReturn(anEmptyTablewareCabinet).given(aMaterialReport).getToWash();

    return aMaterialReport;
  }
}
