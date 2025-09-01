package rakan.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ToDoTest {

    @Test
    void todo_inheritsDescriptionAndDefaultState() {
        ToDo todo = new ToDo("write unit tests");

        assertEquals("write unit tests", todo.getDescription());
        assertFalse(todo.isDone(), "ToDo should not be done by default");
    }

    @Test
    void toString_showsCorrectFormatWhenNotDone() {
        ToDo todo = new ToDo("write unit tests");
        assertEquals("[T][ ] write unit tests", todo.toString());
    }

    @Test
    void toString_showsCorrectFormatWhenDone() {
        ToDo todo = new ToDo("write unit tests");
        todo.markAsDone();

        assertEquals("[T][X] write unit tests", todo.toString());
    }
}

