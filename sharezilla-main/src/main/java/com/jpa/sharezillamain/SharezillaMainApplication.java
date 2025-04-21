package com.jpa.sharezillamain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity(prePostEnabled = true)
@SpringBootApplication
public class SharezillaMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(SharezillaMainApplication.class, args);
    }

}
