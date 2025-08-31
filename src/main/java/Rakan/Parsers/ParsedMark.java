package Rakan.Parsers;

public class ParsedMark {
    private final int taskIndex;
    private final boolean isMark;

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

