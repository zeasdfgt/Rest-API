package ca.ulaval.glo4002.reservation.reports.ingredient.application;

import ca.ulaval.glo4002.reservation.date.domain.Date;
import ca.ulaval.glo4002.reservation.date.domain.DateInterval;
import ca.ulaval.glo4002.reservation.event.application.ConfigService;
import ca.ulaval.glo4002.reservation.ingredient.domain.repositories.MealIngredientRepository;
import ca.ulaval.glo4002.reservation.ingredient.infrastructure.persistence.MealIngredientFromMealsRepository;
import ca.ulaval.glo4002.reservation.meal.domain.repositories.MealRepository;
import ca.ulaval.glo4002.reservation.meal.infrastructure.MealFromReservationRepository;
import ca.ulaval.glo4002.reservation.reports.exceptions.InvalidReportDateException;
import ca.ulaval.glo4002.reservation.reports.ingredient.domain.TotalIngredientReport;
import ca.ulaval.glo4002.reservation.reports.ingredient.domain.UnitIngredientReport;
import ca.ulaval.glo4002.reservation.reports.ingredient.domain.generator.TotalIngredientReportGenerator;
import ca.ulaval.glo4002.reservation.reports.ingredient.domain.generator.UnitIngredientReportGenerator;
import ca.ulaval.glo4002.reservation.reservation.domain.exceptions.InvalidDinnerDateException;

public class IngredientReportService {
  private MealIngredientRepository mealIngredientRepository;
  private ConfigService configService;

  public IngredientReportService(
      MealIngredientRepository mealIngredientRepository, ConfigService configService) {
    this.mealIngredientRepository = mealIngredientRepository;
    this.configService = configService;
  }

  public IngredientReportService() {
    MealRepository mealRepository = new MealFromReservationRepository();
    this.mealIngredientRepository = new MealIngredientFromMealsRepository(mealRepository);
    this.configService = new ConfigService();
  }

  public UnitIngredientReport generateUnitIngredientReport(DateInterval dateInterval)
      throws InvalidReportDateException {
    try {
      validateDinnerDate(dateInterval);
    } catch (InvalidDinnerDateException exception) {
      throw new InvalidReportDateException();
    }

    UnitIngredientReportGenerator reportGenerator =
        new UnitIngredientReportGenerator(mealIngredientRepository);

    return reportGenerator.generateReport(dateInterval);
  }

  public TotalIngredientReport generateTotalIngredientReport(DateInterval dateInterval)
      throws InvalidReportDateException {
    try {
      validateDinnerDate(dateInterval);
    } catch (InvalidDinnerDateException exception) {
      throw new InvalidReportDateException();
    }

    TotalIngredientReportGenerator reportGenerator =
        new TotalIngredientReportGenerator(mealIngredientRepository);

    return reportGenerator.generateReport(dateInterval);
  }

  private void validateDinnerDate(DateInterval dateInterval) throws InvalidDinnerDateException {
    DateInterval dinnerDateInterval = configService.getDinnerDateInterval();
    Date startDate = dateInterval.getStartDate();
    Date endDate = dateInterval.getEndDate();
    if (!dinnerDateInterval.isWithinInterval(startDate)
        || !dinnerDateInterval.isWithinInterval(endDate)) {
      throw new InvalidDinnerDateException();
    }
  }
}
