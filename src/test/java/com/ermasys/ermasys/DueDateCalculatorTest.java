package com.ermasys.ermasys;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class DueDateCalculatorTest {

    @Test
    public void testCalculateDueDate() {
        LocalDateTime submitDate = LocalDateTime.of(2024, 7, 16, 14, 12); // Tuesday 2:12 PM
        int turnaroundTime = 16; // 16 working hours

        LocalDateTime expectedDueDate = LocalDateTime.of(2024, 7, 18, 14, 12); // Thursday 2:12 PM

        LocalDateTime actualDueDate = DueDateCalculator.calculateDueDate(submitDate, turnaroundTime);

        assertEquals(expectedDueDate, actualDueDate);
    }

    @Test
    public void testCalculateDueDateWithinSameDay() {
        LocalDateTime submitDate = LocalDateTime.of(2024, 7, 16, 10, 0); // Tuesday 10:00 AM
        int turnaroundTime = 5; // 5 working hours

        LocalDateTime expectedDueDate = LocalDateTime.of(2024, 7, 16, 15, 0); // Tuesday 3:00 PM

        LocalDateTime actualDueDate = DueDateCalculator.calculateDueDate(submitDate, turnaroundTime);

        assertEquals(expectedDueDate, actualDueDate);
    }

    @Test
    public void testCalculateDueDateOverWeekend() {
        LocalDateTime submitDate = LocalDateTime.of(2024, 7, 19, 14, 0); // Friday 2:00 PM
        int turnaroundTime = 4; // 4 working hours

        LocalDateTime expectedDueDate = LocalDateTime.of(2024, 7, 22, 10, 0); // Monday 10:00 AM

        LocalDateTime actualDueDate = DueDateCalculator.calculateDueDate(submitDate, turnaroundTime);

        assertEquals(expectedDueDate, actualDueDate);
    }

    @Test
    public void testSubmittedOutsideWorkingHours(){
        LocalDateTime submitDate = LocalDateTime.of(2024, 7, 16, 18, 0); // Tuesday 6:00 PM
        int turnaroundTime = 5; // 5 working hours

        Exception exception = assertThrows(IllegalArgumentException.class, () -> DueDateCalculator.calculateDueDate(submitDate, turnaroundTime));

        assertEquals("Submit date must be within working hours", exception.getMessage());
    }

    @Test
    public void testSubmitDateNull(){
        LocalDateTime submitDate = null;
        int turnaroundTime = 5;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> DueDateCalculator.calculateDueDate(submitDate, turnaroundTime));

        assertEquals("Submit date must not be null", exception.getMessage());
    }

    @Test
    public void testZeroTurnaroundTime(){
        LocalDateTime submitDate = LocalDateTime.of(2024, 7, 16, 10, 0);
        int turnaroundTime = 0;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> DueDateCalculator.calculateDueDate(submitDate, turnaroundTime));

        assertEquals("Turnaround time must be a positive integer", exception.getMessage());
    }

    @Test
    public void testNegativeTurnaroundTime() {
        LocalDateTime submitDate = LocalDateTime.of(2024, 7, 16, 10, 0); // Tuesday 10:00 AM
        int turnaroundTime = -5; // Negative turnaround time

        Exception exception = assertThrows(IllegalArgumentException.class, () -> DueDateCalculator.calculateDueDate(submitDate, turnaroundTime));

        assertEquals("Turnaround time must be a positive integer", exception.getMessage());
    }

}
