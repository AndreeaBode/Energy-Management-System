package sd;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sd.dtos.DeviceDTO;
import sd.services.DeviceService;

@SpringBootApplication
public class SdApplication {

	public static void main(String[] args) {
		SpringApplication.run(SdApplication.class, args);
	}

}
