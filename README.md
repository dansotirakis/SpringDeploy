[![Gitpod ready-to-code](https://img.shields.io/badge/Gitpod-ready--to--code-blue?logo=gitpod)](https://gitpod.io/#https://github.com/dansotirakis/lab-b-spring-rest-deploy)

![Apache License, Version 2.0, January 2004](https://img.shields.io/github/license/apache/maven.svg?label=License)
[![Maven Central](https://img.shields.io/maven-central/v/org.apache.maven/apache-maven.svg?label=Maven%20Central)](https://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22org.apache.maven%22%20AND%20a%3A%22apache-maven%22)
![Java CI with Maven](https://github.com/dansotirakis/SpringDeploy/workflows/Java%20CI%20with%20Maven/badge.svg)

<img src="https://raw.githubusercontent.com/swagger-api/swagger.io/wordpress/images/assets/SWU-logo-clr.png" width="150">

![java](https://image.flaticon.com/icons/png/128/919/919854.png)
![Spring](https://hack24x7.com/img/icons/development/Spring.png)
![api](https://image.flaticon.com/icons/png/128/103/103093.png)

# SpringDeploy

  
#### Commands run start project 

- _`mvn clean package`_
- _`java -jar target/*.jar`_


#### Know endpoints conssuming:
  
  - Acess points
  
  > _127.0.0.1:8080/swagger-ui.html_
  
  > _127.0.0.1:8080/movies_

  > _127.0.0.1:8080/interval-awards_

  
#### Run tests: 
- _`mvn clean test`_


#### Stack's project:

    Java 8
    Maven
    Swagger

---

### Structure
```
├───main
│   ├───java
│   │   └───br
│   │       └───com
│   │           └───rest
│   │               └───movie
│   │                   └───api
│   │                       └───awards
│   │                           │   Awards.java
│   │                           │   lombok.config
│   │                           │
│   │                           ├───application
│   │                           │   ├───controller
│   │                           │   │       ApiMovieController.java
│   │                           │   │
│   │                           │   └───model
│   │                           │       │   EntityMovie.java
│   │                           │       │
│   │                           │       └───enumeration
│   │                           │               ChampionType.java
│   │                           │
│   │                           ├───domain
│   │                           │       IMovie.java
│   │                           │       Movie.java
│   │                           │
│   │                           ├───infrastructure
│   │                           │   ├───configuration
│   │                           │   │       SwaggerConfig.java
│   │                           │   │
│   │                           │   └───repository
│   │                           │           MovieRepository.java
│   │                           │
│   │                           └───util
│   │                               └───dto
│   │                                       EntityMovieDTO.java
│   │                                       IntervalAwardsDTO.java
│   │                                       IntervalDTO.java
│   │
│   └───resources
│           application.properties
│           movielist.csv
│           movielistTest.csv
│
└───test
    └───java
        └───br
            └───com
                └───rest
                    └───movie
                        └───api
                            └───awards
                                │   AwardsTest.java
                                │
                                ├───application
                                │   └───controller
                                │           ApiMovieControllerTest.java
                                │
                                ├───domain
                                │       MovieTest.java
                                │
                                └───infrastructure
                                    └───repository
                                            MovieRepositoryTest.java
```