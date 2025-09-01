package rakan.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    protected LocalDateTime by;

    /**
     * Constructs Deadline task
     *
     * @param description Task description
     * @param by Date to complete task by
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    public LocalDateTime getBy() {
        return by;
    }

    /**
     * Return task type, description, and /by date
     *
     * @return Task type, description, and /by date
     */
    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");
        return "[D]" + super.toString() + " (by: " + by.format(fmt) + ")";
    }
}
