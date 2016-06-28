package org.adani.example;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Simple CXF JAX-RS Example
 */
public class Application {

    private final static Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String... args){
        LOGGER.info("STARTING JAX-RS SERVER THROUGH APPLICATION CONTEXT: SPRING!");
        new ClassPathXmlApplicationContext("application-context.xml");
    }
}
