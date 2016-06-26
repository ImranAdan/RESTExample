package org.adani.example.todo;

import com.google.gson.Gson;
import org.apache.commons.lang.NotImplementedException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:app-context.xml")
public class TodoDAOTest {

    @Autowired
    TodoDAO todoDAO;
    private Todo todo; /* http://jsonplaceholder.typicode.com/todos/3 */

    @Before
    public void setUp() {
        todo = new Gson().fromJson("{\"id\": 3, \"title\": \"fugiat veniam minus\", \"completed\": false, \"userId\": 1}", Todo.class);
    }

    @Test(expected = NotImplementedException.class)
    public void testCreate() throws Exception {
        Todo record = todoDAO.create(this.todo);
        assertThat(record, is(not(nullValue())));
    }


    @Test(expected = NotImplementedException.class)
    public void testUpdate() throws Exception {
        Todo record = todoDAO.update(this.todo);
        assertThat(record, is(not(nullValue())));
    }

    @Test(expected = NotImplementedException.class)
    public void testDelete() throws Exception {
        todoDAO.delete(this.todo);
    }
}