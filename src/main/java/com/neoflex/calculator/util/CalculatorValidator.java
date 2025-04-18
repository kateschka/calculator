package com.neoflex.calculator.util;

import java.time.LocalDate;

public final class CalculatorValidator {
    private static final LocalDate MIN_VACATION_START_DATE = LocalDate.of(2004, 1, 1);

    public static boolean validateAverageSalary(double averageSalary){
        return averageSalary >= 0;
    }

    public static boolean validateVacationDays(int vacationDays){
        return vacationDays >= 0;
    }

    public static boolean validateVacationDates(LocalDate startDate, LocalDate endDate){
        return (startDate != null && startDate.isAfter(MIN_VACATION_START_DATE)) &&
                (endDate != null && endDate.isAfter(startDate));
    }
}
