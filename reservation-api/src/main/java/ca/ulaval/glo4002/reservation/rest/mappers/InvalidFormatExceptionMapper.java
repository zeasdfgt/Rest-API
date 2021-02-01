package ca.ulaval.glo4002.reservation.rest.mappers;

import ca.ulaval.glo4002.reservation.rest.exceptions.InvalidFormatException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Map;
import java.util.TreeMap;

@Provider
public class InvalidFormatExceptionMapper implements ExceptionMapper<InvalidFormatException> {
  private final int HTTP_STATUS_CODE = HttpServletResponse.SC_BAD_REQUEST;
  private final String ERROR_TYPE = "INVALID_FORMAT";
  private final String DESCRIPTION_MESSAGE = "Invalid Format";

  @Override
  public Response toResponse(InvalidFormatException invalidRequestFormatException) {
    Map<String, String> responseBody = new TreeMap<>();
    responseBody.put("error", ERROR_TYPE);
    responseBody.put("description", DESCRIPTION_MESSAGE);

    return Response.status(HTTP_STATUS_CODE).entity(responseBody).build();
  }
}
