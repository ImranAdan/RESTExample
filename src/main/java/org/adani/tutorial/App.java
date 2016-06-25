package org.adani.tutorial;


import org.adani.tutorial.example.ExampleTodoRESTTEndpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Simple CXF JAX-RS
 *
 *
 * JERSEY +  SPRING EXAMPLE
 *
 */

@SpringBootApplication
@Configuration(value = "app-context.xml")

public class App {

    public static void main(String... args){
        SpringApplication.run(App.class, args);
    }

    @Bean
    public ServletRegistrationBean dispatchServlet(){
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new CXFServlet(), "/rest-api/*");
        servletRegistrationBean.addUrlMappings("/example");
        return servletRegistrationBean;
    }

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus(){
        return new SpringBus();
    }
}
