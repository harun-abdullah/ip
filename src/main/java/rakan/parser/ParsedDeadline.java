package rakan.parser;

import java.time.LocalDateTime;

public class ParsedDeadline {
    private final String description;
    private final LocalDateTime by;

    /**
     * Constructs ParsedDeadline object.
     *
     * @param description Deadline description.
     * @param by Date and time to complete task by.
     */
    public ParsedDeadline(String description, LocalDateTime by) {
        this.description = description;
        this.by = by;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getBy() {
        return by;
    }
}

