package ca.ulaval.glo4002.reservation.reports.chef.domain.factories;

import ca.ulaval.glo4002.reservation.chef.Chef;
import ca.ulaval.glo4002.reservation.chef.infrastructure.persistence.InMemoryChefRepository;
import ca.ulaval.glo4002.reservation.date.domain.Date;
import ca.ulaval.glo4002.reservation.event.domain.EventDay;
import ca.ulaval.glo4002.reservation.money.Money;
import ca.ulaval.glo4002.reservation.reports.chef.domain.DailyChefReport;
import ca.ulaval.glo4002.reservation.reservation.domain.Restriction;
import ca.ulaval.glo4002.reservation.reservation.domain.RestrictionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

class DailyChefReportFactoryTest {
  private final InMemoryChefRepository chefRepository = new InMemoryChefRepository();
  private final Date FIRST_DATE = new Date(2150, 12, 10, 11, 22, 55);
  private final Date SECOND_DATE = new Date(2150, 12, 11, 11, 22, 55);
  private final Money FIRST_PRICE = new Money(200);
  private final Money SECOND_PRICE = new Money(500);
  private final Restriction FIRST_RESTRICTION =
      new Restriction(FIRST_PRICE, RestrictionType.ILLNESS);
  private final Restriction SECOND_RESTRICTION =
      new Restriction(SECOND_PRICE, RestrictionType.VEGAN);
  private EventDay firstEventDay;
  private EventDay secondEventDay;
  private List<EventDay> eventDays = new ArrayList<>();
  private List<Chef> chefs = new ArrayList<>();
  private List<Restriction> firstRestrictions = new ArrayList<>();
  private List<Restriction> secondRestrictions = new ArrayList<>();
  private DailyChefReportFactory dailyChefReportFactory;

  @BeforeEach
  public void DailyChefReportFactoryInitializer() {
    firstEventDay = mock(EventDay.class);
    secondEventDay = mock(EventDay.class);
    eventDays.add(firstEventDay);
    eventDays.add(secondEventDay);
    chefs = chefRepository.getAllChefs();
    willReturn(FIRST_DATE).given(firstEventDay).getDate();
    willReturn(SECOND_DATE).given(secondEventDay).getDate();
    firstRestrictions.add(FIRST_RESTRICTION);
    secondRestrictions.add(SECOND_RESTRICTION);
    willReturn(firstRestrictions).given(firstEventDay).getRestrictionsForChefs();
    willReturn(secondRestrictions).given(secondEventDay).getRestrictionsForChefs();
    dailyChefReportFactory = new DailyChefReportFactory();
  }

  @Test
  public void whenGeneratingChefReports_theReturnsChefReportForEachEventDay() {
    List<DailyChefReport> returnedReports =
        dailyChefReportFactory.generateChefReports(eventDays, chefs);

    assertEquals(FIRST_DATE, returnedReports.get(0).getDate());
    assertEquals(SECOND_DATE, returnedReports.get(1).getDate());
  }
}
