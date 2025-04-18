package com.neoflex.calculator.service;

import com.neoflex.calculator.exception.CalculatorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class VacationSalaryCalculatorServiceTest {

	public static final int VACATION_DAYS = 14;
	private final double AVERAGE_SALARY = 60000;

	private VacationSalaryCalculatorService vacationSalaryCalculatorService;


	@BeforeEach
	void setup() {
		vacationSalaryCalculatorService = new VacationSalaryCalculatorService();
	}

	@Test
	void testCalculateVacationSalary() {

		double expectedVacationPay = (AVERAGE_SALARY / 29.3) * VACATION_DAYS * (1 - 0.13);

		double actualVacationPay = vacationSalaryCalculatorService.calculateVacationSalary(AVERAGE_SALARY, VACATION_DAYS);

		assertEquals(expectedVacationPay, actualVacationPay, 0.01);
	}

	@Test
	void testCalculateVacationSalaryWithPeriod() {
		LocalDate startDate = LocalDate.of(2024, 6, 1);
		LocalDate endDate = LocalDate.of(2024, 6, 14);

		// 12 июня - государственный праздник, не должен быть учтен
		double expectedVacationPay = vacationSalaryCalculatorService.calculateVacationSalary(AVERAGE_SALARY, 13);

		double actualVacationPay = vacationSalaryCalculatorService.calculateVacationSalaryWithPeriod(AVERAGE_SALARY, startDate, endDate);

		assertEquals(expectedVacationPay, actualVacationPay, 0.01);
	}

	@Test
	void testCalculateVacationSalaryWithPeriod_CrossYear() {
		double averageSalary = 60000;
		LocalDate startDate = LocalDate.of(2024, 12, 28); // Начало в декабре 2024
		LocalDate endDate = LocalDate.of(2025, 1, 10);    // Конец в январе 2025

		// с 1 по 8 января - государственные праздники, их не учитываем
		double expectedVacationPay = vacationSalaryCalculatorService.calculateVacationSalary(averageSalary, 6);

		double actualVacationPay = vacationSalaryCalculatorService.calculateVacationSalaryWithPeriod(averageSalary, startDate, endDate);

		assertEquals(expectedVacationPay, actualVacationPay, 0.01);
	}


	@Test
	void testCalculateVacationSalary_InvalidSalary_ShouldThrowException() {
		double invalidSalary = -50000;

		assertThrows(CalculatorException.class,
				() -> vacationSalaryCalculatorService.calculateVacationSalary(invalidSalary, VACATION_DAYS));
	}

	@Test
	void testCalculateVacationSalary_InvalidVacationDays_ShouldThrowException() {
		int invalidVacationDays = -5;

		assertThrows(CalculatorException.class,
				() -> vacationSalaryCalculatorService.calculateVacationSalary(AVERAGE_SALARY, invalidVacationDays));
	}

	@Test
	void testCalculateVacationSalaryWithPeriod_InvalidDates_ShouldThrowException() {
		LocalDate startDate = LocalDate.of(2024, 6, 10);
		LocalDate endDate = LocalDate.of(2024, 6, 1);

		assertThrows(CalculatorException.class,
				() -> vacationSalaryCalculatorService.calculateVacationSalaryWithPeriod(AVERAGE_SALARY, startDate, endDate));
	}
}
