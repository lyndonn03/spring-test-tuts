package com.lpamintuan.backend;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

public class ContainersForTest {
    
    private static final PostgreSQLContainer<?> container = new PostgreSQLContainer("postgres:13.3-alpine")
        .withDatabaseName("springtest").withPassword("user").withUsername("user");


    static {
        container.start();
    }


    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);

    }
}
