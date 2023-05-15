package app.vrabia.authservice.dto.request;

import app.vrabia.authservice.dto.enums.MusicGenre;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private LocalDate birthdate;
    private String about;
    @NotNull
    private MusicGenre genre;
    @NotNull
    private String password;
    @NotNull
    private AddressDTORequest address;
}
