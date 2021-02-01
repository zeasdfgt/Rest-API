package ca.ulaval.glo4002.reservation.reservation.rest.assemblers;

import ca.ulaval.glo4002.reservation.date.domain.Date;
import ca.ulaval.glo4002.reservation.ingredient.domain.exceptions.IngredientNotFoundException;
import ca.ulaval.glo4002.reservation.ingredient.domain.repositories.IngredientRepository;
import ca.ulaval.glo4002.reservation.meal.domain.repositories.MenuRepository;
import ca.ulaval.glo4002.reservation.reservation.domain.Reservation;
import ca.ulaval.glo4002.reservation.reservation.domain.ReservationNumber;
import ca.ulaval.glo4002.reservation.reservation.domain.Table;
import ca.ulaval.glo4002.reservation.reservation.domain.exceptions.AllergyConflictException;
import ca.ulaval.glo4002.reservation.reservation.domain.exceptions.InvalidReservationQuantityException;
import ca.ulaval.glo4002.reservation.reservation.domain.exceptions.TooManyPeopleException;
import ca.ulaval.glo4002.reservation.reservation.rest.requests.ReservationRequest;
import ca.ulaval.glo4002.reservation.reservation.rest.requests.TableRequest;
import ca.ulaval.glo4002.reservation.rest.exceptions.InvalidFormatException;

import java.util.ArrayList;
import java.util.List;

public class ReservationRequestAssembler {
  private final TableRequestAssembler tableRequestAssembler;

  public ReservationRequestAssembler(
      MenuRepository menuRepository, IngredientRepository ingredientRepository) {
    tableRequestAssembler = new TableRequestAssembler(menuRepository, ingredientRepository);
  }

  public ReservationRequestAssembler(TableRequestAssembler tableRequestAssembler) {
    this.tableRequestAssembler = tableRequestAssembler;
  }

  public Reservation fromRequest(ReservationRequest reservationRequest)
      throws AllergyConflictException, InvalidReservationQuantityException, InvalidFormatException,
          TooManyPeopleException, IngredientNotFoundException {
    List<Table> tables = new ArrayList<>();

    for (TableRequest tableRequest : reservationRequest.getTables()) {
      tables.add(tableRequestAssembler.fromRequest(tableRequest));
    }

    return new Reservation(
        new ReservationNumber(reservationRequest.getVendorCode()),
        reservationRequest.getVendorCode(),
        Date.parseZonedDateTime(reservationRequest.getFrom().getReservationDate()),
        Date.parseZonedDateTime(reservationRequest.getDinnerDate()),
        tables);
  }
}
