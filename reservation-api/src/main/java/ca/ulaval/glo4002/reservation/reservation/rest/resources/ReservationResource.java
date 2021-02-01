package ca.ulaval.glo4002.reservation.reservation.rest.resources;

import ca.ulaval.glo4002.reservation.event.domain.Event;
import ca.ulaval.glo4002.reservation.ingredient.domain.exceptions.IngredientNotFoundException;
import ca.ulaval.glo4002.reservation.ingredient.domain.repositories.IngredientRepository;
import ca.ulaval.glo4002.reservation.ingredient.infrastructure.persistence.WebApiIngredientRepository;
import ca.ulaval.glo4002.reservation.meal.domain.repositories.MenuRepository;
import ca.ulaval.glo4002.reservation.meal.infrastructure.persistence.InMemoryMenuRepository;
import ca.ulaval.glo4002.reservation.reports.chef.domain.exceptions.NoPossibleChefException;
import ca.ulaval.glo4002.reservation.reservation.domain.Reservation;
import ca.ulaval.glo4002.reservation.reservation.domain.ReservationNumber;
import ca.ulaval.glo4002.reservation.reservation.domain.exceptions.*;
import ca.ulaval.glo4002.reservation.reservation.rest.assemblers.ReservationRequestAssembler;
import ca.ulaval.glo4002.reservation.reservation.rest.requests.ReservationRequest;
import ca.ulaval.glo4002.reservation.reservation.rest.responses.ReservationResponse;
import ca.ulaval.glo4002.reservation.reservation.rest.validators.ReservationRequestValidator;
import ca.ulaval.glo4002.reservation.rest.exceptions.InvalidFormatException;
import ca.ulaval.glo4002.reservation.rest.exceptions.InvalidRequestFormatException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/reservations")
@Produces(MediaType.APPLICATION_JSON)
public class ReservationResource {
  Event event;
  ReservationRequestValidator reservationRequestValidator;
  ReservationRequestAssembler reservationRequestAssembler;
  MenuRepository menuRepository;
  IngredientRepository ingredientRepository;

  public ReservationResource() {
    this.event = new Event();
    this.reservationRequestValidator = new ReservationRequestValidator();
    this.menuRepository = new InMemoryMenuRepository();
    this.ingredientRepository = new WebApiIngredientRepository();
    this.reservationRequestAssembler =
        new ReservationRequestAssembler(menuRepository, ingredientRepository);
  }

  public ReservationResource(
      Event event,
      ReservationRequestValidator reservationValidator,
      ReservationRequestAssembler reservationRequestAssembler) {
    this.event = event;
    this.reservationRequestValidator = reservationValidator;
    this.reservationRequestAssembler = reservationRequestAssembler;
  }

  @POST
  public Response createReservation(ReservationRequest reservationRequest)
      throws ReservationNotFoundException, TomatoDateConflictException, InvalidDinnerDateException,
          InvalidReservationDateException, IngredientNotFoundException, TooManyPeopleException,
          AllergyConflictException, InvalidReservationQuantityException, InvalidFormatException,
          InvalidRequestFormatException, NoPossibleChefException {

    reservationRequestValidator.validateReservationRequest(reservationRequest);
    Reservation reservation = reservationRequestAssembler.fromRequest(reservationRequest);
    ReservationNumber reservationNumber = event.bookReservation(reservation);

    return Response.status(HttpServletResponse.SC_CREATED)
        .header("Location", "/reservations/" + reservationNumber)
        .build();
  }

  @GET
  @Path("{id}")
  public ReservationResponse getReservationById(@PathParam(value = "id") String id)
      throws ReservationNotFoundException {
    ReservationNumber reservationNumber = ReservationNumber.parseFromString(id);
    return new ReservationResponse(event.findReservation(reservationNumber));
  }
}
