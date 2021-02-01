package ca.ulaval.glo4002.reservation.reports.rest.mappers;

import ca.ulaval.glo4002.reservation.reports.exceptions.InvalidReportDateException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Map;
import java.util.TreeMap;

@Provider
public class InvalidReportDateExceptionMapper
    implements ExceptionMapper<InvalidReportDateException> {
  private final int HTTP_STATUS_CODE = HttpServletResponse.SC_BAD_REQUEST;
  private final String ERROR_TYPE = "INVALID_DATE";

  @Override
  public Response toResponse(InvalidReportDateException invalidReportDateException) {
    Map<String, String> responseBody = new TreeMap<>();
    responseBody.put("error", ERROR_TYPE);
    String eventStartDate = invalidReportDateException.getEventStartDate().format("MMMM dd YYYY");
    String eventEndDate = invalidReportDateException.getEventEndDate().format("MMMM dd YYYY");
    String descriptionMessage =
        "Dates should be between "
            + eventStartDate
            + " and "
            + eventEndDate
            + " and must be specified.";
    responseBody.put("description", descriptionMessage);

    return Response.status(HTTP_STATUS_CODE).entity(responseBody).build();
  }
}
