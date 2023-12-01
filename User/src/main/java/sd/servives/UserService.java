package sd.servives;

import jakarta.persistence.EntityNotFoundException;
import sd.dtos.LoginDTO;
import sd.dtos.RegisterDTO;
import sd.dtos.UserDTO;
import sd.dtos.builders.UserBuilder;
import sd.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sd.repositories.UserRepository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLOutput;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(RegisterDTO model) {
        User existingUser = userRepository.findByEmail(model.getEmail());

        if (existingUser != null) {
            throw new RuntimeException("A user with this email already exists.");
        }

        if (!model.getPassword().equals(model.getConfirmedPassword())) {
            throw new RuntimeException("Passwords do not match.");
        }

        String[] emailParts = model.getEmail().split("@");
        String name = emailParts[0];

        String hashedPassword = hashPassword(model.getPassword());

        User user = new User();
        user.setName(name);
        user.setEmail(model.getEmail());
        user.setPassword(hashedPassword);
        user.setRole(model.getRole());

        userRepository.save(user);
        return user;
    }

    public User login(LoginDTO model) {
        User existingUser = userRepository.findByEmail(model.getEmail());

        if (existingUser != null && verifyPassword(model.getPassword(), existingUser.getPassword())){
            return existingUser;
        }

        throw new RuntimeException("Authentication failed. Invalid email or password.");
    }

    public String generateJwtToken(User user, String key, String issuer, String audience) {
        Claims claims = Jwts.claims()
                .setSubject(user.getEmail());

        long expirationTimeMillis = System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000;
        Date expirationDate = new Date(expirationTimeMillis);

        claims.put("userId", user.getId());
        claims.put("role", user.getRole());
        claims.put("expire", expirationTimeMillis);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(issuer)
                .setAudience(audience)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hashedBytes) {
                stringBuilder.append(String.format("%02x", b));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password.");
        }
    }

    private boolean verifyPassword(String password, String hashedPassword) {
        return hashPassword(password).equals(hashedPassword);
    }

    public List<UserDTO> getAllUsers() {
        List<User> userList = userRepository.findAll();
        return userList.stream()
                .map(UserBuilder::toUserDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(int userId) {
        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent()) {
            return null;
        }

        return new UserDTO(user.get());
    }

    public UserDTO updateUser(int id, UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            userRepository.save(user);
            System.out.println(id);
            System.out.println("Am ajuns");
            return UserBuilder.toUserDTO(user);
        } else {
            System.out.println("Utilizatorul cu ID-ul " + id + " nu a fost găsit.");
            return null;
        }
    }


    public UserDTO deleteUser(int id) {
        User user = userRepository.findById(id).orElse(null);

        if (user != null) {
            userRepository.delete(user);
            return new UserDTO(user);
        } else {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
    }

    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user;
    }
}
