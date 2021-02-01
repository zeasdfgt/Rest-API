package ca.ulaval.glo4002.reservation.tableware.domain;

import ca.ulaval.glo4002.reservation.money.Money;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class TablewareCabinet {
  private final List<Tableware> tableware;

  public TablewareCabinet(List<Tableware> tableware) {
    this.tableware = tableware;
  }

  public List<Tableware> getTablewareByType(TablewareType tablewareType) {
    List<Tableware> resultTableware = new ArrayList<>();

    for (Tableware individualTableware : tableware) {
      if (individualTableware.getType() == tablewareType) {
        resultTableware.add(individualTableware);
      }
    }
    return resultTableware;
  }

  public List<Tableware> getAllTableware() {
    return this.tableware;
  }

  public TablewareCabinet getWashableTableware(TablewareCabinet previousDayUsedTableware) {
    List<Tableware> washable = new ArrayList<>();

    HashMap<TablewareType, Integer> tablewareTypesOccurrence = new HashMap<>();

    for (Tableware previousDayTableware : previousDayUsedTableware.getAllTableware()) {
      tablewareTypesOccurrence.putIfAbsent(previousDayTableware.getType(), 0);
      Integer count = tablewareTypesOccurrence.get(previousDayTableware.getType());
      tablewareTypesOccurrence.put(previousDayTableware.getType(), ++count);
    }

    for (Tableware currentDayTableware : tableware) {
      if (tablewareTypesOccurrence.containsKey(currentDayTableware.getType())) {
        if (tablewareTypesOccurrence.get(currentDayTableware.getType()) > 0) {
          washable.add(currentDayTableware);
          Integer count = tablewareTypesOccurrence.get(currentDayTableware.getType());
          tablewareTypesOccurrence.put(currentDayTableware.getType(), --count);
        }
      }
    }
    return new TablewareCabinet(washable);
  }

  public TablewareCabinet getUnwashableTableware(TablewareCabinet previousDayUsedTableware) {
    List<Tableware> unwashableTableware = new ArrayList<>();

    HashMap<TablewareType, Integer> tablewareTypesOccurrence = new HashMap<>();

    for (Tableware previousDayTableware : previousDayUsedTableware.getAllTableware()) {
      tablewareTypesOccurrence.putIfAbsent(previousDayTableware.getType(), 0);
      Integer count = tablewareTypesOccurrence.get(previousDayTableware.getType());
      tablewareTypesOccurrence.put(previousDayTableware.getType(), ++count);
    }

    for (Tableware currentDayTableware : tableware) {
      if (tablewareTypesOccurrence.containsKey(currentDayTableware.getType())) {
        if (tablewareTypesOccurrence.get(currentDayTableware.getType()) <= 0) {
          unwashableTableware.add(currentDayTableware);
        } else {
          Integer count = tablewareTypesOccurrence.get(currentDayTableware.getType());
          tablewareTypesOccurrence.put(currentDayTableware.getType(), --count);
        }
      } else {
        unwashableTableware.add(currentDayTableware);
      }
    }
    return new TablewareCabinet(unwashableTableware);
  }

  public Money getWashingPrice() {
    int itemsPerBatch = 9;
    Money washingPricePerBatch = new Money(100);
    int batchAmount = (int) Math.ceil((double) tableware.size() / (double) itemsPerBatch);
    return washingPricePerBatch.multiply(new BigDecimal(batchAmount));
  }

  public Money getBuyingPrice() {
    Money price = new Money(0);
    for (Tableware individualTableware : tableware) {
      price = price.add(individualTableware.getPrice());
    }
    return price;
  }

  public int numberOfTableware() {
    return tableware.size();
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    TablewareCabinet that = (TablewareCabinet) object;
    return Objects.equals(tableware, that.tableware);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tableware);
  }
}
