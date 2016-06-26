package org.adani.example.todo;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * The DAO responsible for
 * handling database related
 * functions. e.g. Read from or write to
 * when required.
 */
public class TodoDAO {

    final Map<Long, Todo> cache;
    @Autowired
    DataSource dataSource;

    public TodoDAO() {
        cache = new HashMap<>();
    }

    public Todo create(Todo todo) {
        Todo record = insert(todo);
        return record;
    }

    public Todo update(Todo todo) {
        throw new NotImplementedException("Implement insert how you desire");
    }

    public void delete(Todo todo) {
        throw new NotImplementedException("Implement update how you desire");
    }

    private Todo insert(Todo todo) {
        throw new NotImplementedException("Implement delete how you desire");
    }

}
