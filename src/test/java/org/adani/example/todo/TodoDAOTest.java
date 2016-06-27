package org.adani.example.todo;

import com.google.gson.Gson;
import org.junit.Before;
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
 * Testing our JDBC backed DAO.
 * We test the standard CRUD operations
 * <p>
 * CREATE, READ, UPDATE, DELETE
 * <p>
 * create(),fetch(),update(),delete()
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-context.xml")
public class TodoDAOTest {

    @Autowired
    TodoDAO todoDAO;


    private Todo todo;

    /***
     * http://jsonplaceholder.typicode.com/todos/3
     **/

    @Before
    public void setUp() {
        todo = new Gson().fromJson("{\"id\": 1, \"title\": \"fugiat veniam minus\", \"completed\": false, \"userId\": 1}", Todo.class);
        todo.created = new Timestamp(Calendar.getInstance().getTime().getTime());
    }


    @Test
    public void testCreate() throws Exception {
        Todo record = todoDAO.create(this.todo);
        assertThat(record, is(not(nullValue())));
        assertTrue(todoDAO.getCache().containsKey(record.id));
    }

    @Test
    public void testFetch() throws Exception {
        Todo todo = new Gson().fromJson("{\"id\": 2, \"title\": \"fugiat veniam minus\", \"completed\": false, \"userId\": 1}", Todo.class);
        todo.created = new Timestamp(Calendar.getInstance().getTime().getTime());

        Todo record = todoDAO.create(todo);
        assertThat(record, is(not(nullValue())));

        Todo fetched = todoDAO.fetch(record);
        assertThat(fetched, is(not(nullValue())));
    }


    @Test
    public void testUpdate() throws Exception {

        Todo todo = new Gson().fromJson("{\"id\": 3, \"title\": \"fugiat veniam minus\", \"completed\": false, \"userId\": 1}", Todo.class);
        todo.created = new Timestamp(Calendar.getInstance().getTime().getTime());

        Todo createdRecordForUpdate = todoDAO.create(todo);
        assertThat(createdRecordForUpdate, is(not(nullValue())));

        String newTitle = "THIS IS THE NEW TITLE NOW";
        createdRecordForUpdate.title = newTitle;
        createdRecordForUpdate.created = new Timestamp(Calendar.getInstance().getTime().getTime());

        Todo updated = todoDAO.update(createdRecordForUpdate);
        assertThat(updated.title, is(newTitle));
        assertThat(todoDAO.getCache().get(updated.id), is(equalTo(updated)));
    }

    @Test
    public void testDelete() throws Exception {
        todoDAO.delete(todo);
        assertTrue(!todoDAO.getCache().containsKey(todo.id));
    }
}