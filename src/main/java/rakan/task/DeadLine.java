package rakan.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DeadLine extends Task {

    protected LocalDateTime by;

    public DeadLine(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    public LocalDateTime getBy() {
        return by;
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");
        return "[D]" + super.toString() + " (by: " + by.format(fmt) + ")";
    }
}
