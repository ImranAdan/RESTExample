package org.adani.example.todo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.sql.Timestamp;

public class Todo {

    @JsonProperty("userId")
    private long userId;

    @JsonProperty("id")
    private long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("completed")
    private boolean completed;

    private Timestamp created;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }


    @Override
    public boolean equals(Object obj) {
        if ((obj == null) || !(obj instanceof Todo)) return false;
        final Todo other = (Todo) obj;
        return new EqualsBuilder().append(id, other.id).append(created, other.created).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(created).toHashCode();
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
