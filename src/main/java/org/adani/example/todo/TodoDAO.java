package org.adani.example.todo;

import org.slf4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.PreparedStatement;

public class TodoDAO {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(TodoDAO.class);

    final DataSource dataSource; //TODO: investigate autowiring issue

    public TodoDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Todo fetchWithToDoId(long id) {
        return new JdbcTemplate(dataSource).queryForObject("SELECT * FROM PUBLIC.TODO WHERE todo_id = ?", new Object[]{id}, getRowMapper());
    }

    public Todo fetch(Todo item) {
        Todo fetched = fetchWithToDoId(item.getId());
        return fetched;
    }

    @Transactional
    public Todo create(Todo todo) {
        Todo record = insert(todo);
        return record;
    }

    @Transactional
    public Todo update(Todo todo) {
        String sql = "UPDATE PUBLIC.TODO SET (todo_user_id, todo_id, todo_title, todo_completed) = (?,?,?,?) WHERE todo_id = ?";

        int updatedRows = new JdbcTemplate(dataSource).update(sql, new Object[]{todo.getUserId(), todo.getId(), todo.getTitle(), todo.isCompleted(), todo.getId()});
        LOGGER.info("UPDATE COMPLETE: AFFECTED " + updatedRows + " ROWS");

        return fetchWithToDoId(todo.getId());
    }


    @Transactional
    public void delete(Todo todo) {
        String sql = "DELETE FROM PUBLIC.TODO WHERE id = ?";
        int affected = new JdbcTemplate(dataSource).update(sql, new Object[]{todo.getId()});
        LOGGER.info("DELETED COMPLETE: AFFECTED " + affected + " ROWS", todo);
    }


    private Todo insert(Todo todo) {
        String sql = "INSERT INTO PUBLIC.TODO (todo_user_id, todo_id, todo_title, todo_completed) VALUES (?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rows = new JdbcTemplate(dataSource).update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, todo.getUserId());
            ps.setLong(2, todo.getId());
            ps.setString(3, todo.getTitle());
            ps.setBoolean(4, todo.isCompleted());
            return ps;
        }, keyHolder);

        LOGGER.info("INSERTION COMPLETE: CREATED " + rows + " ROWS");

        Todo fetched = fetchWithToDoId(todo.getId());
        return fetched;
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
}
