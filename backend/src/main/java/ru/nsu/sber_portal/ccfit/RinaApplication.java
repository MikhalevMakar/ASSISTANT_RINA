package ru.nsu.sber_portal.ccfit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class RinaApplication {

	public static void main(String[] args) {
		SpringApplication.run(RinaApplication.class, args);
	}
}
