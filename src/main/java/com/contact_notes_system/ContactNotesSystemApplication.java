package com.contact_notes_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ContactNotesSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContactNotesSystemApplication.class, args);
	}

}
