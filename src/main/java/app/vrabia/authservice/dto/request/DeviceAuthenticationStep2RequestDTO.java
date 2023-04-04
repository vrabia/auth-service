package app.vrabia.authservice.dto.request;

import app.vrabia.authservice.utils.validators.EqualsConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceAuthenticationStep2RequestDTO {
    private String deviceCode;
    private String clientId;
    @EqualsConstraint(compareWith = "urn:ietf:params:oauth:grant-type:device_code")
    private String grantType;
}
