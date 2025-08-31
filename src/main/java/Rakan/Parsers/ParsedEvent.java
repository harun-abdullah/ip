package Rakan.Parsers;

import java.time.LocalDateTime;

public class ParsedEvent {
    private final String description;
    private final LocalDateTime from;
    private final LocalDateTime to;

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

