package org.adani.tutorial.example;

/**
 * Basic POJO represented
 * as the resource from the URI
 */
public class Todo {

    private final long userId;
    private final long id;
    private final String title;
    private final boolean completed;

    public Todo(long userId, long id, String title, boolean completed) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.completed = completed;
    }

    public long getUserId() {
        return userId;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }
}
