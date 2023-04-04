package app.vrabia.authservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeviceAuthenticationResponseDTO {
    private String deviceCode;
    private String userCode;
    private String verificationUri;
}
