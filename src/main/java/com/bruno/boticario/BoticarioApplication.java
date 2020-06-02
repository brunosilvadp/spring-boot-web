package com.bruno.boticario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@SpringBootApplication
@EntityScan(basePackages = {
            "com.bruno.boticario.model"
            })
@EnableJpaRepositories(basePackages = {
	"com.bruno.boticario.repository"
})
public class BoticarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoticarioApplication.class, args);
	}

}
