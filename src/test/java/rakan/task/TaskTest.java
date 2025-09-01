package rakan.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    private Task task;

    @BeforeEach
    void setup() {
        task = new Task("read book");
    }

    @Test
    void newTask_isNotDoneByDefault() {
        assertFalse(task.isDone(), "Task should not be done by default");
        assertEquals(" ", task.getStatusIcon(), "Default status icon should be a space");
    }

    @Test
    void markAsDone_setsTaskToDone() {
        task.markAsDone();
        assertTrue(task.isDone(), "Task should be marked as done");
        assertEquals("X", task.getStatusIcon(), "Status icon should be 'X' when done");
    }

    @Test
    void markAsNotDone_setsTaskToNotDone() {
        task.markAsDone(); // first mark done
        task.markAsNotDone();
        assertFalse(task.isDone(), "Task should be marked as not done");
        assertEquals(" ", task.getStatusIcon(), "Status icon should be space when not done");
    }

    @Test
    void toString_returnsCorrectFormat() {
        assertEquals("[ ] read book", task.toString());

        task.markAsDone();
        assertEquals("[X] read book", task.toString());
    }

    @Test
    void getDescription_returnsOriginalDescription() {
        assertEquals("read book", task.getDescription());
    }
}
