package ca.ulaval.glo4002.reservation.event.rest.validators;

import ca.ulaval.glo4002.reservation.event.rest.requests.ConfigRequest;
import ca.ulaval.glo4002.reservation.rest.exceptions.InvalidRequestFormatException;

public class ConfigRequestValidator {
  public void validateConfigRequest(ConfigRequest configRequest)
      throws InvalidRequestFormatException {
    checkReservationPeriodFormat(configRequest);
    checkHoppeningFormat(configRequest);
  }

  private void checkReservationPeriodFormat(ConfigRequest configRequest)
      throws InvalidRequestFormatException {
    if (configRequest.reservationPeriod != null) {
      if (configRequest.reservationPeriod.beginDate == null
          || configRequest.reservationPeriod.endDate == null) {
        throw new InvalidRequestFormatException();
      }
    } else {
      throw new InvalidRequestFormatException();
    }
  }

  private void checkHoppeningFormat(ConfigRequest configRequest)
      throws InvalidRequestFormatException {
    if (configRequest.hoppeningPeriod != null) {
      if (configRequest.hoppeningPeriod.beginDate == null
          || configRequest.hoppeningPeriod.endDate == null) {
        throw new InvalidRequestFormatException();
      }
    } else {
      throw new InvalidRequestFormatException();
    }
  }
}
