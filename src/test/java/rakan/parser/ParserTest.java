package rakan.parser;

import org.junit.jupiter.api.Test;
import rakan.RakanException;
import rakan.command.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void parse_validTodoCommand_returnsTodoCommand() throws RakanException {
        assertTrue(Parser.parse("todo read book") instanceof TodoCommand);
    }

    @Test
    void parse_validDeadlineCommand_returnsDeadlineCommand() throws RakanException {
        assertTrue(Parser.parse("deadline return book /by 2/12/2019 1800") instanceof DeadlineCommand);
    }

    @Test
    void parse_validEventCommand_returnsEventCommand() throws RakanException {
        assertTrue(Parser.parse("event project meeting /from 2/12/2019 1400 /to 2/12/2019 1600") instanceof EventCommand);
    }

    @Test
    void parse_validListCommand_returnsListCommand() throws RakanException {
        assertTrue(Parser.parse("list") instanceof ListCommand);
    }

    @Test
    void parse_aliasCommand_returnsCorrectCommand() throws RakanException {
        assertTrue(Parser.parse("t read book") instanceof TodoCommand); // alias "t" → "todo"
    }

    @Test
    void parse_invalidCommand_throwsException() {
        assertThrows(RakanException.class, () -> Parser.parse("nonsense stuff"));
    }

    @Test
    void parse_emptyInput_throwsException() {
        assertThrows(RakanException.class, () -> Parser.parse(""));
    }

    @Test
    void validateTaskNumber_validNumber_returnsNumber() throws RakanException {
        assertEquals(2, Parser.validateTaskNumber("2", 3));
    }

    @Test
    void validateTaskNumber_outOfRange_throwsException() {
        assertThrows(RakanException.class, () -> Parser.validateTaskNumber("5", 3));
    }

    @Test
    void validateTaskNumber_nonInteger_throwsException() {
        assertThrows(RakanException.class, () -> Parser.validateTaskNumber("abc", 3));
    }

    @Test
    void formatStringToDate_validDate_returnsLocalDateTime() throws RakanException {
        LocalDateTime expected = LocalDateTime.of(2019, 12, 2, 18, 0);
        assertEquals(expected, Parser.formatStringToDate("2/12/2019 1800"));
    }

    @Test
    void formatStringToDate_invalidFormat_throwsException() {
        assertThrows(RakanException.class, () -> Parser.formatStringToDate("2019-12-02 18:00"));
    }
}
