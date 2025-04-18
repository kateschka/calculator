package com.neoflex.calculator.service;

import java.time.LocalDate;

public interface CalculatorService {

    double calculateVacationSalaryWithPeriod(double averageSalary, LocalDate startDate, LocalDate endDate);
    double calculateVacationSalary(double averageSalary, int days);

}
