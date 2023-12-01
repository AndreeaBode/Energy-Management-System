package sd.dtos;

public class LoginResponseDTO {
        private String token;

        public LoginResponseDTO(String token1) {
                this.token = token1;
        }

        public String getToken() {
                return token;
        }
}
