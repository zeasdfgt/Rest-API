package ca.ulaval.glo4002.reservation.reports.material.application;

import ca.ulaval.glo4002.reservation.date.domain.Date;
import ca.ulaval.glo4002.reservation.date.domain.DateInterval;
import ca.ulaval.glo4002.reservation.event.application.ConfigService;
import ca.ulaval.glo4002.reservation.event.domain.EventDateConfiguration;
import ca.ulaval.glo4002.reservation.reports.exceptions.InvalidReportDateException;
import ca.ulaval.glo4002.reservation.reports.material.domain.MaterialReportFactory;
import ca.ulaval.glo4002.reservation.reservation.domain.Reservation;
import ca.ulaval.glo4002.reservation.reservation.domain.repositories.ReservationRepository;
import ca.ulaval.glo4002.reservation.reservation.infrastructure.persistence.InMemoryReservationRepository;
import ca.ulaval.glo4002.reservation.rest.exceptions.InvalidFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class MaterialReportServiceTest {
  private final String A_DATE_DURING_DINNER = "2150-07-24";
  private final String A_DATE_TWO_DAYS_EARLIER = "2015-07-22";
  private final String A_DATE_NOT_DURING_DINNER = "2120-07-12";
  private final EventDateConfiguration AN_EVENT_DATE_CONFIGURATION = new EventDateConfiguration();
  private final DateInterval DINNER_DATE_INTERVAL =
      AN_EVENT_DATE_CONFIGURATION.getDinnerDateInterval();

  private MaterialReportService materialReportService;
  private ConfigService configService;
  private MaterialReportFactory materialReportFactory;
  private ReservationRepository reservationRepository;

  @BeforeEach
  public void materialReportServiceInitialisation() {
    configService = mock(ConfigService.class);
    materialReportFactory = mock(MaterialReportFactory.class);
    reservationRepository = mock(InMemoryReservationRepository.class);
    materialReportService =
        new MaterialReportService(configService, materialReportFactory, reservationRepository);

    willReturn(DINNER_DATE_INTERVAL).given(configService).getDinnerDateInterval();
  }

  @Test
  public void whenGeneratingMaterialReport_thenMaterialReportIsGeneratedTroughFactoryWithGivenDate()
      throws InvalidFormatException, InvalidReportDateException {
    Date dateDuringDinner = Date.parseLocalDate(A_DATE_DURING_DINNER);
    materialReportService.generateMaterialReport(dateDuringDinner);

    verify(materialReportFactory).generateMaterialReport(any(), any());
  }

  @Test
  public void
      givenADateNotDuringDinner_whenGeneratingMaterialReport_thenThrowsInvalidReportDateException()
          throws InvalidFormatException {
    Date dateNotDuringDinner = Date.parseLocalDate(A_DATE_NOT_DURING_DINNER);
    assertThrows(
        InvalidReportDateException.class,
        () -> materialReportService.generateMaterialReport(dateNotDuringDinner));
  }

  @Test
  public void
      givenReservationsMoreThanOneDayApart_whenGeneratingMaterialReport_thenNoTablewareListSentToMaterialReportAssemblerAreEmpty()
          throws InvalidFormatException, InvalidReportDateException {
    Date dateDuringDinner = Date.parseLocalDate(A_DATE_DURING_DINNER);
    setTwoReservationTwoDaysAppartInRepository();

    materialReportService.generateMaterialReport(dateDuringDinner);

    verify(materialReportFactory).generateMaterialReport(notNull(), notNull());
  }

  private void setTwoReservationTwoDaysAppartInRepository() throws InvalidFormatException {
    Date aDate = Date.parseLocalDate(A_DATE_DURING_DINNER);
    Date aDateTwoDaysEarlier = Date.parseLocalDate(A_DATE_TWO_DAYS_EARLIER);

    List<Reservation> firstDayReservations = new ArrayList<>();
    Reservation aReservation = mock(Reservation.class);
    firstDayReservations.add(aReservation);

    List<Reservation> earlierDayReservations = new ArrayList<>();
    earlierDayReservations.add(aReservation);
    earlierDayReservations.add(aReservation);

    willReturn(firstDayReservations)
        .given(reservationRepository)
        .getReservationsByDinnerDate(aDate);

    willReturn(earlierDayReservations)
        .given(reservationRepository)
        .getReservationsByDinnerDate(aDateTwoDaysEarlier);
  }
}
