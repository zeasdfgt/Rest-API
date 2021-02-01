package ca.ulaval.glo4002.reservation.date.domain;

import ca.ulaval.glo4002.reservation.date.domain.exceptions.InvalidDateIntervalException;

import java.util.Objects;

public class DateInterval {
  private final Date startDate;
  private final Date endDate;

  public DateInterval(Date startDate, Date endDate) {
    if (startDate.isAfter(endDate)) {
      throw new InvalidDateIntervalException();
    }

    this.startDate = startDate.startOfDay();
    this.endDate = endDate.endOfDay();
  }

  public boolean isWithinInterval(Date date) {
    return !(date.isBefore(startDate) || date.isAfter(endDate));
  }

  public boolean startDateSameAsEndDate() {
    return startDate.isSameDay(endDate);
  }

  public Date getStartDate() {
    return startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    DateInterval that = (DateInterval) object;
    return Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(startDate, endDate);
  }
}
