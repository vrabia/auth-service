package app.vrabia.authservice.dto.request;

import app.vrabia.authservice.model.MusicGenre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RegisterUserDTORequest {
    private String name;
    private String email;
    private LocalDate birthdate;
    private String about;
    private MusicGenre genre;
    private String password;
    private AddressDTORequest address;
}
