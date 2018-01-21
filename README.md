# Bare httpserver

[![Build Status](https://travis-ci.org/mostrovoi/barehttpserver.svg?branch=master)](https://travis-ci.org/mostrovoi/barehttpserver)
[![Quality Gate](https://sonarcloud.io/api/badges/gate?key=com.schibsted.server%3Atest-web-application)](https://sonarcloud.io/dashboard/index/com.schibsted.server%3Atest-web-application)


## Run the application

This application has been made using gradle. To build the application type the following command:
'''
gradle clean build
'''

To run the application once it is build:
''''
jjava -jar /build/libs/test-web-application-1.0.jar
''''
This will start a web server listening on localhost and port 8080. 

Start navigation by going to the login page:

* http://localhost:8080/login 

The following pages can be also tested:

* http://localhost:8080/private/page1
* http://localhost:8080/private/page2
* http://localhost:8080/private/page3


#### This project is analysed on [SonarCloud](https://sonarcloud.io)!

Implementation of a basic web server using only core java classes
