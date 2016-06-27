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

/**
 * Test the service endpoint; ensuring
 * we can get items from the external API and let
 * the REST Interface we define provide that
 * data to other services.
 * <p>
 * We use the standard HTTP status codes to ensure
 * feedback of service status.
 * <p>
 * This can simulate the GET of live data
 * from external APIs.
 * <p>
 * For testing we use http://jsonplaceholder.typicode.com/ to
 * mock a RESTful service we are consuming.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:application-context.xml")
public class TodoRESTEndpointTest {

    @Autowired
    TodoRESTEndpoint ep;

    private Todo todoItem;


    @Before
    public void setUp() {
        todoItem = new Gson().fromJson("{\"id\": 3, \"title\": \"fugiat veniam minus\", \"completed\": false, \"userId\": 1}", Todo.class);
    }

    @Test
    public void testCreate() throws Exception {
        final Response response = ep.postToDo(todoItem);
        assertThat(response.getStatus(), anyOf(equalTo(200), equalTo(201)));
    }

    @Test
    public void testGetTodoById() {
        final Response response = ep.getById(4);

        assertThat(response.getStatus(), anyOf(equalTo(200), equalTo(201)));
    }

    @Test
    public void testGetAll() {
        final Response response = ep.getAll();
        assertThat(response.getStatus(), anyOf(equalTo(200), equalTo(201)));
    }
}