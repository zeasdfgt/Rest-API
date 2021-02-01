package ca.ulaval.glo4002.reservation.reservation.rest.validators;

import ca.ulaval.glo4002.reservation.reservation.rest.requests.*;
import ca.ulaval.glo4002.reservation.rest.exceptions.InvalidRequestFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ReservationRequestValidatorTest {
  private final long A_VALID_ID = 420;
  private final String A_VALID_VENDOR_CODE = "TEAM";
  private final String A_VALID_DINNER_DATE = "2150-07-21T15:23:20.142Z";
  private final String A_VALID_COUNTRY_CODE = "CA";
  private final String A_VALID_COUNTRY_FULLNAME = "CANADA";
  private final String A_VALID_COUNTRY_CURRENCY = "CAD";
  private final String A_VALID_RESERVATION_DATE = "2150-05-21T15:23:20.142Z";
  private final String A_VALID_CUSTOMER_NAME = "TEST";
  private List<String> A_VALID_RESTRICTIONS_LIST;
  private CustomerRequest A_VALID_CUSTOMER;
  private List<CustomerRequest> A_VALID_CUSTOMERS_LIST;
  private TableRequest A_VALID_TABLE;
  private List<TableRequest> A_VALID_TABLES_LIST;
  private BigDecimal A_VALID_PRICE;
  private ReservationRequestValidator reservationValidator;
  private CountryRequest A_VALID_COUNTRY_REQUEST;
  private FromRequest A_VALID_FROM_REQUEST;

  @BeforeEach
  public void reservationValidatorInitialization() {
    this.A_VALID_COUNTRY_REQUEST =
        new CountryRequest(
            A_VALID_COUNTRY_CODE, A_VALID_COUNTRY_FULLNAME, A_VALID_COUNTRY_CURRENCY);
    this.A_VALID_FROM_REQUEST = new FromRequest(A_VALID_COUNTRY_REQUEST, A_VALID_RESERVATION_DATE);
    this.A_VALID_RESTRICTIONS_LIST = new ArrayList<>();
    this.A_VALID_CUSTOMER = new CustomerRequest(A_VALID_CUSTOMER_NAME, A_VALID_RESTRICTIONS_LIST);
    this.A_VALID_CUSTOMERS_LIST = new ArrayList<>();
    this.A_VALID_CUSTOMERS_LIST.add(A_VALID_CUSTOMER);
    this.A_VALID_TABLES_LIST = new ArrayList<>();
    this.A_VALID_TABLE = new TableRequest(A_VALID_CUSTOMERS_LIST);
    this.A_VALID_TABLES_LIST.add(A_VALID_TABLE);
    this.A_VALID_PRICE = new BigDecimal(420);

    this.reservationValidator = new ReservationRequestValidator();
  }

  @Test
  public void
      givenAReservationRequestWithEmptyVendorCode_whenValidatingReservation_thenThrowsInvalidFormatException() {
    String anEmptyVendorCode = null;

    ReservationRequest reservationRequest =
        new ReservationRequest(
            A_VALID_ID,
            anEmptyVendorCode,
            A_VALID_DINNER_DATE,
            A_VALID_FROM_REQUEST,
            A_VALID_TABLES_LIST,
            A_VALID_PRICE);

    assertThrows(
        InvalidRequestFormatException.class,
        () -> this.reservationValidator.validateReservationRequest(reservationRequest));
  }

  @Test
  public void
      givenAReservationRequestWithEmptyDinnerDate_whenValidatingReservation_thenThrowsInvalidFormatException() {
    String anEmptyDinnerDate = null;

    ReservationRequest reservationRequest =
        new ReservationRequest(
            A_VALID_ID,
            A_VALID_VENDOR_CODE,
            anEmptyDinnerDate,
            A_VALID_FROM_REQUEST,
            A_VALID_TABLES_LIST,
            A_VALID_PRICE);

    assertThrows(
        InvalidRequestFormatException.class,
        () -> this.reservationValidator.validateReservationRequest(reservationRequest));
  }

  @Test
  public void
      givenAReservationRequestWithInvalidDinnerDate_whenValidatingReservation_thenThrowsInvalidFormatException() {
    String anInvalidDinnerDate = "INVALID_DINNER_DATE";

    ReservationRequest reservationRequest =
        new ReservationRequest(
            A_VALID_ID,
            A_VALID_VENDOR_CODE,
            anInvalidDinnerDate,
            A_VALID_FROM_REQUEST,
            A_VALID_TABLES_LIST,
            A_VALID_PRICE);

    assertThrows(
        InvalidRequestFormatException.class,
        () -> this.reservationValidator.validateReservationRequest(reservationRequest));
  }

  @Test
  public void
      givenAReservationRequestWithEmptyFromRequest_whenValidatingReservation_thenThrowsInvalidFormatException() {
    FromRequest anEmptyFromRequest = null;

    ReservationRequest reservationRequest =
        new ReservationRequest(
            A_VALID_ID,
            A_VALID_VENDOR_CODE,
            A_VALID_DINNER_DATE,
            anEmptyFromRequest,
            A_VALID_TABLES_LIST,
            A_VALID_PRICE);

    assertThrows(
        InvalidRequestFormatException.class,
        () -> this.reservationValidator.validateReservationRequest(reservationRequest));
  }

  @Test
  public void
      givenAReservationRequestWithEmptyReservationDate_whenValidatingReservation_thenThrowsInvalidFormatException() {
    String anEmptyReservationDate = null;
    FromRequest anInvalidFromRequest =
        new FromRequest(A_VALID_COUNTRY_REQUEST, anEmptyReservationDate);

    ReservationRequest reservationRequest =
        new ReservationRequest(
            A_VALID_ID,
            A_VALID_VENDOR_CODE,
            A_VALID_DINNER_DATE,
            anInvalidFromRequest,
            A_VALID_TABLES_LIST,
            A_VALID_PRICE);

    assertThrows(
        InvalidRequestFormatException.class,
        () -> this.reservationValidator.validateReservationRequest(reservationRequest));
  }

  @Test
  public void
      givenAReservationRequestWithInvalidReservationDate_whenValidatingReservation_thenThrowsInvalidFormatException() {
    String anInvalidReservationDate = "AN_INVALID_DATE";
    FromRequest anInvalidFromRequest =
        new FromRequest(A_VALID_COUNTRY_REQUEST, anInvalidReservationDate);

    ReservationRequest reservationRequest =
        new ReservationRequest(
            A_VALID_ID,
            A_VALID_VENDOR_CODE,
            A_VALID_DINNER_DATE,
            anInvalidFromRequest,
            A_VALID_TABLES_LIST,
            A_VALID_PRICE);

    assertThrows(
        InvalidRequestFormatException.class,
        () -> this.reservationValidator.validateReservationRequest(reservationRequest));
  }

  @Test
  public void
      givenAReservationRequestWithEmptyCountry_whenValidatingReservation_thenThrowsInvalidFormatException() {
    CountryRequest anEmptyCountryRequest = null;
    FromRequest anInvalidFromRequest =
        new FromRequest(anEmptyCountryRequest, A_VALID_RESERVATION_DATE);

    ReservationRequest reservationRequest =
        new ReservationRequest(
            A_VALID_ID,
            A_VALID_VENDOR_CODE,
            A_VALID_DINNER_DATE,
            anInvalidFromRequest,
            A_VALID_TABLES_LIST,
            A_VALID_PRICE);

    assertThrows(
        InvalidRequestFormatException.class,
        () -> this.reservationValidator.validateReservationRequest(reservationRequest));
  }

  @Test
  public void
      givenAReservationRequestWithEmptyCountryCode_whenValidatingReservation_thenThrowsInvalidFormatException() {
    String anEmptyCountryCode = null;
    CountryRequest anEmptyCodeCountryRequest =
        new CountryRequest(anEmptyCountryCode, A_VALID_COUNTRY_FULLNAME, A_VALID_COUNTRY_CURRENCY);
    FromRequest anInvalidFromRequest =
        new FromRequest(anEmptyCodeCountryRequest, A_VALID_RESERVATION_DATE);

    ReservationRequest reservationRequest =
        new ReservationRequest(
            A_VALID_ID,
            A_VALID_VENDOR_CODE,
            A_VALID_DINNER_DATE,
            anInvalidFromRequest,
            A_VALID_TABLES_LIST,
            A_VALID_PRICE);

    assertThrows(
        InvalidRequestFormatException.class,
        () -> this.reservationValidator.validateReservationRequest(reservationRequest));
  }

  @Test
  public void
      givenAReservationRequestWithEmptyCountryFullName_whenValidatingReservation_thenThrowsInvalidFormatException() {
    String anEmptyCountryFullName = null;
    CountryRequest anEmptyCodeCountryRequest =
        new CountryRequest(A_VALID_COUNTRY_CODE, anEmptyCountryFullName, A_VALID_COUNTRY_CURRENCY);
    FromRequest anInvalidFromRequest =
        new FromRequest(anEmptyCodeCountryRequest, A_VALID_RESERVATION_DATE);

    ReservationRequest reservationRequest =
        new ReservationRequest(
            A_VALID_ID,
            A_VALID_VENDOR_CODE,
            A_VALID_DINNER_DATE,
            anInvalidFromRequest,
            A_VALID_TABLES_LIST,
            A_VALID_PRICE);

    assertThrows(
        InvalidRequestFormatException.class,
        () -> this.reservationValidator.validateReservationRequest(reservationRequest));
  }

  @Test
  public void
      givenAReservationRequestWithEmptyCountryCurrency_whenValidatingReservation_thenThrowsInvalidFormatException() {
    String anEmptyCountryCurrency = null;
    CountryRequest anEmptyCodeCountryRequest =
        new CountryRequest(A_VALID_COUNTRY_CODE, A_VALID_COUNTRY_FULLNAME, anEmptyCountryCurrency);
    FromRequest anInvalidFromRequest =
        new FromRequest(anEmptyCodeCountryRequest, A_VALID_RESERVATION_DATE);

    ReservationRequest reservationRequest =
        new ReservationRequest(
            A_VALID_ID,
            A_VALID_VENDOR_CODE,
            A_VALID_DINNER_DATE,
            anInvalidFromRequest,
            A_VALID_TABLES_LIST,
            A_VALID_PRICE);

    assertThrows(
        InvalidRequestFormatException.class,
        () -> this.reservationValidator.validateReservationRequest(reservationRequest));
  }

  @Test
  public void
      givenAReservationRequestWithEmptyTables_whenValidatingReservation_thenThrowsInvalidFormatException() {
    ArrayList<TableRequest> anEmptyTableRequest = null;

    ReservationRequest reservationRequest =
        new ReservationRequest(
            A_VALID_ID,
            A_VALID_VENDOR_CODE,
            A_VALID_DINNER_DATE,
            A_VALID_FROM_REQUEST,
            anEmptyTableRequest,
            A_VALID_PRICE);

    assertThrows(
        InvalidRequestFormatException.class,
        () -> this.reservationValidator.validateReservationRequest(reservationRequest));
  }

  @Test
  public void
      givenAReservationRequestWithEmptyCustomers_whenValidatingReservation_thenThrowsInvalidFormatException() {
    ArrayList<CustomerRequest> anEmptyCustomersList = null;
    TableRequest anInvalidTable = new TableRequest(anEmptyCustomersList);
    List<TableRequest> anInvalidTableList = new ArrayList<>();
    anInvalidTableList.add(anInvalidTable);

    ReservationRequest reservationRequest =
        new ReservationRequest(
            A_VALID_ID,
            A_VALID_VENDOR_CODE,
            A_VALID_DINNER_DATE,
            A_VALID_FROM_REQUEST,
            anInvalidTableList,
            A_VALID_PRICE);

    assertThrows(
        InvalidRequestFormatException.class,
        () -> this.reservationValidator.validateReservationRequest(reservationRequest));
  }

  @Test
  public void
      givenAReservationRequestWithEmptyCustomerName_whenValidatingReservation_thenThrowsInvalidFormatException() {
    CustomerRequest aCustomerWithAnEmptyName = new CustomerRequest(null, A_VALID_RESTRICTIONS_LIST);
    List<CustomerRequest> anInvalidCustomerList = new ArrayList<>();
    anInvalidCustomerList.add(aCustomerWithAnEmptyName);
    TableRequest anInvalidTable = new TableRequest(anInvalidCustomerList);
    List<TableRequest> anInvalidTableList = new ArrayList<>();
    anInvalidTableList.add(anInvalidTable);

    ReservationRequest reservationRequest =
        new ReservationRequest(
            A_VALID_ID,
            A_VALID_VENDOR_CODE,
            A_VALID_DINNER_DATE,
            A_VALID_FROM_REQUEST,
            anInvalidTableList,
            A_VALID_PRICE);

    assertThrows(
        InvalidRequestFormatException.class,
        () -> this.reservationValidator.validateReservationRequest(reservationRequest));
  }

  @Test
  public void
      givenAReservationRequestWithEmptyCustomerRestrictions_whenValidatingReservation_thenThrowsInvalidFormatException() {
    CustomerRequest aCustomerWithAnEmptyRestrictions =
        new CustomerRequest(A_VALID_CUSTOMER_NAME, null);
    List<CustomerRequest> anInvalidCustomerList = new ArrayList<>();
    anInvalidCustomerList.add(aCustomerWithAnEmptyRestrictions);
    TableRequest anInvalidTable = new TableRequest(anInvalidCustomerList);
    List<TableRequest> anInvalidTableList = new ArrayList<>();
    anInvalidTableList.add(anInvalidTable);

    ReservationRequest reservationRequest =
        new ReservationRequest(
            A_VALID_ID,
            A_VALID_VENDOR_CODE,
            A_VALID_DINNER_DATE,
            A_VALID_FROM_REQUEST,
            anInvalidTableList,
            A_VALID_PRICE);

    assertThrows(
        InvalidRequestFormatException.class,
        () -> this.reservationValidator.validateReservationRequest(reservationRequest));
  }
}
