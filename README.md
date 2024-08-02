# Logical Expression Evaluator Documentation

This document describes the endpoints and usage examples for a Logical Expression Evaluator API.

# Running the application

## Using Maven
Navigate to the project folder and run:

`mvn spring-boot:run`

By default, the application starts the Tomcat server on port 8080. Server port can be modified in the `application.properties` file at:

`src/main/resources/application.properties`

The base URL will include current application version, which is `v1`:
`http://localhost:8080/v1`

# Endpoints

## 'expression' endpoint
```
/expression
```

### API Input

This endpoint takes the `name` of the logical expression and its `value` in JSON format, for example:

URL:

```
http://localhost:8080/v1/expression
```
Request body:
```
{
	"name":"Complex logical expression",
	"value":"(customer.firstName == \"MARK\" AND customer.salary < 100) OR (customer.address != null && customer.address.city == \"Washington\")"
}
```

Double quotes in JSON values should always be escaped using the escape character, for example:
```\"MARK\"```

Requests should be sent using HTTP POST method.

### API Response

For each request executed against the endpoint, API returns a unique identifier (`id`) that represents the identifier of the stored logical expression.

Example response:

```
{
	"id": 1
}
```
This `id` will have to be used against the `evaluate` endpoint as described in the following chapters.

## 'evaluate' endpoint
```
/evaluate/{id}
```

### API Input

This API endpoint takes expression ID (through URL) and JSON object (through request body) as input, for example:

URL:

```
http://localhost:8080/v1/evaluate/1
```
Request body:
```
{
	"customer":
	{
		"firstName": "MARK",
		"lastName": "DOE",
		"address":
		{
			"city": "Chicago",
			"zipCode": 1234,
			"street": "56th",
			"houseNumber": 2345
		},
		"salary": 99,
		"type": "BUSINESS"
	}
}
```
Requests should be sent using HTTP POST method.

### API Response

This API returns the `result` of evaluation by using the requested expression and provided JSON object.

The result will be a boolean (`true` or `false`) value.

Example response:

```
{
	"result": true
}
```
# Error messages
In case of errors, application will return JSON response containing `code` and `message` values, for example:

Example response:

```
{
	"code": 404,
	"message": "Not Found"
}
```