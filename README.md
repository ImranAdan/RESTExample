# RESTful API using Spring and Apache CXF

Template for Apache CXF + Spring REST Based Project. 

This project makes use of JSONPlaceholder which is a fake online REST API for Testing and Prototyping. In this project we gather data from the mock REST API and stored into a cache and then into a db. 

We expose that data through a JAXRS service endpoint.

We also present a background task that runs in the background and causes a trigger on a given interval. 

### JSONPlaceholder
http://jsonplaceholder.typicode.com/

### Requirements
 - Maven, Build + Dependecy Management
### Example Run
Using the maven exec plugin we can start the our server as follows: 

```sh
$ mvn exec:java -Dexec.mainClass="org.adani.example.Application"
```

The should start the server meaning that we can hit our endpoint for requests: 

http://localhost:9000/example/todos/1

```json
{
	"headers": {},
	"body": {
		"created": 1467159183563,
		"userId": 1,
		"id": 1,
		"title": "delectus aut autem",
		"completed": false
	},
	"statusCode": "OK"
}
```

Compare that with what the fake mock API provides @ http://jsonplaceholder.typicode.com/todos/1 


### TODO
Better header descriptions!