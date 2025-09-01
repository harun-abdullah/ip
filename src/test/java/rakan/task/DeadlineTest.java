package rakan.task;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DeadlineTest {

    @Test
    void deadline_storesDescriptionAndDate() {
        LocalDateTime date = LocalDateTime.of(2025, 9, 1, 17, 30);
        Deadline deadline = new Deadline("submit report", date);

        assertEquals("submit report", deadline.getDescription());
        assertEquals(date, deadline.getBy());
        assertFalse(deadline.isDone(), "Deadline should not be done by default");
    }

    @Test
    void toString_formatsCorrectlyWhenNotDone() {
        LocalDateTime date = LocalDateTime.of(2025, 9, 1, 17, 30);
        Deadline deadline = new Deadline("submit report", date);

        String expected = "[D][ ] submit report (by: Sep 01 2025, 5:30pm)";
        assertEquals(expected, deadline.toString());
    }

    @Test
    void toString_formatsCorrectlyWhenDone() {
        LocalDateTime date = LocalDateTime.of(2025, 9, 1, 17, 30);
        Deadline deadline = new Deadline("submit report", date);
        deadline.markAsDone();

        String expected = "[D][X] submit report (by: Sep 01 2025, 5:30pm)";
        assertEquals(expected, deadline.toString());
    }
}

