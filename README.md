
# A Blog API with Spring Boot

## Table of contents
1. [Introduction](#introduction)
2. [How to access](#how-to-access)
3. [Requirements](#requirements)
4. [Dependencies](#dependencies)
5. [How to run server locally](#how-to-run-server-locally)


## Introduction
This project it's one of my learning projects to practice web development with Spring Boot. This API allows you to create and manage blog posts, comments, and users.


## How to access
You can access the API through these forms:
* React website: https://blogcaiolobo.netlify.app/
* Directly API url:  https://blog-production-e16b.up.railway.app/
* Swagger Documentation: https://blog-production-e16b.up.railway.app/swagger-ui/index.html#/


## Requirements

* Java 17
* Maven
* PostgreSQL
* Spring Boot 3
* Spring Framework
* Lombok
* Swagger


## Dependencies

There are a number of third-party dependencies used in the project. Browse the Maven ```pom.xml``` file for details of libraries and versions used.
## How to run server locally

Clone the project:

```bash
https://github.com/caioedlobo/Blog.git 
```

Go to the project directory:
```bash
cd blog-application
```

Before starting the server, navigate to ```application.properties``` file in ```src/main/resources``` path and set the variable ```spring.profiles.active=dev```. Now you must create a ```env.properties``` file that will contain:

```
#Variables

#A 256-bit HEX secret encryption key that you can generate at https://www.allkeysgenerator.com/Random/Security-Encryption-Key-Generator.aspx
secret.key=

#Spring Mail - Email and Password to send messages
spring.mail.username=
spring.mail.password=
```

Now just run ```BlogApplication.java```
