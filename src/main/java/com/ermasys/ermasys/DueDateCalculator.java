package com.ermasys.ermasys;

import java.time.LocalDateTime;
import java.time.DayOfWeek;

public class DueDateCalculator {

    public static LocalDateTime calculateDueDate(LocalDateTime submitDate, int turnaroundHours) {

        if (submitDate == null){
            throw new IllegalArgumentException("Submit date must not be null");
        }

        if(turnaroundHours < 1){
            throw new IllegalArgumentException("Turnaround time must be a positive integer");
        }

        if (submitDate.getHour() < 9 || submitDate.getHour() >= 17 || submitDate.getDayOfWeek() == DayOfWeek.SATURDAY || submitDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException("Submit date must be within working hours");
        }

        LocalDateTime dueDate = submitDate;
        int hoursLeft = turnaroundHours;

        while (hoursLeft > 0) {
            if (dueDate.getHour() >= 17) {
                dueDate = dueDate.withHour(9).withMinute(0).withSecond(0).plusDays(1);
            } else if (dueDate.getHour() < 9) {
                dueDate = dueDate.withHour(9).withMinute(0).withSecond(0);
            } else if (dueDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
                dueDate = dueDate.plusDays(2).withHour(9).withMinute(0).withSecond(0);
            } else if (dueDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                dueDate = dueDate.plusDays(1).withHour(9).withMinute(0).withSecond(0);
            } else {
                int hoursToEndOfDay = 17 - dueDate.getHour();
                if (hoursLeft <= hoursToEndOfDay) {
                    dueDate = dueDate.plusHours(hoursLeft);
                    hoursLeft = 0;
                } else {
                    dueDate = dueDate.plusHours(hoursToEndOfDay);
                    hoursLeft -= hoursToEndOfDay;
                    dueDate = dueDate.plusDays(1).withHour(9);
                }
            }

            if (dueDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
                dueDate = dueDate.plusDays(2);
            } else if (dueDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                dueDate = dueDate.plusDays(1);
            }
        }

        return dueDate;
    }
}