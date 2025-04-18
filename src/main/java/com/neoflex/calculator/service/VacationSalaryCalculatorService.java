package com.neoflex.calculator.service;

import com.neoflex.calculator.exception.CalculatorException;
import com.neoflex.calculator.util.CalculatorValidator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.Collectors;

@Service
public class VacationSalaryCalculatorService implements CalculatorService{
    private static final double AVERAGE_NUMBER_OF_DAYS_IN_MONTH = 29.3;
    private static final double INCOME_TAX = 0.13;

    @Override
    public double calculateVacationSalaryWithPeriod(double averageSalary, LocalDate startDate, LocalDate endDate) {
        if (!CalculatorValidator.validateVacationDates(startDate, endDate)){
            throw new CalculatorException("Incorrect start or end date of vacation");
        }

        return calculateVacationSalary(averageSalary, calculateVacationDays(startDate, endDate));
    }

    @Override
    public double calculateVacationSalary(double averageSalary, int vacationDays){
        if (!CalculatorValidator.validateAverageSalary(averageSalary)){
            throw new CalculatorException("Salary should be a positive number");
        }
        if (!CalculatorValidator.validateVacationDays(vacationDays)){
            throw new CalculatorException("Vacation period cannot be negative");
        }
        BigDecimal vacationPay = BigDecimal.valueOf(
                (averageSalary / AVERAGE_NUMBER_OF_DAYS_IN_MONTH) * vacationDays * (1 - INCOME_TAX));
        return vacationPay.setScale(2, RoundingMode.FLOOR).doubleValue();
    }

    public int calculateVacationDays(LocalDate startDate, LocalDate endDate){

        Set<LocalDate> holidays = (startDate.getYear() != endDate.getYear())
                ? Stream.concat(getHolidays(startDate.getYear()).stream(),
                        getHolidays(endDate.getYear()).stream())
                .collect(Collectors.toSet())
                : getHolidays(startDate.getYear());

        Set<LocalDate> vacationDays = startDate.datesUntil(endDate.plusDays(1))
                .collect(Collectors.toSet());

        vacationDays.removeAll(holidays);

        return vacationDays.size();
    }

    private static Set<LocalDate> getHolidays(int year){
        return Stream.of(
                LocalDate.of(year, 1, 1),
                LocalDate.of(year, 1, 2),
                LocalDate.of(year, 1, 3),
                LocalDate.of(year, 1, 4),
                LocalDate.of(year, 1, 5),
                LocalDate.of(year, 1, 6),
                LocalDate.of(year, 1, 7),
                LocalDate.of(year, 1, 8),
                LocalDate.of(year, 2, 23),
                LocalDate.of(year, 3, 8),
                LocalDate.of(year, 5, 1),
                LocalDate.of(year, 5, 9),
                LocalDate.of(year, 6, 12),
                LocalDate.of(year, 11, 4)
        ).collect(Collectors.toSet());
    }
}
