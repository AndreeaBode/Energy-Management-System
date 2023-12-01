package sd.dtos.builders;

import sd.dtos.UserDTO;
import sd.entities.User;

public class UserBuilder {

    public static UserDTO toUserDTO(User user){
        return new UserDTO(user.getId(),user.getName(), user.getEmail(), user.getRole());
    }
}
