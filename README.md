# SecureSpringWebisite

# installing requsite

1- Eclipse IDE 

2- Java

3- Wildfly

4- MySql database

# Project Structure
1- Server Side project which is a rest api using JAX-RS and MySql with hibernate 
2- Client Side project with spring MVC using spring security and jwt tokens

# About the project
* This is a spring MVC prject that uses 2 wildfly servers with client and server side.
* In client side you can register and login with different roles such as ADMIN, USER and GUEST.
* After logining you can access the endpoints such as /admin or /user related to your role.
* These endpoints are protected by jwt tokens in the cookies and spring security authentication with checks the users from the server side MySql server.
* client side uses the rest api of server side project to get the data and display it. In rest api you can do CRUD operations for user entity
* You can get information about the user from their respective endpoints such as /user/info

# Technologies used
1- MySql and hibernate for storing users
2- Spring MVC for MVC design 
3- Spring security for authentication
4- JAX-RS for rest api
5- Spring beans for dependency injection
6- Jwt tokens for security
7- JSP pages for displaying
8- Wildfly for deployment

# How to install

Using the below link 

https://www.baeldung.com/eclipse-wildfly-configuration

1- download Eclipse (choose Eclipse IDE for Java EE Developers)

2- download JBoss tools extension inside the IDE (Help -> Eclipse MarketPlace) 

3- export the downloaded 2 projects (client, server) to the workspace 

4- right click on the pom.xl files and select maven update to download the dependencies



