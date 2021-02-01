package ca.ulaval.glo4002.reservation.reports.total.rest.resources;

import ca.ulaval.glo4002.reservation.event.application.ConfigService;
import ca.ulaval.glo4002.reservation.event.domain.Event;
import ca.ulaval.glo4002.reservation.reports.chef.application.ChefReportService;
import ca.ulaval.glo4002.reservation.reports.exceptions.InvalidReportDateException;
import ca.ulaval.glo4002.reservation.reports.ingredient.application.IngredientReportService;
import ca.ulaval.glo4002.reservation.reports.material.application.MaterialReportService;
import ca.ulaval.glo4002.reservation.reports.total.application.TotalReportService;
import ca.ulaval.glo4002.reservation.reports.total.domain.TotalReportFactory;
import ca.ulaval.glo4002.reservation.reports.total.rest.factories.TotalReportResponseAssembler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class TotalReportResourceTest {
  private TotalReportResource totalReportResource;
  private TotalReportResponseAssembler totalReportResponseAssembler;
  private TotalReportFactory totalReportFactory;
  private Event event;
  private ConfigService configService;
  private MaterialReportService materialReportService;
  private IngredientReportService ingredientReportService;
  private ChefReportService chefReportService;
  private TotalReportService totalReportService;

  @BeforeEach
  public void totalReportResourceInitialisation() {
    event = mock(Event.class);
    configService = mock(ConfigService.class);
    materialReportService = mock(MaterialReportService.class);
    totalReportResponseAssembler = mock(TotalReportResponseAssembler.class);
    totalReportFactory = mock(TotalReportFactory.class);
    ingredientReportService = mock(IngredientReportService.class);
    chefReportService = mock(ChefReportService.class);
    totalReportService = mock(TotalReportService.class);
    totalReportResource =
        new TotalReportResource(
            event,
            configService,
            totalReportFactory,
            totalReportResponseAssembler,
            materialReportService,
            ingredientReportService,
            chefReportService,
            totalReportService);
  }

  @Test
  public void whenGeneratingTotalReport_thenReportIsGeneratedWithTotalReportAssembler()
      throws InvalidReportDateException {
    totalReportResource.getTotalReport();

    verify(totalReportResponseAssembler).getTotalReportResponse();
  }
}
