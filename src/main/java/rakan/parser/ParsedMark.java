package rakan.parser;

/**
 * Class to contain constructor parameters for handleMark.
 */
public class ParsedMark {
    private final int taskIndex;
    private final boolean isMark;

    /**
     * Constructs ParsedMark object.
     *
     * @param taskIndex Index of task to be marked/unmarked.
     * @param isMark Boolean to indicate whether to mark/unmark.
     */
    public ParsedMark(int taskIndex, boolean isMark) {
        this.taskIndex = taskIndex;
        this.isMark = isMark;
    }

    public int getTaskIndex() {
        return taskIndex;
    }

    public boolean isMark() {
        return isMark;
    }
}

