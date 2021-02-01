package ca.ulaval.glo4002.reservation.reports.material.domain;

import ca.ulaval.glo4002.reservation.money.Money;
import ca.ulaval.glo4002.reservation.tableware.domain.Tableware;
import ca.ulaval.glo4002.reservation.tableware.domain.TablewareCabinet;

import java.util.List;

public class MaterialReportFactory {

  public MaterialReport generateMaterialReport(
      List<Tableware> previousDayTableware, List<Tableware> currentDayTableware) {
    TablewareCabinet previousDayTablewareCabinet = new TablewareCabinet(previousDayTableware);
    TablewareCabinet currentDayTablewareCabinet = new TablewareCabinet(currentDayTableware);
    TablewareCabinet washedTablewareCabinet =
        currentDayTablewareCabinet.getWashableTableware(previousDayTablewareCabinet);
    TablewareCabinet unwashableTablewareCabinet =
        currentDayTablewareCabinet.getUnwashableTableware(previousDayTablewareCabinet);

    Money unwashableBuyingPrice = unwashableTablewareCabinet.getBuyingPrice();
    Money washingPrice = washedTablewareCabinet.getWashingPrice();
    Money totalPrice = unwashableBuyingPrice.add(washingPrice);

    return new MaterialReport(washedTablewareCabinet, unwashableTablewareCabinet, totalPrice);
  }
}
