package dev.edwardlewis.PAPL;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class PAPL extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(PAPL.class, args);
    }
}
