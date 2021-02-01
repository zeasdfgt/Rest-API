package ca.ulaval.glo4002.reservation.meal.infrastructure;

import ca.ulaval.glo4002.reservation.date.domain.Date;
import ca.ulaval.glo4002.reservation.meal.domain.Meal;
import ca.ulaval.glo4002.reservation.meal.domain.repositories.MealRepository;
import ca.ulaval.glo4002.reservation.reservation.domain.Reservation;
import ca.ulaval.glo4002.reservation.reservation.domain.repositories.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class MealFromReservationRepositoryTest {
  private MealRepository mealRepository;
  private ReservationRepository reservationRepository;
  private final Meal FIRST_MEAL = mock(Meal.class);
  private final Meal SECOND_MEAL = mock(Meal.class);

  @BeforeEach
  public void mealRepositoryInitialization() {
    reservationRepository = mock(ReservationRepository.class);
    mealRepository = new MealFromReservationRepository(reservationRepository);
  }

  @Test
  public void whenGettingAllMeals_thenReturnsEveryMeals() {
    List<Reservation> reservations = generateValidReservationsWithMeals();
    willReturn(reservations).given(reservationRepository).getAllReservations();

    List<Meal> meals = mealRepository.getAllMeals();

    assertTrue(meals.contains(FIRST_MEAL));
    assertTrue(meals.contains(SECOND_MEAL));
  }

  @Test
  public void whenGettingMealsByDate_thenReturnsMealsFromThisDate() {
    List<Reservation> reservations = generateValidReservationsWithMeals();
    Date aDinnerDate = Date.startOfDay(2150, 7, 25);
    willReturn(reservations).given(reservationRepository).getReservationsByDinnerDate(aDinnerDate);

    List<Meal> meals = mealRepository.getMealsByDate(aDinnerDate);

    verify(reservationRepository).getReservationsByDinnerDate(aDinnerDate);
    assertTrue(meals.contains(FIRST_MEAL));
    assertTrue(meals.contains(SECOND_MEAL));
  }

  private List<Reservation> generateValidReservationsWithMeals() {
    List<Reservation> reservations = new ArrayList<>();

    Reservation firstReservation = createAReservation(FIRST_MEAL);
    reservations.add(firstReservation);

    Reservation secondReservation = createAReservation(SECOND_MEAL);
    reservations.add(secondReservation);

    return reservations;
  }

  private Reservation createAReservation(Meal meal) {
    Reservation aReservation = mock(Reservation.class);
    List<Meal> firstReservationMeals = new ArrayList<>();
    firstReservationMeals.add(meal);
    willReturn(firstReservationMeals).given(aReservation).getMeals();
    return aReservation;
  }
}
