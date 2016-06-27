package org.adani.example.todo;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:app-context.xml")
public class TodoRESTEndpointTest {

    @Autowired
    TodoRESTEndpoint ep;

    private Todo todoItem;

    /***
     * http://jsonplaceholder.typicode.com/todos/3
     ****/

    @Before
    public void setUp() {
        todoItem = new Gson().fromJson("{\"id\": 3, \"title\": \"fugiat veniam minus\", \"completed\": false, \"userId\": 1}", Todo.class);
    }

    @Test
    public void testCreate() throws Exception {
        final Response response = ep.create(todoItem);
        assertThat(response.getStatus(), anyOf(equalTo(200), equalTo(201)));
    }

    @Test
    public void testGetAll() throws Exception {
        final Response response = ep.getAll();
        assertThat(response.getStatus(), anyOf(equalTo(200), equalTo(201)));
    }


    @Test
    public void testGetTodoById() throws Exception {
        final Response response = ep.getTodoByKey(4);
        assertThat(response.getStatus(), anyOf(equalTo(200), equalTo(201)));
    }
}