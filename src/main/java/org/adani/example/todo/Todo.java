package org.adani.example.todo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.sql.Timestamp;

/* Very Basic POJO: http://jsonplaceholder.typicode.com/todos/1 */
public class Todo {

    @JsonProperty("userId")
    long userId;

    @JsonProperty("id")
    long id;

    @JsonProperty("title")
    String title;

    @JsonProperty("completed")
    boolean completed;

    Timestamp created; // for cache invalidation

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
}
