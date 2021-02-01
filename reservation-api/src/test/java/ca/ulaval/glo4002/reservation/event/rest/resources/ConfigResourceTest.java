package ca.ulaval.glo4002.reservation.event.rest.resources;

import ca.ulaval.glo4002.reservation.date.domain.exceptions.InvalidTimeFrameException;
import ca.ulaval.glo4002.reservation.event.application.ConfigService;
import ca.ulaval.glo4002.reservation.event.rest.requests.ConfigRequest;
import ca.ulaval.glo4002.reservation.event.rest.requests.DatePeriodRequest;
import ca.ulaval.glo4002.reservation.event.rest.validators.ConfigRequestValidator;
import ca.ulaval.glo4002.reservation.rest.exceptions.InvalidDateException;
import ca.ulaval.glo4002.reservation.rest.exceptions.InvalidFormatException;
import ca.ulaval.glo4002.reservation.rest.exceptions.InvalidRequestFormatException;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ConfigResourceTest {
  private final String A_DATE_STRING = "2121-03-20";
  private final String A_LATER_DATE_STRING = "2122-03-20";
  private final String A_DATE_STRING_FOR_A_LATER_INTERVAL = "2122-03-21";
  private final String A_LATER_DATE_STRING_FOR_A_LATER_INTERVAL = "2122-03-25";

  @Test
  public void whenEditingEventConfiguration_thenEventConfigurationIsSet()
      throws InvalidFormatException, InvalidTimeFrameException, InvalidDateException,
          InvalidRequestFormatException {
    ConfigService configService = mock(ConfigService.class);
    ConfigRequestValidator configRequestValidator = mock(ConfigRequestValidator.class);
    ConfigResource configResource = new ConfigResource(configService, configRequestValidator);
    ConfigRequest configRequest = createConfigRequest();

    configResource.editConfiguration(configRequest);

    verify(configService).setDateConfiguration(any());
  }

  private ConfigRequest createConfigRequest() {
    DatePeriodRequest aDatePeriodRequest =
        new DatePeriodRequest(A_DATE_STRING, A_LATER_DATE_STRING);
    DatePeriodRequest aLaterDatePeriodRequest =
        new DatePeriodRequest(
            A_DATE_STRING_FOR_A_LATER_INTERVAL, A_LATER_DATE_STRING_FOR_A_LATER_INTERVAL);
    return new ConfigRequest(aDatePeriodRequest, aLaterDatePeriodRequest);
  }
}
