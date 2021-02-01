package ca.ulaval.glo4002.reservation.reservation.rest.mappers;

import ca.ulaval.glo4002.reservation.reservation.domain.exceptions.TooManyPeopleException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Map;
import java.util.TreeMap;

@Provider
public class TooManyPeopleExceptionMapper implements ExceptionMapper<TooManyPeopleException> {
  private final int HTTP_STATUS_CODE = HttpServletResponse.SC_BAD_REQUEST;
  private final String ERROR_TYPE = "TOO_MANY_PEOPLE";
  private final String DESCRIPTION_MESSAGE =
      "The reservation tries to bring a number of people which does not comply with recent government laws.";

  @Override
  public Response toResponse(TooManyPeopleException tooManyPeopleException) {
    Map<String, String> responseBody = new TreeMap<>();
    responseBody.put("error", ERROR_TYPE);
    responseBody.put("description", DESCRIPTION_MESSAGE);

    return Response.status(HTTP_STATUS_CODE).entity(responseBody).build();
  }
}
