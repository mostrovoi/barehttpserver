# Bare httpserver

[![Build Status](https://travis-ci.org/mostrovoi/barehttpserver.svg?branch=master)](https://travis-ci.org/mostrovoi/barehttpserver)
[![Quality Gate](https://sonarcloud.io/api/badges/gate?key=com.schibsted.server%3Atest-web-application)](https://sonarcloud.io/dashboard/index/com.schibsted.server%3Atest-web-application)

Implementation of a basic web server using  core Java classes. 
Following libraries are in use: **Gson** for serializing DTO to json, **Mustache** as templating engine, **Spock** for unit and dobule tests, **Guava** for session cache, **Gradle** as build tool and **log4j2** for logging purposes.

## Run the application

This application has been made using **gradle**. To build the application type the following command:
'''
gradle clean build
'''

To run the application once it is build:
''''
java -jar /build/libs/test-web-application-1.0.jar
''''

This will start a web server listening on localhost and port 8080. 

## Using the application

Start navigation by going to the login page:

* http://localhost:8080/login 

The following pages can be also tested, given the authenticated user has the needed role:

Webpage  | Role |
-------- | -----|
* http://localhost:8080/private/  |  ANY   |
* http://localhost:8080/private/page1 | PAGE_1 |
* http://localhost:8080/private/page2 | PAGE_2 | 
* http://localhost:8080/private/page3 | PAGE_3 |
* http://localhost:8080/private/logout | ANY   | 

### Availabe users

Username | Password | Role |
---------| -------- | ---- |
user1    |  user1   | PAGE_1 |
user2    |  user2   | PAGE_2 |
user3    |  user3   | PAGE_3 |
user4    |  user4   | PAGE_1, PAGE_2, PAGE_3 |
user5    |  user5   | PAGE_2, PAGE_3 |
admin    | admin    | ADMIN |



## API Rest

This application provides also a REST API for user management. Only authenticated users can use it. 
Users with role ADMIN can modify, create and delete other users via the REST API. The rest of authenticated users can only read data via the API. 

The authentication is done with Basic Auth. Only alphanumerical values and non capital letters are accepted as valid usernames

### Read all users 
```
curl -i -H "Content-Type: application/json" -X GET http://localhost:8080/api/users --user user1:user1
```
### Read user1
```
curl -i -H "Content-Type: application/json" -X GET http://localhost:8080/api/users/user1 --user user1:user1
```
### User deletion
```
curl -i -H "Content-Type: application/json" -X DELETE http://localhost:8080/api/users/user1 --user admin:admin
```
### User update (must exist previously)
```
curl -i -H "Content-Type: application/json" -X PUT -d '{"username":"user2","password":"test","roles":["PAGE_1"]}' http://localhost:8080/api/users/user2 --user admin:admin
```
### User creation
```
curl -i -H "Content-Type: application/json" -X POST -d '{"username":"newuser","password":"test","roles":["ADMIN","PAGE_1"]}' http://localhost:8080/api/users --user admin:admin
```


