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

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-context.xml")
public class TodoDAOTest {

    @Autowired
    TodoDAO todoDAO;
    private Todo todo;

    /***
     * http://jsonplaceholder.typicode.com/todos/3
     ****/

    @Before
    public void setUp() {
        todo = new Gson().fromJson("{\"id\": 3, \"title\": \"fugiat veniam minus\", \"completed\": false, \"userId\": 1}", Todo.class);
        todo.created = new Timestamp(Calendar.getInstance().getTime().getTime());
    }

    @Test
    public void testCreate() throws Exception {
        Todo record = todoDAO.create(this.todo);
        assertThat(record, is(not(nullValue())));
        assertThat(todoDAO.getCache().size(), is(equalTo(1)));
    }


    @Test
    public void testUpdate() throws Exception {
        String newTitle = "THIS IS THE NEW TITLE";
        todo.title = newTitle;
        Todo record = todoDAO.update(todo);
        assertThat(record.title, is(newTitle));
        assertThat(todoDAO.getCache().size(), is(equalTo(1)));
    }

    @Test
    public void testDelete() throws Exception {
        todoDAO.delete(this.todo);
        assertThat(todoDAO.getCache().size(), is(0));
    }
}