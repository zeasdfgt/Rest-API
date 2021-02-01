package ca.ulaval.glo4002.reservation.event.rest.resources;

import ca.ulaval.glo4002.reservation.date.domain.DateInterval;
import ca.ulaval.glo4002.reservation.date.domain.exceptions.InvalidTimeFrameException;
import ca.ulaval.glo4002.reservation.event.application.ConfigService;
import ca.ulaval.glo4002.reservation.event.domain.EventDateConfiguration;
import ca.ulaval.glo4002.reservation.event.rest.requests.ConfigRequest;
import ca.ulaval.glo4002.reservation.event.rest.validators.ConfigRequestValidator;
import ca.ulaval.glo4002.reservation.rest.exceptions.InvalidDateException;
import ca.ulaval.glo4002.reservation.rest.exceptions.InvalidFormatException;
import ca.ulaval.glo4002.reservation.rest.exceptions.InvalidRequestFormatException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/configuration")
@Produces(MediaType.APPLICATION_JSON)
public class ConfigResource {
  private ConfigService configService;
  private ConfigRequestValidator configRequestValidator;

  public ConfigResource() {
    this.configService = new ConfigService();
    this.configRequestValidator = new ConfigRequestValidator();
  }

  public ConfigResource(
      ConfigService configService, ConfigRequestValidator configRequestValidator) {
    this.configService = configService;
    this.configRequestValidator = configRequestValidator;
  }

  @POST
  public Response editConfiguration(ConfigRequest configRequest)
      throws InvalidFormatException, InvalidTimeFrameException, InvalidDateException,
          InvalidRequestFormatException {
    configRequestValidator.validateConfigRequest(configRequest);
    DateInterval reservationPeriod = configRequest.reservationPeriod.toDateInterval();
    DateInterval hoppeningPeriod = configRequest.hoppeningPeriod.toDateInterval();
    EventDateConfiguration eventDateConfiguration =
        new EventDateConfiguration(reservationPeriod, hoppeningPeriod);

    configService.setDateConfiguration(eventDateConfiguration);

    return Response.status(HttpServletResponse.SC_OK).build();
  }
}
