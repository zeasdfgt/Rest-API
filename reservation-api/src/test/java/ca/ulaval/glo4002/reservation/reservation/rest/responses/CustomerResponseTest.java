package ca.ulaval.glo4002.reservation.reservation.rest.responses;

import ca.ulaval.glo4002.reservation.reservation.domain.Customer;
import ca.ulaval.glo4002.reservation.reservation.domain.Restriction;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

class CustomerResponseTest {
  private CustomerResponse response;

  @Test
  public void
      givenMultipleRestrictions_whenReturningCustomerResponse_thenRestrictionsAreInAlphabeticalOrder() {
    Customer customer = mock(Customer.class);
    Restriction aRestriction = mock(Restriction.class);
    Restriction anOtherRestriction = mock(Restriction.class);
    String lastExpectedType = "zzzzzzz";
    willReturn(lastExpectedType).given(aRestriction).getTypeToString();
    String firstExpectedType = "aaaaaaa";
    willReturn(firstExpectedType).given(anOtherRestriction).getTypeToString();
    List<Restriction> e = new ArrayList<>();
    e.add(aRestriction);
    e.add(anOtherRestriction);
    willReturn(e).given(customer).getRestrictions();

    response = new CustomerResponse(customer);

    assertEquals(firstExpectedType, response.restrictions.get(0).type);
    assertEquals(lastExpectedType, response.restrictions.get(1).type);
  }
}
