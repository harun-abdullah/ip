public class Task {
    protected  String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString(){
        return "[" + getStatusIcon() + "] " + getDescription();
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    public void markAsDone() {
        this.isDone = true;
    }
}
