package org.adani.example.todo;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.Calendar;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Basic test of our JDBC backed DAO.
 * We test the standard CRUD operations
 * <p>
 *     CREATE, READ, UPDATE, DELETE
 * <p>
 *
 *     CRUD test are independent of each other
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-context.xml")
public class TodoDAOTest {

    @Autowired
    TodoDAO todoDAO;

    @Test
    public void testCreate() throws Exception {
        //CREATE A RECORD
        Todo todo = new Gson().fromJson("{\"id\": 1, \"title\": \"fugiat veniam minus\", \"completed\": false, \"userId\": 1}", Todo.class);
        todo.setCreated(new Timestamp(Calendar.getInstance().getTime().getTime()));

        Todo record = todoDAO.create(todo);

        assertThat(record, is(not(nullValue())));
        assertTrue(todoDAO.getCache().containsKey(record.getId()));
    }

    @Test
    public void testFetch() throws Exception {
        //CREATE A RECORD THEN FETCH IT
        Todo todo = new Gson().fromJson("{\"id\": 2, \"title\": \"fugiat veniam minus\", \"completed\": false, \"userId\": 1}", Todo.class);
        todo.setCreated(new Timestamp(Calendar.getInstance().getTime().getTime()));

        Todo record = todoDAO.create(todo);

        assertThat(record, is(not(nullValue())));

        Todo fetched = todoDAO.fetch(record);
        assertThat(fetched, is(not(nullValue())));
    }

    @Test
    public void testUpdate() throws Exception {
        // CREATE RECORD, THEN UPDATE IT
        Todo todo = new Gson().fromJson("{\"id\": 3, \"title\": \"fugiat veniam minus\", \"completed\": false, \"userId\": 1}", Todo.class);
        todo.setCreated(new Timestamp(Calendar.getInstance().getTime().getTime()));

        Todo createdRecordForUpdate = todoDAO.create(todo);
        assertThat(createdRecordForUpdate, is(not(nullValue())));

        String newTitle = "THIS IS THE NEW TITLE NOW";
        createdRecordForUpdate.setTitle(newTitle);
        createdRecordForUpdate.setCreated(new Timestamp(Calendar.getInstance().getTime().getTime()));

        Todo updated = todoDAO.update(createdRecordForUpdate);
        assertThat(updated.getTitle(), is(newTitle));
        assertThat(todoDAO.getCache().get(updated.getId()), is(equalTo(updated)));
    }

    @Test
    public void testDelete() throws Exception {
        // CREATE RECORD, THEN DELETE IT
        Todo todo = new Gson().fromJson("{\"id\": 4, \"title\": \"fugiat veniam minus\", \"completed\": false, \"userId\": 1}", Todo.class);
        todo.setCreated(new Timestamp(Calendar.getInstance().getTime().getTime()));

        todoDAO.delete(todo);
        assertTrue(!todoDAO.getCache().containsKey(todo.getId()));
    }
}