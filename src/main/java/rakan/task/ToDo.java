package rakan.task;

public class ToDo extends Task {

    /**
     * Constructs ToDo task
     *
     * @param description Task description
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Returns task type and description
     *
     * @return task type and description
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
