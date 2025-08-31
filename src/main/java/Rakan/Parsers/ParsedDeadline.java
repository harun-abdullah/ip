package Rakan.Parsers;

import java.time.LocalDateTime;

public class ParsedDeadline {
    private final String description;
    private final LocalDateTime by;

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

