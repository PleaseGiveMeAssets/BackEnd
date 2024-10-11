package com.example.spring.util;

import lombok.extern.slf4j.Slf4j;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
public class KRXBusinessDayCalculator {
    public LocalDate getNthPrevBusinessDay(LocalDate startDate, int n, Set<LocalDate> marketHolidays) {
        LocalDate currentDate = startDate;
        int businessDays = 0;

        while (businessDays < n) {
            if (!(currentDate.getDayOfWeek() == DayOfWeek.SATURDAY ||
                    currentDate.getDayOfWeek() == DayOfWeek.SUNDAY ||
                    marketHolidays.contains(currentDate))) {
                businessDays++;
            }
            if (businessDays == n) {
                return currentDate;
            }
            currentDate = currentDate.minusDays(1);
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

    public List<LocalDate> getListBusinessDays(LocalDate startDate, int n, Set<LocalDate> holidays) {
        List<LocalDate> businessDayList = new ArrayList<>();
        int businessDays = 0;

        while (businessDays < n) {
            if (!(startDate.getDayOfWeek() == DayOfWeek.SATURDAY ||
                    startDate.getDayOfWeek() == DayOfWeek.SUNDAY ||
                    holidays.contains(startDate))) {
                businessDayList.add(startDate);
                businessDays++;
            }
            startDate = startDate.minusDays(1);
        }
        return businessDayList;
    }
}
