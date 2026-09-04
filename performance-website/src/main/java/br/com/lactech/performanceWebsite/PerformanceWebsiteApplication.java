package br.com.lactech.performanceWebsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PerformanceWebsiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(PerformanceWebsiteApplication.class, args);
	}

}
