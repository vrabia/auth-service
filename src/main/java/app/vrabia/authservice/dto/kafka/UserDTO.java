package app.vrabia.authservice.dto.kafka;

import app.vrabia.authservice.dto.enums.MusicGenre;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
public class UserDTO {
    private String id;
    private String name;
    private AddressDTO address;
    private LocalDate birthdate;
    private String about;
    private MusicGenre genre;
}
