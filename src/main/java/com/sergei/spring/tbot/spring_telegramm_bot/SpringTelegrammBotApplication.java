package com.sergei.spring.tbot.spring_telegramm_bot;

import com.sergei.spring.tbot.spring_telegramm_bot.repositroy.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class SpringTelegrammBotApplication implements CommandLineRunner {

	@Autowired
	PlayerRepository playerRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringTelegrammBotApplication.class, args);
	}

	@Override
	public void run(final String... args) throws Exception {

	}
}
