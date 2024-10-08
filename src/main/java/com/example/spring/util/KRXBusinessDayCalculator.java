package com.example.spring.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

public class KRXBusinessDayCalculator {
    public LocalDate getNthPrevBusinessDay(LocalDate startDate, int n, Set<LocalDate> marketHolidays) {
        LocalDate currentDate = startDate;
        int businessDays = 0;

        while (businessDays < n) {
            currentDate = currentDate.minusDays(1);

            if (!(currentDate.getDayOfWeek() == DayOfWeek.SATURDAY ||
                    currentDate.getDayOfWeek() == DayOfWeek.SUNDAY ||
                    marketHolidays.contains(currentDate))) {
                businessDays++;
            }
        }

        return currentDate;
    }

    public LocalDate getNearestPrevBusinessDay(LocalDate startDate, Set<LocalDate> marketHolidays) {
        LocalDate currentDate = startDate;

        while (currentDate.getDayOfWeek() == DayOfWeek.SATURDAY ||
                currentDate.getDayOfWeek() == DayOfWeek.SUNDAY ||
                marketHolidays.contains(currentDate)) {
            currentDate = currentDate.minusDays(1);
        }

        return currentDate;
    }

    public int calculateBusinessDaysBetween(LocalDate startDate, LocalDate endDate, Set<LocalDate> holidays) {
        int businessDays = 0;
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            if (!(currentDate.getDayOfWeek() == DayOfWeek.SATURDAY ||
                    currentDate.getDayOfWeek() == DayOfWeek.SUNDAY ||
                    holidays.contains(currentDate))) {
                businessDays++;
            }
            currentDate = currentDate.plusDays(1);
        }

        return businessDays;
    }
}
