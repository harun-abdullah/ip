package rakan.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {

    protected LocalDateTime from;
    protected LocalDateTime to;

    /**
     * Constructs Event task.
     *
     * @param description Task description.
     * @param from Starting date and time of event.
     * @param to Ending date and time of event.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    /**
     * Return task type, description, /to and /from dates.
     *
     * @return task type, description, /to and /from dates.
     */
    @Override
    public String toString(){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");
        return "[E]" + super.toString() + " (from: " + from.format(fmt) + " to: " + to.format(fmt) + ")";
    }
}
