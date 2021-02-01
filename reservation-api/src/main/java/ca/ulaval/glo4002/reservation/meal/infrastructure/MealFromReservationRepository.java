package ca.ulaval.glo4002.reservation.meal.infrastructure;

import ca.ulaval.glo4002.reservation.date.domain.Date;
import ca.ulaval.glo4002.reservation.meal.domain.Meal;
import ca.ulaval.glo4002.reservation.meal.domain.repositories.MealRepository;
import ca.ulaval.glo4002.reservation.reservation.domain.Reservation;
import ca.ulaval.glo4002.reservation.reservation.domain.repositories.ReservationRepository;
import ca.ulaval.glo4002.reservation.reservation.infrastructure.persistence.InMemoryReservationRepository;

import java.util.ArrayList;
import java.util.List;

public class MealFromReservationRepository implements MealRepository {
  ReservationRepository reservationRepository;

  public MealFromReservationRepository(ReservationRepository reservationRepository) {
    this.reservationRepository = reservationRepository;
  }

  public MealFromReservationRepository() {
    this.reservationRepository = new InMemoryReservationRepository();
  }

  public List<Meal> getAllMeals() {
    List<Meal> meals = new ArrayList<>();
    List<Reservation> reservations = reservationRepository.getAllReservations();
    for (Reservation reservation : reservations) {
      meals.addAll(reservation.getMeals());
    }
    return meals;
  }

  public List<Meal> getMealsByDate(Date date) {
    List<Meal> meals = new ArrayList<>();
    List<Reservation> reservations = reservationRepository.getReservationsByDinnerDate(date);
    for (Reservation reservation : reservations) {
      meals.addAll(reservation.getMeals());
    }
    return meals;
  }
}
