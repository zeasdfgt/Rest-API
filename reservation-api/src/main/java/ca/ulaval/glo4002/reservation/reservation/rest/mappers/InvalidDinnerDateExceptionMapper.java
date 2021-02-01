package ca.ulaval.glo4002.reservation.reservation.rest.mappers;

import ca.ulaval.glo4002.reservation.reservation.domain.exceptions.InvalidDinnerDateException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Map;
import java.util.TreeMap;

@Provider
public class InvalidDinnerDateExceptionMapper
    implements ExceptionMapper<InvalidDinnerDateException> {
  private final int HTTP_STATUS_CODE = HttpServletResponse.SC_BAD_REQUEST;
  private final String ERROR_TYPE = "INVALID_DINNER_DATE";

  @Override
  public Response toResponse(InvalidDinnerDateException invalidDinnerDateException) {
    Map<String, String> responseBody = new TreeMap<>();
    String eventStartDate = invalidDinnerDateException.getEventStartDate().format("MMMM dd YYYY");
    String eventEndDate = invalidDinnerDateException.getEventEndDate().format("MMMM dd YYYY");
    String descriptionMessage =
        "Dinner date should be between " + eventStartDate + " and " + eventEndDate;
    responseBody.put("error", ERROR_TYPE);
    responseBody.put("description", descriptionMessage);

    return Response.status(HTTP_STATUS_CODE).entity(responseBody).build();
  }
}
