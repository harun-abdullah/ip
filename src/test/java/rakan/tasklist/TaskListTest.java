package rakan.tasklist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rakan.RakanException;
import rakan.parser.ParsedMark;
import rakan.task.Task;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TaskListTest {

    private TaskList taskList;

    // Stub Task for testing
    static class StubTask extends Task {
        private boolean done = false;
        StubTask(String description) {
            super(description);
        }
        @Override
        public boolean isDone() {
            return done;
        }
        @Override
        public void markAsDone() {
            done = true;
        }
        @Override
        public void markAsNotDone() {
            done = false;
        }
        @Override
        public String toString() {
            return (done ? "[X] " : "[ ] ") + getDescription();
        }
    }

    @BeforeEach
    void setUp() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new StubTask("read book"));
        tasks.add(new StubTask("write essay"));
        taskList = new TaskList(tasks);
    }

    @Test
    void addTask_taskAddedSuccessfully() {
        int initialSize = taskList.getTasks().size();
        taskList.addTask(new StubTask("new task"));
        assertEquals(initialSize + 1, taskList.getTasks().size());
        assertEquals("new task", taskList.getTasks().get(initialSize).getDescription());
    }

    @Test
    void handleDelete_taskDeletedSuccessfully() {
        taskList.handleDelete(0);
        assertEquals(1, taskList.getTasks().size());
        assertEquals("write essay", taskList.getTasks().get(0).getDescription());
    }

    @Test
    void handleMark_markTaskAsDone_success() throws RakanException {
        ParsedMark parsed = new ParsedMark(0, true);
        taskList.handleMark(parsed);
        assertTrue(taskList.getTasks().get(0).isDone());
    }

    @Test
    void handleMark_markAlreadyDone_throwsException() throws RakanException {
        ParsedMark parsed = new ParsedMark(0, true);
        taskList.handleMark(parsed); // first time works
        assertThrows(RakanException.class, () -> taskList.handleMark(parsed));
    }

    @Test
    void handleMark_unmarkTask_success() throws RakanException {
        // mark first, then unmark
        ParsedMark mark = new ParsedMark(0, true);
        taskList.handleMark(mark);
        ParsedMark unmark = new ParsedMark(0, false);
        taskList.handleMark(unmark);
        assertFalse(taskList.getTasks().get(0).isDone());
    }

    @Test
    void handleMark_unmarkAlreadyNotDone_throwsException() {
        ParsedMark unmark = new ParsedMark(0, false);
        assertThrows(RakanException.class, () -> taskList.handleMark(unmark));
    }

    @Test
    void find_keywordMatchesOneTask_returnsCorrectTask() {
        ArrayList<Task> results = taskList.find("book");
        assertEquals(1, results.size());
        assertEquals("read book", results.get(0).getDescription());
    }

    @Test
    void find_keywordMatchesNoTask_returnsEmptyList() {
        ArrayList<Task> results = taskList.find("nothing");
        assertTrue(results.isEmpty());
    }
}

