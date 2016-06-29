# RESTful API using Spring and Apache CXF

Template for Apache CXF + Spring RESTful Based Project. 

# Background & Description

It is common requirements nowadays that internal systems developers build within organisations need retrieve information from external sources outside the organization for internal processing purposes.
 
A common method of retrieving such data is through the use of Web Services; where the REST approach is the preferred method nowadays. 

The following project is intended to solve this type of problem and provide a project template the provides the following out of the box: 

* Connects to External API; Here we use JSONPlaceholder which is a fake online REST API for Testing (Integration) and Prototyping, to retrieve some external values. 
* Save fetched entry within our own internal database, we use an embedded H2 database in this template project. 
* Local Caching of fetched entry, thread safe. We separate the task of fetching external data and serving that through our endpoints. Different threads will be responsible for achieving this.  
* Expose locally cached data through a RESTful interface. This a JAX-RS endpoint. 

### Requirements
 - Maven, Build + Dependency Management

### Example Run
Using the maven exec plugin, we can start our server as follows: 

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