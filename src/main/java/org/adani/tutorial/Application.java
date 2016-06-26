package org.adani.tutorial;


import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.adani.tutorial.example.ExampleTodoRESTTEndpoint;
import org.adani.tutorial.example.Todo;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * Simple CXF JAX-RS Example
 *
 */

//@SpringBootApplication
//@Configuration(value = "app-context.xml")
public class Application {

    private final static Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String... args){
        LOGGER.info("STARTING JAXRS SERVER THROUGH APPLICATION CONTEXT: SPRING!");

        //Specify the application context

        ApplicationContext context = new FileSystemXmlApplicationContext("C:\\Users\\Imran\\IdeaProjects\\RESTExample\\src\\main\\resources\\app-context.xml");
       // final JAXRSServerFactoryBean restServer = (JAXRSServerFactoryBean) context.getBean("restServer");
     //   restServer.create();
//        JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
//        List<Object> endPointBeans = Arrays.asList(new ExampleTodoRESTTEndpoint());
//        sf.setServiceBeans(endPointBeans);
//
//        List<? extends Object> providers = Arrays.asList(new JacksonJaxbJsonProvider());
//        sf.setProviders(providers);
//
//        sf.setAddress("http://localhost:9000/");
//        sf.create();
    }



    private static void workingTemplateExample() {

        String ep = "http://jsonplaceholder.typicode.com/todos";

        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


        final Todo[] forObject = restTemplate.getForObject(ep, Todo[].class);
        for(Todo t: forObject)
            System.out.println(t.toString());

    }
}
