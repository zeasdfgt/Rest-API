package ca.ulaval.glo4002.reservation.reservation.rest.mappers;

import ca.ulaval.glo4002.reservation.reservation.domain.exceptions.InvalidReservationDateException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Map;
import java.util.TreeMap;

@Provider
public class InvalidReservationDateExceptionMapper
    implements ExceptionMapper<InvalidReservationDateException> {
  private final int HTTP_STATUS_CODE = HttpServletResponse.SC_BAD_REQUEST;
  private final String ERROR_TYPE = "INVALID_RESERVATION_DATE";

  @Override
  public Response toResponse(InvalidReservationDateException invalidReservationDateException) {
    Map<String, String> responseBody = new TreeMap<>();
    String reservationStartDate =
        invalidReservationDateException.getEventStartDate().format("MMMM dd YYYY");
    String reservationEndDate =
        invalidReservationDateException.getEventEndDate().format("MMMM dd YYYY");
    String descriptionMessage =
        "Reservation date should be between " + reservationStartDate + " and " + reservationEndDate;
    responseBody.put("error", ERROR_TYPE);
    responseBody.put("description", descriptionMessage);

    return Response.status(HTTP_STATUS_CODE).entity(responseBody).build();
  }
}
