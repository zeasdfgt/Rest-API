package ca.ulaval.glo4002.reservation.reservation.rest.validators;

import ca.ulaval.glo4002.reservation.reservation.rest.requests.CustomerRequest;
import ca.ulaval.glo4002.reservation.reservation.rest.requests.FromRequest;
import ca.ulaval.glo4002.reservation.reservation.rest.requests.ReservationRequest;
import ca.ulaval.glo4002.reservation.reservation.rest.requests.TableRequest;
import ca.ulaval.glo4002.reservation.rest.exceptions.InvalidRequestFormatException;

import java.time.ZonedDateTime;
import java.util.List;

public class ReservationRequestValidator {
  public void validateReservationRequest(ReservationRequest reservation)
      throws InvalidRequestFormatException {
    checkValidVendorCodeFormat(reservation);
    checkValidDinnerDateFormat(reservation);
    checkValidFromFormat(reservation);
    checkValidTablesFormat(reservation);
  }

  private void checkValidVendorCodeFormat(ReservationRequest reservation)
      throws InvalidRequestFormatException {
    if (reservation.getVendorCode() == null) {
      throw new InvalidRequestFormatException();
    }
  }

  private void checkValidDinnerDateFormat(ReservationRequest reservation)
      throws InvalidRequestFormatException {
    if (reservation.getDinnerDate() == null) {
      throw new InvalidRequestFormatException();
    }

    try {
      ZonedDateTime.parse(reservation.getDinnerDate());
    } catch (Exception e) {
      throw new InvalidRequestFormatException();
    }
  }

  private void checkValidFromFormat(ReservationRequest reservation)
      throws InvalidRequestFormatException {
    if (reservation.getFrom() == null) {
      throw new InvalidRequestFormatException();
    }

    if (reservation.getFrom().getReservationDate() == null) {
      throw new InvalidRequestFormatException();
    }

    try {
      ZonedDateTime.parse(reservation.getFrom().getReservationDate());
    } catch (Exception e) {
      throw new InvalidRequestFormatException();
    }

    this.checkValidCountryFormat(reservation.getFrom());
  }

  private void checkValidCountryFormat(FromRequest fromRequest)
      throws InvalidRequestFormatException {
    if (fromRequest.getCountry() == null) {
      throw new InvalidRequestFormatException();
    }

    if (fromRequest.getCountry().getCode() == null
        || fromRequest.getCountry().getCurrency() == null
        || fromRequest.getCountry().getFullName() == null) {
      throw new InvalidRequestFormatException();
    }
  }

  private void checkValidTablesFormat(ReservationRequest reservation)
      throws InvalidRequestFormatException {
    if (reservation.getTables() == null) {
      throw new InvalidRequestFormatException();
    }

    checkValidCustomerFormat(reservation);
  }

  private void checkValidCustomerFormat(ReservationRequest reservation)
      throws InvalidRequestFormatException {
    for (TableRequest tables : reservation.getTables()) {
      List<CustomerRequest> customers = tables.getCustomers();
      if (customers == null) {
        throw new InvalidRequestFormatException();
      } else {
        checkValidCustomerRestrictionsFormat(customers);
      }
    }
  }

  private void checkValidCustomerRestrictionsFormat(List<CustomerRequest> customers)
      throws InvalidRequestFormatException {
    for (CustomerRequest customer : customers) {
      if (customer.getName() == null || customer.getRestrictions() == null) {
        throw new InvalidRequestFormatException();
      }
    }
  }
}
