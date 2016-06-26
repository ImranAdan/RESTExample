package org.adani.example.todo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/* Very Basic POJO: http://jsonplaceholder.typicode.com/todos/1 */
public class Todo {

    @JsonProperty("id")
    long id;

    @JsonProperty("title")
    String title;

    @JsonProperty("completed")
    boolean completed;

    @JsonProperty("userId")
    long userId;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
