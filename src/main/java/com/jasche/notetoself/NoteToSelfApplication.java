package com.jasche.notetoself;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class for application that allows authenticated users to write and edit notes that are stored
 * in a database.
 */
@SpringBootApplication
public class NoteToSelfApplication {

	/**
	 * Main function for starting SpringApplication.
	 * @param args	CLI arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(NoteToSelfApplication.class, args);
	}

}
