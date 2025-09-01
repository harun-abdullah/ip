package rakan.parser;

import java.time.LocalDateTime;

public class ParsedEvent {
    private final String description;
    private final LocalDateTime from;
    private final LocalDateTime to;

    /**
     * Constructs ParsedEvent object.
     *
     * @param description Event description.
     * @param from Starting date and time of event.
     * @param to Ending date and time of event.
     */
    public ParsedEvent(String description, LocalDateTime from, LocalDateTime to) {
        this.description = description;
        this.from = from;
        this.to = to;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }
}

