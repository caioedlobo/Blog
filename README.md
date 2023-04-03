
# A Blog API with Spring Boot

This API allows you to create and manage blog posts, comments, and users.


## Requirements

* Java 17
* Maven
* PostgreSQL
* Spring Boot 3
* Spring Framework
* Lombok
* Swagger



## Dependencies

There are a number of third-party dependencies used in the project. Browse the Maven pom.xml file for details of libraries and versions used.
## How to run server?

Before starting the server, navigate to application.properties file in ```src/main/resources``` path and set ```spring.profiles.active=dev```. Now you must create a env.properties file that will contain:

```
#Variables

#A 256-bit HEX secret encryption key that you can generate at https://www.allkeysgenerator.com/Random/Security-Encryption-Key-Generator.aspx
secret.key=

#Spring Mail - Email and Password of to send messages
spring.mail.username=
spring.mail.password=
```
