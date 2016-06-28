# RESTful API using Spring and Apache CXF

Template for Spring & Apache CXF REST Based Project. 

This project makes use of JSONPlaceholder which is a fake online REST API for Testing and Prototyping.

In this project we gather data from the mock REST API and stored into a cache
and then into a db. We expose that data through a JAXRS service endpoint.

JSONPlaceholder

http://jsonplaceholder.typicode.com/

### Requirements

 - Maven

### Example Run
 
  mvn exec:java -Dexec.mainClass="org.adani.example.Application"