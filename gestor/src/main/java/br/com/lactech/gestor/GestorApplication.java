package br.com.lactech.gestor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GestorApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestorApplication.class, args);
	}

}
