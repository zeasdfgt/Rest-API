package ca.ulaval.glo4002.reservation.tableware.domain;

import ca.ulaval.glo4002.reservation.reservation.domain.Customer;
import ca.ulaval.glo4002.reservation.reservation.domain.Restriction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

class TablewareFactoryTest {
  private final int DEFAULT_TABLEWARE_AMOUNT = 3;
  private TablewareFactory tablewareFactory;

  @BeforeEach
  public void tablewareFactoryInitialization() {
    tablewareFactory = new TablewareFactory();
  }

  @Test
  public void
      givenACustomerWithNoRestriction_whenCreatingTablewareForCustomer_thenCreatesDefaultNumberOfEachTableware() {
    Customer customer = mock(Customer.class);
    List<Restriction> noRestrictions = new ArrayList<>();
    willReturn(noRestrictions).given(customer).getRestrictions();

    List<Tableware> returnedTableware = tablewareFactory.createTablewareForCustomer(customer);

    HashMap<TablewareType, Integer> occurrences =
        countOccurrencesOfTablewareTypes(returnedTableware);

    for (TablewareType tablewareType : TablewareType.values()) {
      assertEquals(occurrences.get(tablewareType), DEFAULT_TABLEWARE_AMOUNT);
    }
  }

  @Test
  public void
      givenACustomerWithRestrictions_whenCreatingTablewareForCustomer_thenCreatesOneExtraPlateAndForkPerRestriction() {
    Customer customer = mock(Customer.class);
    List<Restriction> someRestrictions = new ArrayList<>();
    Restriction aRestriction = mock(Restriction.class);
    someRestrictions.add(aRestriction);
    willReturn(someRestrictions).given(customer).getRestrictions();
    int numberForksAndPlates = DEFAULT_TABLEWARE_AMOUNT + someRestrictions.size();

    List<Tableware> returnedTableware = tablewareFactory.createTablewareForCustomer(customer);

    HashMap<TablewareType, Integer> occurrences =
        countOccurrencesOfTablewareTypes(returnedTableware);

    assertEquals(numberForksAndPlates, occurrences.get(TablewareType.FORK));
    assertEquals(numberForksAndPlates, occurrences.get(TablewareType.PLATE));
  }

  private HashMap<TablewareType, Integer> countOccurrencesOfTablewareTypes(
      List<Tableware> tableware) {
    HashMap<TablewareType, Integer> occurrences = new HashMap<>();
    for (Tableware aTableware : tableware) {
      occurrences.putIfAbsent(aTableware.getType(), 0);
      Integer count = occurrences.get(aTableware.getType());
      occurrences.put(aTableware.getType(), ++count);
    }

    return occurrences;
  }
}
