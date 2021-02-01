package ca.ulaval.glo4002.reservation.rest.mappers;

import ca.ulaval.glo4002.reservation.rest.exceptions.InvalidDateException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Map;
import java.util.TreeMap;

@Provider
public class InvalidDateExceptionMapper implements ExceptionMapper<InvalidDateException> {
  private final int HTTP_STATUS_CODE = HttpServletResponse.SC_BAD_REQUEST;
  private final String ERROR_TYPE = "INVALID_DATE";
  private final String DESCRIPTION_MESSAGE = "Invalide dates, please use the format yyyy-mm-dd";

  @Override
  public Response toResponse(InvalidDateException invalidDateException) {
    Map<String, String> responseBody = new TreeMap<>();
    responseBody.put("error", ERROR_TYPE);
    responseBody.put("description", DESCRIPTION_MESSAGE);

    return Response.status(HTTP_STATUS_CODE).entity(responseBody).build();
  }
}
