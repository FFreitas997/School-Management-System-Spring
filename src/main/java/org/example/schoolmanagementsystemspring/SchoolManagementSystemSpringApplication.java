package org.example.schoolmanagementsystemspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Create a Spring Banner for your application <a href="http://patorjk.com/software/taag/#p=display&f=Graffiti&t=Type%20Something%20">here</a>
 */
@SpringBootApplication
@EnableScheduling
public class SchoolManagementSystemSpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(SchoolManagementSystemSpringApplication.class, args);
    }
}