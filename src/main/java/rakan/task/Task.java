package rakan.task;

public class Task {
    protected  String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns status of task as a string
     * If the task is done, return "X"
     * If the task is not done, return " "
     *
     * @return task status
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Returns description of task as String
     *
     * @return task description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns task status as boolean
     *
     * @return task status (boolean)
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns task status and description in one String
     *
     * @return task status and description
     */
    @Override
    public String toString(){
        return "[" + getStatusIcon() + "] " + getDescription();
    }

    /**
     * Marks task as not done
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Marks task as done
     */
    public void markAsDone() {
        this.isDone = true;
    }
}
