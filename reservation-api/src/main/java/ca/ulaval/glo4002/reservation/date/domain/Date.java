package ca.ulaval.glo4002.reservation.date.domain;

import ca.ulaval.glo4002.reservation.rest.exceptions.InvalidFormatException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Date implements Comparable<Date> {
  private ZonedDateTime date;

  public Date(int year, int month, int day, int hour, int minute, int second) {
    this.date = ZonedDateTime.of(year, month, day, hour, minute, second, 0, ZoneId.of("GMT"));
  }

  public Date(ZonedDateTime date) {
    this.date = date;
  }

  public static Date startOfDay(int year, int month, int day) {
    return new Date(year, month, day, 0, 0, 0);
  }

  public static Date endOfDay(int year, int month, int day) {
    return new Date(year, month, day, 23, 59, 59);
  }

  public static Date parseZonedDateTime(String stringDate) throws InvalidFormatException {
    ZonedDateTime zonedDateTime;

    try {
      zonedDateTime = ZonedDateTime.parse(stringDate);
    } catch (Exception exception) {
      throw new InvalidFormatException();
    }

    return new Date(zonedDateTime);
  }

  public static Date parseLocalDate(String stringDate) throws InvalidFormatException {
    ZonedDateTime zonedDateTime;

    String pattern = "yyyy-MM-dd";
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);

    try {
      LocalDate localDate = LocalDate.parse(stringDate, dateTimeFormatter);
      zonedDateTime = ZonedDateTime.of(localDate, LocalTime.MIDNIGHT, ZoneId.of("GMT"));
    } catch (Exception exception) {
      throw new InvalidFormatException();
    }

    return new Date(zonedDateTime);
  }

  public Date endOfDay() {
    ZonedDateTime newDate =
        ZonedDateTime.of(
            date.getYear(),
            date.getMonthValue(),
            date.getDayOfMonth(),
            23,
            59,
            59,
            999999999,
            ZoneId.of("GMT"));
    return new Date(newDate);
  }

  public Date startOfDay() {
    ZonedDateTime newDate =
        ZonedDateTime.of(
            date.getYear(),
            date.getMonthValue(),
            date.getDayOfMonth(),
            0,
            0,
            0,
            0,
            ZoneId.of("GMT"));
    return new Date(newDate);
  }

  public boolean isBefore(Date otherDate) {
    return date.isBefore(otherDate.toZonedDateTime());
  }

  public boolean isSameDay(Date otherDate) {
    return date.getDayOfYear() == otherDate.date.getDayOfYear()
        && date.getYear() == otherDate.date.getYear();
  }

  public boolean isAfter(Date otherDate) {
    return date.isAfter(otherDate.toZonedDateTime());
  }

  public ZonedDateTime toZonedDateTime() {
    return date;
  }

  public LocalDate toLocalDate() {
    return date.toLocalDate();
  }

  public boolean isDuringDayInterval(Date fromDate, Date toDate) {
    return (isDuringInterval(fromDate, toDate) || isSameDay(fromDate) || isSameDay(toDate));
  }

  private boolean isDuringInterval(Date fromDate, Date toDate) {
    return (this.isAfter(fromDate) && this.isBefore(toDate));
  }

  public String format(String pattern) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);

    return dateTimeFormatter.format(date);
  }

  public Date minusDays(int number) {
    return new Date(this.date.minusDays(number));
  }

  public Date plusDays(int number) {
    return new Date(this.date.plusDays(number));
  }

  @Override
  public int compareTo(Date otherDate) {
    return this.date.compareTo(otherDate.toZonedDateTime());
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    Date date1 = (Date) object;
    return Objects.equals(date, date1.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(date);
  }
}
