package ca.ulaval.glo4002.reservation.reservation.rest.assemblers;

import ca.ulaval.glo4002.reservation.reservation.domain.Customer;
import ca.ulaval.glo4002.reservation.reservation.domain.Table;
import ca.ulaval.glo4002.reservation.reservation.rest.requests.CustomerRequest;
import ca.ulaval.glo4002.reservation.reservation.rest.requests.TableRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

class TableRequestAssemblerTest {
  private CustomerRequestAssembler customerRequestAssembler;
  private TableRequestAssembler tableRequestAssembler;

  @BeforeEach
  public void tableRequestAssemblerInitialization() {
    customerRequestAssembler = mock(CustomerRequestAssembler.class);
    tableRequestAssembler = new TableRequestAssembler(customerRequestAssembler);
  }

  @Test
  public void whenManyCustomersOnATableRequest_thenAllCustomersAddedToTheTable() throws Exception {
    CustomerRequest aCustomerRequest = mock(CustomerRequest.class);
    CustomerRequest anotherCustomerRequest = mock(CustomerRequest.class);
    List<CustomerRequest> customerRequests =
        new ArrayList<>(Arrays.asList(aCustomerRequest, anotherCustomerRequest));

    TableRequest tableRequest = new TableRequest(customerRequests);

    Customer anExpectedCustomer = mock(Customer.class);
    Customer anotherExpectedCustomer = mock(Customer.class);
    willReturn(anExpectedCustomer).given(customerRequestAssembler).fromRequest(aCustomerRequest);
    willReturn(anotherExpectedCustomer)
        .given(customerRequestAssembler)
        .fromRequest(anotherCustomerRequest);

    Table tableResult = tableRequestAssembler.fromRequest(tableRequest);

    assertTrue(tableResult.getCustomers().contains(anExpectedCustomer));
    assertTrue(tableResult.getCustomers().contains(anotherExpectedCustomer));
    assertEquals(2, tableResult.getCustomers().size());
  }
}
