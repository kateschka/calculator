package com.neoflex.calculator.controller;

import com.neoflex.calculator.dto.VacationSalaryResponse;
import com.neoflex.calculator.service.VacationSalaryCalculatorService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/")
public class CalculatorController {
    private final VacationSalaryCalculatorService vacationSalaryCalculatorService;

    public CalculatorController(VacationSalaryCalculatorService vacationSalaryCalculatorService) {
        this.vacationSalaryCalculatorService = vacationSalaryCalculatorService;

    }

    @GetMapping("/calculate")
    public VacationSalaryResponse calculate(@RequestParam(name = "averageSalary") double averageSalary,
                                            @RequestParam(name = "vacationDays", required = false) Integer vacationDays,
                                            @RequestParam(name = "startDate", required = false)
                                                @DateTimeFormat(iso = ISO.DATE) LocalDate startDate,
                                            @RequestParam(name = "endDate", required = false)
                                                @DateTimeFormat(iso = ISO.DATE) LocalDate endDate
                                            ) {
        double vacationSalary;

        if (vacationDays != null) {
            vacationSalary = vacationSalaryCalculatorService.calculateVacationSalary(averageSalary, vacationDays);
        } else if (startDate != null && endDate != null) {
            vacationSalary = vacationSalaryCalculatorService.calculateVacationSalaryWithPeriod(averageSalary, startDate, endDate);
        } else {
            throw new IllegalArgumentException("Either vacationDays or startDate and endDate must be provided");
        }

        return new VacationSalaryResponse(vacationSalary);
    }

}
