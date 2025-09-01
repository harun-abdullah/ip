package rakan.task;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    @Test
    void event_storesDescriptionAndDates() {
        LocalDateTime from = LocalDateTime.of(2025, 9, 1, 14, 0);
        LocalDateTime to = LocalDateTime.of(2025, 9, 1, 16, 0);
        Event event = new Event("team meeting", from, to);

        assertEquals("team meeting", event.getDescription());
        assertEquals(from, event.getFrom());
        assertEquals(to, event.getTo());
        assertFalse(event.isDone(), "Event should not be done by default");
    }

    @Test
    void toString_formatsCorrectlyWhenNotDone() {
        LocalDateTime from = LocalDateTime.of(2025, 9, 1, 14, 0);
        LocalDateTime to = LocalDateTime.of(2025, 9, 1, 16, 0);
        Event event = new Event("team meeting", from, to);

        String expected = "[E][ ] team meeting (from: Sep 01 2025, 2:00pm to: Sep 01 2025, 4:00pm)";
        assertEquals(expected, event.toString());
    }

    @Test
    void toString_formatsCorrectlyWhenDone() {
        LocalDateTime from = LocalDateTime.of(2025, 9, 1, 14, 0);
        LocalDateTime to = LocalDateTime.of(2025, 9, 1, 16, 0);
        Event event = new Event("team meeting", from, to);
        event.markAsDone();

        String expected = "[E][X] team meeting (from: Sep 01 2025, 2:00pm to: Sep 01 2025, 4:00pm)";
        assertEquals(expected, event.toString());
    }
}
