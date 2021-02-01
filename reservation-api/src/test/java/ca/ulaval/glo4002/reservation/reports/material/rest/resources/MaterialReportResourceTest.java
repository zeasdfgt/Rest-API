package ca.ulaval.glo4002.reservation.reports.material.rest.resources;

import ca.ulaval.glo4002.reservation.event.domain.Event;
import ca.ulaval.glo4002.reservation.reports.exceptions.InvalidReportDateException;
import ca.ulaval.glo4002.reservation.reports.material.rest.factories.MaterialReportResponseFactory;
import ca.ulaval.glo4002.reservation.reports.material.rest.responses.MaterialReportResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class MaterialReportResourceTest {
  private final String A_DATE = "2020-07-20";
  private final String A_LATER_DATE = "2020-07-22";
  private final String AN_INVALID_DATE = "glory to the USSR";

  private Event event;
  private MaterialReportResource materialReportResource;
  private MaterialReportResponse materialReportResponse;
  private MaterialReportResponseFactory materialReportResponseFactory;

  MaterialReportResourceTest() {}

  @BeforeEach
  public void resourceInitialization() throws InvalidReportDateException {
    event = mock(Event.class);
    materialReportResponse = mock(MaterialReportResponse.class);
    materialReportResponseFactory = mock(MaterialReportResponseFactory.class);
    materialReportResource = new MaterialReportResource(event, materialReportResponseFactory);

    willReturn(materialReportResponse)
        .given(materialReportResponseFactory)
        .generateMaterialReport(any(), any());
  }

  @Test
  public void givenValidDates_whenGeneratingReport_thenReportIsGeneratedTroughEvent()
      throws InvalidReportDateException {
    materialReportResource.getMaterialReport(A_DATE, A_LATER_DATE);

    verify(materialReportResponseFactory).generateMaterialReport(any(), any());
  }

  @Test
  public void
      givenEndDateBeforeStartDate_whenGeneratingReport_thenThrowsInvalidReportDateException() {
    assertThrows(
        InvalidReportDateException.class,
        () -> materialReportResource.getMaterialReport(A_LATER_DATE, A_DATE));
  }

  @Test
  public void givenNoEndDate_whenGeneratingReport_thenThrowsInvalidReportDateException() {
    assertThrows(
        InvalidReportDateException.class,
        () -> materialReportResource.getMaterialReport(A_DATE, ""));
  }

  @Test
  public void givenNoStartDate_whenGeneratingReport_thenThrowsInvalidReportDateException() {
    assertThrows(
        InvalidReportDateException.class,
        () -> materialReportResource.getMaterialReport("", A_LATER_DATE));
  }

  @Test
  public void givenAMalformedDate_whenGeneratingReport_thenThrowsInvalidReportDateException() {
    assertThrows(
        InvalidReportDateException.class,
        () -> materialReportResource.getMaterialReport(AN_INVALID_DATE, A_LATER_DATE));
  }
}
