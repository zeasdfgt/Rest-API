package ca.ulaval.glo4002.reservation.reports.material.rest.factories;

import ca.ulaval.glo4002.reservation.date.domain.Date;
import ca.ulaval.glo4002.reservation.reports.material.domain.MaterialReport;
import ca.ulaval.glo4002.reservation.reports.material.rest.responses.DateMaterialResponse;
import ca.ulaval.glo4002.reservation.reports.material.rest.responses.TablewareResponse;
import ca.ulaval.glo4002.reservation.tableware.domain.TablewareCabinet;
import ca.ulaval.glo4002.reservation.tableware.domain.TablewareType;

import java.math.BigDecimal;

public class DateMaterialResponseFactory {

  public DateMaterialResponse generateDateMaterialResponse(
      Date date, MaterialReport materialReport) {
    TablewareResponse boughtTablewareResponse = null;
    if (materialReport.getToBuy().numberOfTableware() > 0) {
      boughtTablewareResponse = generateTablewareResponse(materialReport.getToBuy());
    }

    TablewareResponse cleanedTablewareResponse = null;
    if (materialReport.getToWash().numberOfTableware() > 0) {
      cleanedTablewareResponse = generateTablewareResponse(materialReport.getToWash());
    }

    BigDecimal totalPrice = materialReport.getTotalPrice().toBigDecimal();

    return new DateMaterialResponse(
        date.toLocalDate(), cleanedTablewareResponse, boughtTablewareResponse, totalPrice);
  }

  private TablewareResponse generateTablewareResponse(TablewareCabinet cabinet) {
    int numberOfKnives = cabinet.getTablewareByType(TablewareType.KNIFE).size();
    int numberOfForks = cabinet.getTablewareByType(TablewareType.FORK).size();
    int numberOfSpoons = cabinet.getTablewareByType(TablewareType.SPOON).size();
    int numberOfBowls = cabinet.getTablewareByType(TablewareType.BOWL).size();
    int numberOfPlates = cabinet.getTablewareByType(TablewareType.PLATE).size();
    return new TablewareResponse(
        numberOfKnives, numberOfForks, numberOfSpoons, numberOfBowls, numberOfPlates);
  }
}
