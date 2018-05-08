package com.cts.trader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableJpaRepositories("com.cts.trader.repository")
@EntityScan("com.cts.trader.model")
@ComponentScan(basePackages = "com.cts.trader")
public class TraderApplication {

	public static void main(String[] args) {
		SpringApplication.run(TraderApplication.class, args);
	}
}
