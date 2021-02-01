package ca.ulaval.glo4002.reservation.reservation.domain.validators;
/*
import ca.ulaval.glo4002.reservation.date.domain.Date;
import ca.ulaval.glo4002.reservation.date.domain.DinnerDateInterval;
import ca.ulaval.glo4002.reservation.date.domain.ReservationDateInterval;
import ca.ulaval.glo4002.reservation.reservation.domain.Reservation;
import ca.ulaval.glo4002.reservation.reservation.domain.Restriction;
import ca.ulaval.glo4002.reservation.reservation.domain.RestrictionTypes;
import ca.ulaval.glo4002.reservation.reservation.domain.Table;
import ca.ulaval.glo4002.reservation.reservation.domain.exceptions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

class ReservationValidatorTest {
  private final Date AN_INVALID_TOMATO_DATE = createDate(2150, 7, 24);
  private final long AN_ID = 15314;
  private final String VENDOR_CODE = "VENDOR_CODE";
  private final List<Reservation> A_DAY_WITHOUT_RESERVATIONS = new ArrayList<>();
  private final DinnerDateInterval DINNER_DATE_INTERVAL = new DinnerDateInterval();
  private final ReservationDateInterval RESERVATION_DATE_INTERVAL = new ReservationDateInterval();
  private Date aValidDinnerDate;
  private Date aDinnerDateTooSoon;
  private Date aValidReservationDate;
  private Date aReservationDateTooSoon;
  private ReservationValidator reservationValidator;
  private List<Table> A_VALID_TABLE;
  private Reservation A_RESERVATION_SPY;

  @BeforeEach
  void reservationSpyInitialization() {
    reservationValidator = new ReservationValidator();
    A_VALID_TABLE = createValidTable();
    aValidDinnerDate = DINNER_DATE_INTERVAL.getStartDate();
    aDinnerDateTooSoon = DINNER_DATE_INTERVAL.getStartDate().minusDays(1);
    aValidReservationDate = RESERVATION_DATE_INTERVAL.getStartDate();
    aReservationDateTooSoon = RESERVATION_DATE_INTERVAL.getStartDate().minusDays(1);

    A_RESERVATION_SPY =
        Mockito.spy(
            new Reservation(
                AN_ID, VENDOR_CODE, aValidReservationDate, aValidDinnerDate, A_VALID_TABLE));
  }

  @Test
  public void
      givenReservationWithTomatoesAndInvalidDinnerDate_whenValidating_thenThrowsTomatoDateConflictException() {
    BDDMockito.willReturn(true)
        .given(A_RESERVATION_SPY)
        .ingredientInAMeal(reservationValidator.TOMATO);

    BDDMockito.willReturn(AN_INVALID_TOMATO_DATE).given(A_RESERVATION_SPY).getDinnerDate();

    Assertions.assertThrows(
        TomatoDateConflictException.class,
        () -> reservationValidator.validate(A_RESERVATION_SPY, new ArrayList<>()));
  }

  @Test
  public void
      givenReservationWithAllergiesAndSameDayReservationsWithAllergens_whenValidating_thenThrowsAllergyConflictException() {

    List<Restriction> restrictions = new ArrayList<>();
    Restriction allergyRestriction = createAllergyRestrictionMock();
    restrictions.add(allergyRestriction);

    BDDMockito.willReturn(restrictions).given(A_RESERVATION_SPY).getCustomersRestrictions();

    Reservation sameDayReservation = Mockito.mock(Reservation.class);
    BDDMockito.willReturn(true)
        .given(sameDayReservation)
        .ingredientInAMeal(reservationValidator.ALLERGEN);

    List<Reservation> sameDayReservations = new ArrayList<>();
    sameDayReservations.add(sameDayReservation);

    Assertions.assertThrows(
        AllergyConflictException.class,
        () -> reservationValidator.validate(A_RESERVATION_SPY, sameDayReservations));
  }

  @Test
  public void
      givenReservationWithAllergenAndSameDayReservationsWithAllergy_whenValidating_thenThrowsAllergyConflictException() {

    List<Restriction> restrictions = new ArrayList<>();
    Restriction allergyRestriction = createAllergyRestrictionMock();
    restrictions.add(allergyRestriction);

    Reservation sameDayReservation = Mockito.mock(Reservation.class);
    BDDMockito.willReturn(restrictions).given(sameDayReservation).getCustomersRestrictions();

    BDDMockito.willReturn(true)
        .given(A_RESERVATION_SPY)
        .ingredientInAMeal(reservationValidator.ALLERGEN);

    List<Reservation> sameDayReservations = new ArrayList<>();
    sameDayReservations.add(sameDayReservation);

    Assertions.assertThrows(
        AllergyConflictException.class,
        () -> reservationValidator.validate(A_RESERVATION_SPY, sameDayReservations));
  }

  @Test
  public void
      givenAReservationDateOutsideReservationInterval_thenThrowsInvalidReservationDateException() {
    Reservation reservation =
        new Reservation(
            AN_ID, VENDOR_CODE, aReservationDateTooSoon, aValidDinnerDate, A_VALID_TABLE);
    Assertions.assertThrows(
        InvalidReservationDateException.class,
        () -> reservationValidator.validate(reservation, A_DAY_WITHOUT_RESERVATIONS));
  }

  @Test
  public void
      givenADinnerDateOutsideDinnerInterval_thenReservationShouldThrowsInvalidDinnerDateException() {
    Reservation reservation =
        new Reservation(
            AN_ID, VENDOR_CODE, aValidReservationDate, aDinnerDateTooSoon, A_VALID_TABLE);
    Assertions.assertThrows(
        InvalidDinnerDateException.class,
        () -> reservationValidator.validate(reservation, A_DAY_WITHOUT_RESERVATIONS));
  }

  @Test
  public void givenReservationWithoutTable_thenThrowsInvalidReservationQuantityException() {
    List<Table> emptyTablesList = new ArrayList<>();
    Reservation reservation =
        new Reservation(
            AN_ID, VENDOR_CODE, aValidReservationDate, aValidDinnerDate, emptyTablesList);
    Assertions.assertThrows(
        InvalidReservationQuantityException.class,
        () -> reservationValidator.validate(reservation, A_DAY_WITHOUT_RESERVATIONS));
  }

  @Test
  public void givenReservationWithMoreThanMaxCustomers_thenThrowsTooManyPeopleException() {
    List<Table> tables = new ArrayList<>();
    for (int i = 0; i < reservationValidator.MAX_NUMBER_OF_CUSTOMERS_PER_RESERVATION + 1; i++) {
      tables.add(createTableWithACustomer());
    }
    Reservation reservation =
        new Reservation(AN_ID, VENDOR_CODE, aValidReservationDate, aValidDinnerDate, tables);
    Assertions.assertThrows(
        TooManyPeopleException.class,
        () -> reservationValidator.validate(reservation, A_DAY_WITHOUT_RESERVATIONS));
  }

  @Test
  void givenADayWithTheMaximumAmountOfReservations_thenThrowsTooManyPeopleException() {
    List<Reservation> sameDayReservations =
        createSameDayReservations(reservationValidator.MAXIMUM_PEOPLE_IN_RESTAURANT_PER_DAY + 1);
    Reservation reservation =
        new Reservation(AN_ID, VENDOR_CODE, aValidReservationDate, aValidDinnerDate, A_VALID_TABLE);
    Assertions.assertThrows(
        TooManyPeopleException.class,
        () -> reservationValidator.validate(reservation, sameDayReservations));
  }

  private Date createDate(int year, int month, int day) {
    return Date.startOfDay(year, month, day);
  }

  private Restriction createAllergyRestrictionMock() {
    Restriction allergyRestriction = Mockito.mock(Restriction.class);
    BDDMockito.willReturn(RestrictionTypes.ALLERGIES).given(allergyRestriction).getType();

    return allergyRestriction;
  }

  private List<Table> createValidTable() {
    List<Table> tables = new ArrayList<>();
    tables.add(createTableWithACustomer());
    return tables;
  }

  private Table createTableWithACustomer() {
    Table table = Mockito.mock(Table.class);
    BDDMockito.willReturn(1).given(table).getNumberOfCustomers();
    return table;
  }

  private List<Reservation> createSameDayReservations(int numberOfReservations) {
    List<Reservation> reservations = new ArrayList<>();
    for (int i = 0; i < numberOfReservations; i++) {
      Table table = createTableWithACustomer();
      List<Table> tables = new ArrayList<>();
      tables.add(table);

      Reservation reservation =
          new Reservation(AN_ID, VENDOR_CODE, aValidReservationDate, aValidDinnerDate, tables);
      reservations.add(reservation);
    }
    return reservations;
  }
}
*/
