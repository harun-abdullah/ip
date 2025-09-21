package rakan.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rakan.RakanException;
import rakan.task.Task;
import rakan.tasklist.TaskList;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UiTest {

    private Ui ui;

    // Minimal Task stub for testing
    static class StubTask extends Task {
        private boolean done = false;

        public StubTask(String description) {
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
        ui = new Ui();
    }

    @Test
    void greet_returnsGreeting() {
        String greeting = ui.greet();
        assertTrue(greeting.contains("Rakan"));
        assertTrue(greeting.contains("🔥"));
    }

    @Test
    void showExit_appendsGoodbyeMessage() {
        ui.showExit();
        String response = ui.getResponse();
        assertTrue(response.contains("bye"));
    }

    @Test
    void showAddedTask_appendsConfirmationMessage() {
        ArrayList<Task> tasks = new ArrayList<>();
        TaskList taskList = new TaskList(tasks);

        StubTask task = new StubTask("read book");
        tasks.add(task);

        ui.showAddedTask(task, taskList);
        String response = ui.getResponse();

        assertTrue(response.contains("Got it. I've added this task:"));
        assertTrue(response.contains("read book"));
        assertTrue(response.contains("1 tasks"));
    }

    @Test
    void showList_withTasks_displaysAllTasks() throws RakanException {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new StubTask("task one"));
        tasks.add(new StubTask("task two"));

        ui.showList(tasks);
        String response = ui.getResponse();

        assertTrue(response.contains("Tasklist:"));
        assertTrue(response.contains("1. [ ] task one"));
        assertTrue(response.contains("2. [ ] task two"));
    }

    @Test
    void showList_withNoTasks_throwsException() {
        ArrayList<Task> empty = new ArrayList<>();
        assertThrows(RakanException.class, () -> ui.showList(empty));
    }

    @Test
    void showFindResults_withMatches_displaysTasks() throws RakanException {
        ArrayList<Task> matches = new ArrayList<>();
        matches.add(new StubTask("buy milk"));

        ui.showFindResults(matches);
        String response = ui.getResponse();

        assertTrue(response.contains("Here's the matching tasks"));
        assertTrue(response.contains("1. [ ] buy milk"));
    }

    @Test
    void showFindResults_withNoMatches_throwsException() {
        ArrayList<Task> empty = new ArrayList<>();
        assertThrows(RakanException.class, () -> ui.showFindResults(empty));
    }

    @Test
    void showMessage_appendsSingleMessage() {
        ui.showMessage("Hello World");
        assertEquals("Hello World", ui.getResponse());
    }

    @Test
    void showMessages_appendsMultipleMessages() {
        ui.showMessages("Line one", "Line two", "Line three");
        String response = ui.getResponse();

        assertTrue(response.contains("Line one"));
        assertTrue(response.contains("Line two"));
        assertTrue(response.contains("Line three"));
    }

    @Test
    void clearMessages_resetsResponseBuffer() {
        ui.showMessage("Keep this");
        ui.clearMessages();
        assertEquals("", ui.getResponse());
    }
}

