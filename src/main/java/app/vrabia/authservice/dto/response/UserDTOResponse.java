package app.vrabia.authservice.dto.response;

import app.vrabia.vrcommon.models.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class UserDTOResponse {
    private String id;
    private String username;
    private String email;
    private List<Role> roles;
}
