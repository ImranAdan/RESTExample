package org.adani.example.todo;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;


/**
 * The DAO responsible for handling database related functions. e.g. Read from or write to when required.
 */
public class TodoDAO {


    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(TodoDAO.class);

    private static final long CACHE_REFRESH_RATE = 900000L; // Refresh the cache every 10 minutes for example
    private final Map<Long, Todo> cache;
    @Autowired
    DataSource dataSource;

    public TodoDAO() {
        cache = new HashMap<>();
    }

    public Todo fetch(Todo todo) {

        if (cache.containsKey(todo.id) && !expired(todo))
            return cache.get(todo);

        final Todo record = fetchWithId(todo.id);
        cacheRecord(record);
        return record;
    }

    private Todo fetchWithId(long id) {
        return new JdbcTemplate(dataSource).queryForObject("SELECT * FROM PUBLIC.TODO WHERE todo_id = ?", new Object[]{id}, getMapper());
    }

    @Transactional
    public Todo create(Todo todo) {
        Todo record = insert(todo);
        return record;
    }

    private Todo insert(Todo todo) {
        final String sql = "INSERT INTO PUBLIC.TODO (todo_user_id, todo_id, todo_title, todo_completed) VALUES (?,?,?,?)";  //SQL
        KeyHolder keyHolder = new GeneratedKeyHolder();

        //BEGIN INSERT OPERATION
        int updated = new JdbcTemplate(dataSource).update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, todo.userId);
            ps.setLong(2, todo.id);
            ps.setString(3, todo.title);
            ps.setBoolean(4, todo.completed);
            return ps;
        }, keyHolder);

        LOGGER.info("INSERTION COMPLETE: CREATED " + updated + " ROWS");
        final Todo record = fetchWithId(todo.id);
        cacheRecord(record);
        return record;
    }

    private void cacheRecord(Todo record) {
        if (!cache.containsKey(record.id))
            cache.put(record.id, record);
    }

    private boolean expired(Todo todo) {
        final long timeInCache = System.currentTimeMillis() - todo.created.getTime();
        return timeInCache > CACHE_REFRESH_RATE;
    }

    @Transactional
    public Todo update(Todo todo) {
        final String sql = "UPDATE PUBLIC.TODO SET (todo_user_id, todo_id, todo_title, todo_completed) = (?,?,?,?) WHERE todo_id = ?";//SQL
        final int updatedRows = new JdbcTemplate(dataSource).update(sql, new Object[]{todo.userId, todo.id, todo.title, todo.completed, todo.id});
        LOGGER.info("UPDATE COMPLETE: AFFECTED " + updatedRows + " ROWS");
        final Todo record = fetchWithId(todo.id);
        cacheRecord(record);
        return record;
    }

    @Transactional
    public void delete(Todo todo) {

        if (cache.containsKey(todo.id))
            cache.remove(todo);

        final String sql = "DELETE FROM PUBLIC.TODO WHERE id = ?";
        int affected = new JdbcTemplate(dataSource).update(sql, new Object[]{todo.id});
        LOGGER.info("DELETED COMPLETE: AFFECTED " + affected + " ROWS", todo);
    }


    public Map<Long, Todo> getCache() {
        return cache;
    }

    private RowMapper<Todo> getMapper() {
        return (resultSet, i) -> new Todo(resultSet.getInt("todo_user_id"), resultSet.getInt("todo_id"),
                resultSet.getString("todo_title"), resultSet.getBoolean("todo_completed"), resultSet.getTimestamp("todo_created"));
    }
}
