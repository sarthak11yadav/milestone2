package net.milestone2;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info=@Info(title = "Wallet",version = "2.0",description = "making a digital wallet"))
public class Milestone2Application {

	public static void main(String[] args) {
		SpringApplication.run(Milestone2Application.class, args);
	}

}
