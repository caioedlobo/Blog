
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

Before starting the server, create application-dev.properties file in src/main/resources path and use that template.

```
#Variables
application.url=
secret.key=

#Database
server.port=
spring.jpa.database=
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=
spring.jpa.properties.hibernate.dialect=

#Mail
spring.mail.username=
spring.mail.password=
spring.mail.host=
spring.mail.port=
spring.mail.properties.mail.debug=
spring.mail.properties.mail.transport.protocol=
spring.mail.properties.mail.smtp.auth=
spring.mail.properties.mail.smtp.starttls.enable=
```
