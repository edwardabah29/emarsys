package com.ermasys.ermasys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class ErmasysApplication {

	public static void main(String[] args) {
		SpringApplication.run(ErmasysApplication.class, args);

		LocalDateTime submitDate = LocalDateTime.of(2024, 7, 16, 14, 12);
		int turnaroundTime = 16;

		LocalDateTime dueDate = DueDateCalculator.calculateDueDate(submitDate, turnaroundTime);
		System.out.println("Submit date: " + submitDate + ", Turn around time: " + turnaroundTime + ", Due date: " + dueDate );
	}

}
