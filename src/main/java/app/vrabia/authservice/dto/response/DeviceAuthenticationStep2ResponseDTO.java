package app.vrabia.authservice.dto.response;

import app.vrabia.authservice.dto.enums.DeviceAuthenticationStatus;
import app.vrabia.authservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class DeviceAuthenticationStep2ResponseDTO {
    private final DeviceAuthenticationStatus status;
    private UserDTOResponse user;
}
