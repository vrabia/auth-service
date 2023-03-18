package app.vrabia.authservice.dto.response;

import app.vrabia.authservice.model.MusicGenre;
import app.vrabia.vrcommon.models.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class UserDTOResponse {
    private String name;
    private String username;
    private String email;
    private LocalDate birthdate;
    private String about;
    private MusicGenre genre;
    private List<Role> roles;
}
