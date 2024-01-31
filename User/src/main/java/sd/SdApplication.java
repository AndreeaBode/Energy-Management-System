package sd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import sd.dtos.RegisterDTO;
import sd.entities.User;
import sd.servives.UserService;

@SpringBootApplication
public class SdApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SdApplication.class, args);

		UserService userService = context.getBean(UserService.class);

		RegisterDTO adminDTO = new RegisterDTO();
		adminDTO.setEmail("admin@yahoo.com");
		adminDTO.setPassword("admin");
		adminDTO.setConfirmedPassword("admin");
		adminDTO.setRole("Admin");

		User existingAdmin = userService.getUserByEmail(adminDTO.getEmail());
		if (existingAdmin == null) {
			userService.register(adminDTO);
			System.out.println("Contul de administrator a fost creat cu succes!");
		} else {
			System.out.println("Un cont de administrator cu această adresă de email există deja.");
		}
	}

}
