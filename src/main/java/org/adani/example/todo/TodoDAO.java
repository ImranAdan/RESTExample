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


public class TodoDAO {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(TodoDAO.class);
    private static final long CACHE_REFRESH_RATE = 600000L; // For Cache Invalidation
    private final Map<Long, Todo> cache;

    @Autowired
    DataSource dataSource;

    public TodoDAO() {
        cache = new HashMap<>();
    }

    public Todo fetchWithId(long id) {
        return new JdbcTemplate(dataSource).queryForObject("SELECT * FROM PUBLIC.TODO WHERE todo_id = ?", new Object[]{id}, getRowMapper());
    }

    public Todo fetch(Todo item) {

        if (!expired(item))
            return cache.get(item.getId());

        Todo cachedEntry = cacheEntry(item);
        return cachedEntry;
    }

    private Todo cacheEntry(Todo todo) {
        Todo record = fetchWithId(todo.getId());
        Todo previous = cache.put(record.getId(), record);
        Todo current = cache.get(record.getId());
        LOGGER.info("REMOVED: " + previous);
        LOGGER.info("ADDED: " + current);
        return current;
    }


    @Transactional
    public Todo create(Todo todo) {
        Todo record = insert(todo);
        return record;
    }

    private Todo insert(Todo todo) {
        String sql = "INSERT INTO PUBLIC.TODO (todo_user_id, todo_id, todo_title, todo_completed) VALUES (?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int updated = new JdbcTemplate(dataSource).update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, todo.getUserId());
            ps.setLong(2, todo.getId());
            ps.setString(3, todo.getTitle());
            ps.setBoolean(4, todo.isCompleted());
            return ps;
        }, keyHolder);

        LOGGER.info("INSERTION COMPLETE: CREATED " + updated + " ROWS");

        Todo cachedEntry = cacheEntry(todo);
        return cachedEntry;
    }

    private boolean expired(Todo todo) {
        long timeInCache = System.currentTimeMillis() - todo.getCreated().getTime();
        return cache.containsKey(todo.getId()) && timeInCache > CACHE_REFRESH_RATE;
    }

    @Transactional
    public Todo update(Todo todo) {
        String sql = "UPDATE PUBLIC.TODO SET (todo_user_id, todo_id, todo_title, todo_completed) = (?,?,?,?) WHERE todo_id = ?";//SQL

        int updatedRows = new JdbcTemplate(dataSource).update(sql, new Object[]{todo.getUserId(), todo.getId(), todo.getTitle(), todo.isCompleted(), todo.getId()});
        LOGGER.info("UPDATE COMPLETE: AFFECTED " + updatedRows + " ROWS");

        Todo cachedEntry = cacheEntry(todo);
        return cachedEntry;
    }


    @Transactional
    public void delete(Todo todo) {

        String sql = "DELETE FROM PUBLIC.TODO WHERE id = ?";
        int affected = new JdbcTemplate(dataSource).update(sql, new Object[]{todo.getId()});
        LOGGER.info("DELETED COMPLETE: AFFECTED " + affected + " ROWS", todo);

        if (cache.containsKey(todo.getId()))
            cache.remove(todo.getId());
    }

    public Map<Long, Todo> getCache() {
        return cache;
    }

    private RowMapper<Todo> getRowMapper() {
        return (resultSet, i) -> {
            Todo d = new Todo();
            d.setUserId(resultSet.getInt("todo_user_id"));
            d.setId(resultSet.getInt("todo_id"));
            d.setTitle(resultSet.getString("todo_title"));
            d.setCompleted(resultSet.getBoolean("todo_completed"));
            d.setCreated(resultSet.getTimestamp("todo_created"));
            return d;
        };
    }

    public void refreshCache() {
        cache.values().stream().filter(toRefresh -> expired(toRefresh)).forEach(toRefresh -> {
            Todo refreshedEntity = cacheEntry(toRefresh);
            LOGGER.info("APPLIED REFRESH ON:\n" + refreshedEntity.toString());
        });
    }
}
