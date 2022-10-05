package pl.generators.winningnumbers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling()
public class WinningnumbersApplication {

	public static void main(String[] args) {
		SpringApplication.run(WinningnumbersApplication.class, args);
	}

}
