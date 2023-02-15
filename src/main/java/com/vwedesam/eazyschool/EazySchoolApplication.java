package com.vwedesam.eazyschool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.vwedesam.eazyschool.repository")
@EntityScan("com.vwedesam.eazyschool.model")
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class EazySchoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(EazySchoolApplication.class, args);
	}

}
