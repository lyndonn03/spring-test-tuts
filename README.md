# TL;DR

A project that focuses on Spring Boot Test with `JUnit5` and `Assertions` Libraries.


<br />

# Why did I do this?

To practice how to create backend applications using Spring Framework with Spring Boot again.

<br />
<br />


# Setting up envs/profiles in vscode

Spring boot's profile can be change in two different ways (as far as I know):

1. using `application.properties` via `spring.profiles.active={dev,test,prod (just choose one)}`.
2. using device environment via `SPRING_PROFILES_ACTIVE={dev,test,prod}`


IMPORTANT NOTES (Please don't skip these or you will spend hours to debug why you can't run integration tests using testcontainers): 
1. for test env, add `application-test.properties` in `src/main/resources` and add your application properties for test env here.
2. similar to test env, add `application-dev.properties` in `src/main/resources` for dev env.

---

To change the profile when running using docker compose, add these lines to service:
```
    backend:
        ...
        environment:
            - SPRING_PROFILES_ACTIVE=dev
```
---

To change the profile when testing or running test in vscode, first go to workspace settings, search for `Java Test Runner` and click `Edit in settings.sjon` under `Java > test: Config` to add this line:
```
{
    "java.test.config": {
        ...
        "env": {
            "SPRING_PROFILES_ACTIVE": "test"
        }
    }
}
```

<br />
<br />


# How to Fix `pom.xml`/maven error

Error Message:
```
Failed to execute goal org.apache.maven.plugins:maven-compiler
```

Steps (in development env using docker compose):

1. Run `docker compose down -v` to remove containers and volumes.
2. Run `docker compose up` to recreate the project

Reason:
> Some maven bug that pops up sometimes when you install maven dependencies. <br />
I just included here the steps I know on how to fix this cumbersome maven error which prevents you to build your application.

