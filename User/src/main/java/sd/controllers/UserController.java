package sd.controllers;

import org.springframework.http.HttpStatus;
import sd.dtos.LoginDTO;
import sd.dtos.LoginResponseDTO;
import sd.dtos.RegisterDTO;
import sd.dtos.UserDTO;
import sd.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sd.servives.UserService;
import javax.validation.Valid;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping(value = "/")
public class UserController {
    private final UserService userService;

    @Value("${jwt.key}")
    private String jwtKey;

    @Value("${jwt.issuer}")
    private String jwtIssuer;

    @Value("${jwt.audience}")
    private String jwtAudience;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO model) {
        User user = userService.register(model);
        if (user == null) {
            return ResponseEntity.badRequest().body("Registration failed.");
        }

        String token = userService.generateJwtToken(user, jwtKey, jwtIssuer, jwtAudience);
        LoginResponseDTO tokenResponse = new LoginResponseDTO(token);

        System.out.println(ResponseEntity.ok(tokenResponse));
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO model) {
        User user = userService.login(model);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        String token = userService.generateJwtToken(user, jwtKey, jwtIssuer, jwtAudience);
        LoginResponseDTO tokenResponse = new LoginResponseDTO(token);

        return ResponseEntity.ok(tokenResponse);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO>  user = userService.getAllUsers();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/getUserByID/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") int userId) {
        UserDTO  user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") int id, @Valid @RequestBody  UserDTO userDTO){
        System.out.println(id);
        System.out.println("heiiii");
        UserDTO dto = userService.updateUser(id, userDTO);
        System.out.println("mor");
        System.out.println(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable("id") int id) {
        System.out.println("del" + id);
        UserDTO dto = userService.deleteUser(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
