package de.myrnet.springmightyconfig;

import de.myrnet.springmightyconfig.config.DefaultConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppMain implements CommandLineRunner {

	@Autowired
	private DefaultConfig defaultConfig;

	public static void main(String[] args) {
		SpringApplication.run(AppMain.class, args);
	}

	public void run(String[] params) {
		System.out.println("Config web-page:  " + defaultConfig.getWebPageGroup());
		System.out.println("Config order   :  " + defaultConfig.getOrderGroup());
	}

}
