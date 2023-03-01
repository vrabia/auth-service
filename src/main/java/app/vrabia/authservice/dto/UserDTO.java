package app.vrabia.authservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserDTO {
    private String name;
    private String username;
    private String email;
    private String password;
    private LocalDate birthdate;
    private String about;
}
