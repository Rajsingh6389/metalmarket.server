package MaterialMart.Dto;

import MaterialMart.Model.User;
import lombok.*;

@Data
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AuthResponse {
    private String token;
    private User user;



}