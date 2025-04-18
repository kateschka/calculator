package com.neoflex.calculator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VacationSalaryResponse {

    @JsonProperty("vacation_salary")
    double vacationSalary;
    public VacationSalaryResponse(double vacationSalary) {
        this.vacationSalary = vacationSalary;
    }
}
