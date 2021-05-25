# spring-test-tuts
Spring Test Tutorial

# Setting up envs/profiles in vscode

Spring boot's profile can be change in two different ways (as far as I know):

1. using `application.properties` via `spring.profiles.active={dev,test,prod (just choose one)}`.
2. using device environment via `SPRING_PROFILES_ACTIVE={dev,test,prod}`

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

