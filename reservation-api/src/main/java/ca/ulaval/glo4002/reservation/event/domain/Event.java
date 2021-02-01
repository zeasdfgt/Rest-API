package ca.ulaval.glo4002.reservation.event.domain;

import ca.ulaval.glo4002.reservation.chef.infrastructure.persistence.InMemoryChefRepository;
import ca.ulaval.glo4002.reservation.chef.repositories.ChefRepository;
import ca.ulaval.glo4002.reservation.date.domain.Date;
import ca.ulaval.glo4002.reservation.date.domain.DateInterval;
import ca.ulaval.glo4002.reservation.event.application.ConfigService;
import ca.ulaval.glo4002.reservation.reports.chef.domain.ChefAssignator;
import ca.ulaval.glo4002.reservation.reports.chef.domain.exceptions.NoPossibleChefException;
import ca.ulaval.glo4002.reservation.reservation.domain.Reservation;
import ca.ulaval.glo4002.reservation.reservation.domain.ReservationNumber;
import ca.ulaval.glo4002.reservation.reservation.domain.Restriction;
import ca.ulaval.glo4002.reservation.reservation.domain.exceptions.*;
import ca.ulaval.glo4002.reservation.reservation.domain.repositories.ReservationRepository;
import ca.ulaval.glo4002.reservation.reservation.infrastructure.persistence.InMemoryReservationRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Event {
  public final int DAYS_FOR_TOMATO_BAN = 5;
  protected static final String TOMATO = "Tomato";
  public static final String ALLERGEN = "Carrots";
  private final ReservationRepository reservationRepository;
  private final ChefRepository chefRepository;
  private final ChefAssignator chefAssignator;
  private final ConfigService configService;

  public Event() {
    this.reservationRepository = new InMemoryReservationRepository();
    this.chefRepository = new InMemoryChefRepository();
    this.chefAssignator = new ChefAssignator(this.chefRepository.getAllChefs());
    this.configService = new ConfigService();
  }

  public Event(
      ReservationRepository reservationRepository,
      ChefRepository chefRepository,
      ChefAssignator chefAssignator,
      ConfigService configService) {
    this.reservationRepository = reservationRepository;
    this.chefRepository = chefRepository;
    this.chefAssignator = chefAssignator;
    this.configService = configService;
  }

  public ReservationNumber bookReservation(Reservation reservation)
      throws ReservationNotFoundException, TomatoDateConflictException, InvalidDinnerDateException,
          InvalidReservationDateException, TooManyPeopleException, AllergyConflictException,
          NoPossibleChefException {
    List<Reservation> sameDayReservations =
        reservationRepository.getReservationsByDinnerDate(reservation.getDinnerDate());
    EventDay dayOfTheReservation = new EventDay(sameDayReservations);
    dayOfTheReservation.validateCustomersCountForDay(reservation);

    validateConflictWithChef(reservation, dayOfTheReservation);
    validateDinnerDate(reservation.getDinnerDate());
    validateReservationDate(reservation.getReservationDate());
    dayOfTheReservation.validateConflictsWithAllergies(reservation);
    validateWantTomatoesDuringRestrictedPeriod(reservation);
    reservationRepository.addReservation(reservation);

    return reservation.getReservationNumber();
  }

  public Reservation findReservation(ReservationNumber reservationNumber)
      throws ReservationNotFoundException {
    return reservationRepository.getReservationByReservationNumber(reservationNumber);
  }

  public List<Reservation> getAllReservations() {
    return reservationRepository.getAllReservations();
  }

  public List<EventDay> getAllEventDays() {

    Map<LocalDate, List<Reservation>> allReservationsPerDate =
        reservationRepository.getAllReservationsGroupedByDate();
    List<EventDay> eventDays = new ArrayList<>();
    for (LocalDate date : allReservationsPerDate.keySet()) {
      eventDays.add(new EventDay(allReservationsPerDate.get(date)));
    }
    return eventDays;
  }

  private void validateReservationDate(Date reservationDate)
      throws InvalidReservationDateException {
    DateInterval reservationDateInterval = configService.getReservationDateInterval();
    if (!reservationDateInterval.isWithinInterval(reservationDate)) {
      throw new InvalidReservationDateException();
    }
  }

  private void validateDinnerDate(Date dinnerDate) throws InvalidDinnerDateException {
    DateInterval dinnerDateInterval = configService.getDinnerDateInterval();
    if (!dinnerDateInterval.isWithinInterval(dinnerDate)) {
      throw new InvalidDinnerDateException();
    }
  }

  private void validateWantTomatoesDuringRestrictedPeriod(Reservation reservation)
      throws TomatoDateConflictException {
    DateInterval dinnerDateInterval = configService.getDinnerDateInterval();

    if (reservation.ingredientInAMeal(TOMATO)) {
      Date dinnerDate = reservation.getDinnerDate();
      Date adjustedDinnerDate = dinnerDate.minusDays(DAYS_FOR_TOMATO_BAN);

      if (!dinnerDateInterval.isWithinInterval(adjustedDinnerDate)) {
        throw new TomatoDateConflictException();
      }
    }
  }

  private void validateConflictWithChef(Reservation reservation, EventDay eventDay)
      throws NoPossibleChefException {
    List<Restriction> newDayRestrictions = eventDay.getRestrictionsForChefsValidation(reservation);
    chefAssignator.validateChefsForTheDay(newDayRestrictions);
  }
}
