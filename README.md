# Jetty server template
Simple template server with RestEasy REST API

## Compile
```
mvn clean install
```

## Run
```
java -jar server-1.0.jar
```

or

```
java -jar server-1.0.jar --port 8080
```


## Test 
```
GET http://localhost:4444/rest/echo
```

### Security
 
A dummy session service knows only two users:
* admin (with an Admin role) and
* user (with a User role)

(both have password set to "password")


#### Log in with 'user' or 'admin'
```
GET http://localhost:4444/rest/login?username=user&password=password

GET http://localhost:4444/rest/login?username=admin&password=password
```

#### Read session id
The response will return a session in the `X-SessionId` cookie.
Call the following REST with `X-SessionId` header:


#### Make call to 'private' RESTs
```
GET http://localhost:4444/rest/info
```
Only **admin** has access to following REST API:
```
GET http://localhost:4444/rest/private
```
