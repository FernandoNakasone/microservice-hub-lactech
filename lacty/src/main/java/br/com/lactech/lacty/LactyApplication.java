package  br.com.lactech.lacty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LactyApplication {

	public static void main(String[] args) {
		SpringApplication.run(LactyApplication.class, args);
	}

}
