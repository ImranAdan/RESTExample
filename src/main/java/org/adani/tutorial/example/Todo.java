package org.adani.tutorial.example;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Basic POJO represented
 * as t
 * */


public class Todo {

    @JsonProperty("id")
     long id;
    @JsonProperty("title")
      String title;
    @JsonProperty("completed")
    boolean completed;
    @JsonProperty("userId")
    private long userId;

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


    @Override
    public String toString() {
        return "Todo{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", completed=" + completed +
                '}';
    }
}
