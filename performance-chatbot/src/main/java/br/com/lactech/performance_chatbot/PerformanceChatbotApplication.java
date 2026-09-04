package br.com.lactech.performance_chatbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PerformanceChatbotApplication {

	public static void main(String[] args) {
		SpringApplication.run(PerformanceChatbotApplication.class, args);
	}

}
