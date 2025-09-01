package rakan.storage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import rakan.task.Deadline;
import rakan.task.Event;
import rakan.task.Task;
import rakan.task.ToDo;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StorageTest {

    @TempDir
    File tempDir;

    @Test
    void saveAndLoadTodoTask() {
        File file = new File(tempDir, "tasks.txt");
        Storage storage = new Storage(file.getAbsolutePath());

        ArrayList<Task> tasksToSave = new ArrayList<>();
        ToDo todo = new ToDo("write tests");
        todo.markAsDone();
        tasksToSave.add(todo);

        storage.saveTasks(tasksToSave);
        ArrayList<Task> loadedTasks = storage.loadTasks();

        assertEquals(1, loadedTasks.size());
        Task loaded = loadedTasks.get(0);
        assertInstanceOf(ToDo.class, loaded);
        assertEquals("write tests", loaded.getDescription());
        assertTrue(loaded.isDone());
    }

    @Test
    void saveAndLoadDeadlineTask() {
        File file = new File(tempDir, "tasks.txt");
        Storage storage = new Storage(file.getAbsolutePath());

        ArrayList<Task> tasksToSave = new ArrayList<>();
        LocalDateTime by = LocalDateTime.of(2025, 9, 1, 23, 59);
        Deadline deadline = new Deadline("submit report", by);
        tasksToSave.add(deadline);

        storage.saveTasks(tasksToSave);
        ArrayList<Task> loadedTasks = storage.loadTasks();

        assertEquals(1, loadedTasks.size());
        Task loaded = loadedTasks.get(0);
        assertInstanceOf(Deadline.class, loaded);
        assertEquals("submit report", loaded.getDescription());
        assertEquals(by, ((Deadline) loaded).getBy());
        assertFalse(loaded.isDone());
    }

    @Test
    void saveAndLoadEventTask() {
        File file = new File(tempDir, "tasks.txt");
        Storage storage = new Storage(file.getAbsolutePath());

        ArrayList<Task> tasksToSave = new ArrayList<>();
        LocalDateTime from = LocalDateTime.of(2025, 9, 1, 14, 0);
        LocalDateTime to = LocalDateTime.of(2025, 9, 1, 16, 0);
        Event event = new Event("team meeting", from, to);
        event.markAsDone();
        tasksToSave.add(event);

        storage.saveTasks(tasksToSave);
        ArrayList<Task> loadedTasks = storage.loadTasks();

        assertEquals(1, loadedTasks.size());
        Task loaded = loadedTasks.get(0);
        assertInstanceOf(Event.class, loaded);
        assertEquals("team meeting", loaded.getDescription());
        assertEquals(from, ((Event) loaded).getFrom());
        assertEquals(to, ((Event) loaded).getTo());
        assertTrue(loaded.isDone());
    }
}

