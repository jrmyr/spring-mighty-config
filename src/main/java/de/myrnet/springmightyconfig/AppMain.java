package de.myrnet.springmightyconfig;

import de.myrnet.springmightyconfig.config.DefaultConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppMain {

	@Autowired
	private DefaultConfig defaultConfig;

	public static void main(String[] args) {
		SpringApplication.run(AppMain.class, args);
	}

}
