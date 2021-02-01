package ca.ulaval.glo4002.reservation.reservation.rest.mappers;

import ca.ulaval.glo4002.reservation.reservation.domain.exceptions.ReservationNotFoundException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Map;
import java.util.TreeMap;

@Provider
public class ReservationNotFoundExceptionMapper
    implements ExceptionMapper<ReservationNotFoundException> {
  private final int HTTP_STATUS_CODE = HttpServletResponse.SC_NOT_FOUND;
  private final String ERROR_TYPE = "RESERVATION_NOT_FOUND";

  @Override
  public Response toResponse(ReservationNotFoundException reservationNotFoundException) {
    Map<String, String> responseBody = new TreeMap<>();
    responseBody.put("error", ERROR_TYPE);
    String notFoundReservationNumber = reservationNotFoundException.getReservationNumber();
    String descriptionMessage =
        "Reservation with number " + notFoundReservationNumber + " not found";
    responseBody.put("description", descriptionMessage);

    return Response.status(HTTP_STATUS_CODE).entity(responseBody).build();
  }
}
