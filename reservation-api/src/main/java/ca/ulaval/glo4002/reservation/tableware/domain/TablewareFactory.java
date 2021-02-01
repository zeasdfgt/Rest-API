package ca.ulaval.glo4002.reservation.tableware.domain;

import ca.ulaval.glo4002.reservation.money.Money;
import ca.ulaval.glo4002.reservation.reservation.domain.Customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TablewareFactory {
  private static HashMap<TablewareType, Money> prices;
  private final int DEFAULT_TABLEWARE_AMOUNT = 3;

  public TablewareFactory() {
    if (prices == null) {
      initialiseTablewareTypePriceMap();
    }
  }

  public List<Tableware> createTablewareForCustomer(Customer customer) {
    List<Tableware> requiredTableware = getDefaultTableware();
    for (int i = 0; i < customer.getRestrictions().size(); i++) {
      requiredTableware.add(new Tableware(TablewareType.PLATE, prices.get(TablewareType.PLATE)));
      requiredTableware.add(new Tableware(TablewareType.FORK, prices.get(TablewareType.FORK)));
    }
    return requiredTableware;
  }

  private void initialiseTablewareTypePriceMap() {
    prices = new HashMap<>();
    prices.put(TablewareType.FORK, new Money(20));
    prices.put(TablewareType.SPOON, new Money(20));
    prices.put(TablewareType.KNIFE, new Money(20));
    prices.put(TablewareType.BOWL, new Money(170));
    prices.put(TablewareType.PLATE, new Money(170));
  }

  private List<Tableware> getDefaultTableware() {
    List<Tableware> tableware = new ArrayList<>();

    for (int i = 0; i < DEFAULT_TABLEWARE_AMOUNT; i++) {
      tableware.add(new Tableware(TablewareType.FORK, prices.get(TablewareType.FORK)));
      tableware.add(new Tableware(TablewareType.SPOON, prices.get(TablewareType.SPOON)));
      tableware.add(new Tableware(TablewareType.KNIFE, prices.get(TablewareType.KNIFE)));
      tableware.add(new Tableware(TablewareType.PLATE, prices.get(TablewareType.PLATE)));
      tableware.add(new Tableware(TablewareType.BOWL, prices.get(TablewareType.BOWL)));
    }

    return tableware;
  }
}
