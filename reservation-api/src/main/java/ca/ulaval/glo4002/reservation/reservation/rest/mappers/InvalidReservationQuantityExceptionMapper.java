package ca.ulaval.glo4002.reservation.reservation.rest.mappers;

import ca.ulaval.glo4002.reservation.reservation.domain.exceptions.InvalidReservationQuantityException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Map;
import java.util.TreeMap;

@Provider
public class InvalidReservationQuantityExceptionMapper
    implements ExceptionMapper<InvalidReservationQuantityException> {
  private final int HTTP_STATUS_CODE = HttpServletResponse.SC_BAD_REQUEST;
  private final String ERROR_TYPE = "INVALID_RESERVATION_QUANTITY";
  private final String DESCRIPTION_MESSAGE = "Reservations must include tables and customers";

  @Override
  public Response toResponse(
      InvalidReservationQuantityException invalidReservationQuantityException) {
    Map<String, String> responseBody = new TreeMap<>();
    responseBody.put("error", ERROR_TYPE);
    responseBody.put("description", DESCRIPTION_MESSAGE);

    return Response.status(HTTP_STATUS_CODE).entity(responseBody).build();
  }
}
