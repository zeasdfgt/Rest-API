package ca.ulaval.glo4002.reservation.reports.material.application;

import ca.ulaval.glo4002.reservation.date.domain.Date;
import ca.ulaval.glo4002.reservation.date.domain.DateInterval;
import ca.ulaval.glo4002.reservation.event.application.ConfigService;
import ca.ulaval.glo4002.reservation.event.domain.EventDay;
import ca.ulaval.glo4002.reservation.reports.exceptions.InvalidReportDateException;
import ca.ulaval.glo4002.reservation.reports.material.domain.MaterialReport;
import ca.ulaval.glo4002.reservation.reports.material.domain.MaterialReportFactory;
import ca.ulaval.glo4002.reservation.reservation.domain.repositories.ReservationRepository;
import ca.ulaval.glo4002.reservation.reservation.infrastructure.persistence.InMemoryReservationRepository;
import ca.ulaval.glo4002.reservation.tableware.domain.Tableware;

import java.util.ArrayList;
import java.util.List;

public class MaterialReportService {
  private ConfigService configService;
  private MaterialReportFactory materialReportFactory;
  private ReservationRepository reservationRepository;

  public MaterialReportService(
      ConfigService configService,
      MaterialReportFactory materialReportFactory,
      ReservationRepository reservationRepository) {
    this.configService = configService;
    this.materialReportFactory = materialReportFactory;
    this.reservationRepository = reservationRepository;
  }

  public MaterialReportService() {
    this.configService = new ConfigService();
    this.materialReportFactory = new MaterialReportFactory();
    this.reservationRepository = new InMemoryReservationRepository();
  }

  public MaterialReport generateMaterialReport(Date date) throws InvalidReportDateException {
    DateInterval dinnerDateInterval = configService.getDinnerDateInterval();
    if (!dinnerDateInterval.isWithinInterval(date)) {
      throw new InvalidReportDateException();
    }

    EventDay currentEventDay =
        new EventDay(reservationRepository.getReservationsByDinnerDate(date));
    EventDay previousEventDay = getPreviousEventDayWithReservations(date);

    List<Tableware> currentDayTableware = currentEventDay.getRequiredTableware();
    List<Tableware> previousDayTableware = previousEventDay.getRequiredTableware();

    return materialReportFactory.generateMaterialReport(previousDayTableware, currentDayTableware);
  }

  private EventDay getPreviousEventDayWithReservations(Date date) {
    DateInterval dinnerDateInterval = configService.getDinnerDateInterval();

    Date previousDateWithReservations = date.minusDays(1);
    int reservationCountForDay =
        reservationRepository.getReservationsByDinnerDate(previousDateWithReservations).size();
    boolean previousReservationWithinDinnerDateInterval =
        dinnerDateInterval.isWithinInterval(previousDateWithReservations);

    while (reservationCountForDay == 0 && previousReservationWithinDinnerDateInterval) {
      previousDateWithReservations = previousDateWithReservations.minusDays(1);
      reservationCountForDay =
          reservationRepository.getReservationsByDinnerDate(previousDateWithReservations).size();
      previousReservationWithinDinnerDateInterval =
          dinnerDateInterval.isWithinInterval(previousDateWithReservations);
    }

    if (previousReservationWithinDinnerDateInterval) {
      return new EventDay(
          reservationRepository.getReservationsByDinnerDate(previousDateWithReservations));
    } else {
      return new EventDay(new ArrayList<>());
    }
  }
}
