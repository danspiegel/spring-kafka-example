package br.com.kafka.example.kafkaapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class KafkaappApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaappApplication.class, args);
	}

}
