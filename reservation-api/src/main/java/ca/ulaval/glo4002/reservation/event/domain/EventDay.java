package ca.ulaval.glo4002.reservation.event.domain;

import ca.ulaval.glo4002.reservation.date.domain.Date;
import ca.ulaval.glo4002.reservation.reservation.domain.Customer;
import ca.ulaval.glo4002.reservation.reservation.domain.Reservation;
import ca.ulaval.glo4002.reservation.reservation.domain.Restriction;
import ca.ulaval.glo4002.reservation.reservation.domain.RestrictionType;
import ca.ulaval.glo4002.reservation.reservation.domain.exceptions.AllergyConflictException;
import ca.ulaval.glo4002.reservation.reservation.domain.exceptions.TooManyPeopleException;
import ca.ulaval.glo4002.reservation.reservation.domain.factories.RestrictionFactory;
import ca.ulaval.glo4002.reservation.tableware.domain.Tableware;
import ca.ulaval.glo4002.reservation.tableware.domain.TablewareFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class EventDay {
  public final int MAXIMUM_PEOPLE_IN_RESTAURANT_PER_DAY = 42;
  private TablewareFactory tablewareFactory;
  private RestrictionFactory restrictionFactory;
  private List<Reservation> reservations;

  public EventDay(List<Reservation> reservations) {
    this.reservations = reservations;
    this.tablewareFactory = new TablewareFactory();
    this.restrictionFactory = new RestrictionFactory();
  }

  public EventDay(List<Reservation> reservations, TablewareFactory tablewareFactory) {
    this.reservations = reservations;
    this.tablewareFactory = tablewareFactory;
  }

  public List<Restriction> getRestrictionsForChefs() {
    List<Restriction> restrictions = new ArrayList<>();
    for (Reservation reservation : reservations) {
      for (Customer customer : reservation.getCustomers()) {
        restrictions.addAll(customer.getRestrictions());
        if (customer.getRestrictions().isEmpty()) {
          restrictions.add(restrictionFactory.createRestriction(RestrictionType.NONE));
        }
      }
    }
    return restrictions;
  }

  public List<Restriction> getRestrictionsForChefsValidation(Reservation newReservation) {
    List<Restriction> restrictions = new ArrayList<>();
    restrictions.addAll(getRestrictionsForChefs());
    for (Customer customer : newReservation.getCustomers()) {
      restrictions.addAll(customer.getRestrictions());
      if (customer.getRestrictions().isEmpty()) {
        restrictions.add(restrictionFactory.createRestriction(RestrictionType.NONE));
      }
    }
    return restrictions;
  }

  public Date getDate() {
    return reservations.get(0).getDinnerDate();
  }

  public int getNumberOfCustomersForDay() {
    int total = 0;
    for (Reservation reservation : reservations) {
      total += reservation.getNumberOfCustomers();
    }
    return total;
  }

  public void validateCustomersCountForDay(Reservation reservation) throws TooManyPeopleException {
    int totalNumberOfCustomers = reservation.getNumberOfCustomers() + getNumberOfCustomersForDay();

    if (totalNumberOfCustomers > MAXIMUM_PEOPLE_IN_RESTAURANT_PER_DAY) {
      throw new TooManyPeopleException();
    }
  }

  public void validateConflictsWithAllergies(Reservation reservation)
      throws AllergyConflictException {

    List<RestrictionType> sameDayReservationsRestrictions = getRestrictionTypesFromReservations();

    if (sameDayReservationsRestrictions.contains(RestrictionType.ALLERGIES)) {
      if (reservation.ingredientInAMeal(Event.ALLERGEN)) {
        throw new AllergyConflictException();
      }
    }

    List<Restriction> reservationRestrictions = reservation.getUniqueReservationRestrictions();
    List<RestrictionType> reservationRestrictionTypes =
        getRestrictionTypesFromRestrictions(reservationRestrictions);

    if (reservationRestrictionTypes.contains(RestrictionType.ALLERGIES)) {
      for (Reservation sameDayReservation : reservations) {
        if (sameDayReservation.ingredientInAMeal(Event.ALLERGEN)) {
          throw new AllergyConflictException();
        }
      }
    }
  }

  public List<Tableware> getRequiredTableware() {
    ArrayList<Tableware> requiredTableware = new ArrayList<>();
    for (Reservation reservation : reservations) {
      List<Customer> reservationCustomers = reservation.getCustomers();
      for (Customer customer : reservationCustomers) {
        requiredTableware.addAll(tablewareFactory.createTablewareForCustomer(customer));
      }
    }
    return requiredTableware;
  }

  private List<RestrictionType> getRestrictionTypesFromReservations() {
    HashMap<RestrictionType, Restriction> customersRestrictions = new HashMap<>();

    for (Reservation reservation : reservations) {
      for (Restriction restriction : reservation.getUniqueReservationRestrictions()) {
        customersRestrictions.putIfAbsent(restriction.getType(), restriction);
      }
    }

    return new ArrayList<>(customersRestrictions.keySet());
  }

  private List<RestrictionType> getRestrictionTypesFromRestrictions(
      List<Restriction> restrictions) {
    return restrictions.stream().map(Restriction::getType).collect(Collectors.toList());
  }
}
