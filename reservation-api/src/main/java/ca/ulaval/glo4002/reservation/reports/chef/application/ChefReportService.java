package ca.ulaval.glo4002.reservation.reports.chef.application;

import ca.ulaval.glo4002.reservation.chef.Chef;
import ca.ulaval.glo4002.reservation.chef.infrastructure.persistence.InMemoryChefRepository;
import ca.ulaval.glo4002.reservation.chef.repositories.ChefRepository;
import ca.ulaval.glo4002.reservation.event.domain.Event;
import ca.ulaval.glo4002.reservation.event.domain.EventDay;
import ca.ulaval.glo4002.reservation.reports.chef.domain.ChefAssignator;
import ca.ulaval.glo4002.reservation.reports.chef.domain.DailyChefReport;
import ca.ulaval.glo4002.reservation.reports.chef.domain.exceptions.NoPossibleChefException;
import ca.ulaval.glo4002.reservation.reports.chef.domain.factories.DailyChefReportFactory;
import ca.ulaval.glo4002.reservation.reservation.domain.Reservation;
import ca.ulaval.glo4002.reservation.reservation.domain.Restriction;

import java.util.List;

public class ChefReportService {
  private ChefRepository chefRepository;
  private DailyChefReportFactory dailyChefReportFactory;
  private Event event;
  private ChefAssignator chefAssignator;

  public ChefReportService(
      ChefRepository chefRepository,
      DailyChefReportFactory dailyChefReportFactory,
      Event event,
      ChefAssignator chefAssignator) {
    this.chefRepository = chefRepository;
    this.dailyChefReportFactory = dailyChefReportFactory;
    this.event = event;
    this.chefAssignator = chefAssignator;
  }

  public ChefReportService() {
    this.chefRepository = new InMemoryChefRepository();
    this.dailyChefReportFactory = new DailyChefReportFactory();
    this.event = new Event();
    this.chefAssignator = new ChefAssignator(this.chefRepository.getAllChefs());
  }

  public List<DailyChefReport> getChefReports() {
    List<Chef> chefsOfTheEvent = chefRepository.getAllChefs();
    return dailyChefReportFactory.generateChefReports(event.getAllEventDays(), chefsOfTheEvent);
  }

  private void validateConflictWithChef(Reservation reservation, EventDay eventDay)
      throws NoPossibleChefException {
    List<Restriction> newDayRestrictions = eventDay.getRestrictionsForChefsValidation(reservation);
    chefAssignator.validateChefsForTheDay(newDayRestrictions);
  }
}
