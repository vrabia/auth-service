package app.vrabia.authservice.service;

import app.vrabia.authservice.dto.enums.DeviceAuthenticationStatus;
import app.vrabia.authservice.dto.request.DeviceAuthenticationRequestDTO;
import app.vrabia.authservice.dto.request.DeviceAuthenticationStep2RequestDTO;
import app.vrabia.authservice.dto.response.DeviceAuthenticationResponseDTO;
import app.vrabia.authservice.dto.response.DeviceAuthenticationStep2ResponseDTO;
import app.vrabia.authservice.dto.response.UserDTOResponse;
import app.vrabia.authservice.mappers.UserMapper;
import app.vrabia.authservice.model.DeviceAuthentication;
import app.vrabia.authservice.model.User;
import app.vrabia.authservice.repository.DeviceAuthenticationRepository;
import app.vrabia.authservice.repository.UserRepository;
import app.vrabia.vrcommon.exception.ErrorCodes;
import app.vrabia.vrcommon.exception.VrabiaException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DeviceAuthenticationService {
    @Value("${website.url}")
    private String websiteUrl;

    @Value("${website.device-url}")
    private String deviceUrl;
    private final static long EXPIRATION_DAYS = 5;
    private final DeviceAuthenticationRepository deviceAuthenticationRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    public DeviceAuthenticationResponseDTO registerDevice(DeviceAuthenticationRequestDTO deviceAuthenticationRequestDTO) {
        String deviceCode = UUID.randomUUID().toString();
        String userCode = UUID.randomUUID().toString();
        DeviceAuthentication deviceAuthentication = new DeviceAuthentication();
        deviceAuthentication.setDeviceCode(deviceCode);
        deviceAuthentication.setUserCode(userCode);
        deviceAuthentication.setClientId(deviceAuthenticationRequestDTO.getClientId());
        deviceAuthentication.setExpirationDate(LocalDate.now().plusDays(EXPIRATION_DAYS));
        deviceAuthenticationRepository.save(deviceAuthentication);
        return new DeviceAuthenticationResponseDTO(deviceCode, userCode, String.format("%s%s", websiteUrl, deviceUrl));
    }

    public DeviceAuthenticationStep2ResponseDTO registerDeviceStep2(DeviceAuthenticationStep2RequestDTO request) {
        Optional<DeviceAuthentication> deviceAuthenticationOptional =
                deviceAuthenticationRepository.findByClientIdAndDeviceCode(request.getClientId(), request.getDeviceCode());
        if (deviceAuthenticationOptional.isEmpty()) {
            return new DeviceAuthenticationStep2ResponseDTO(DeviceAuthenticationStatus.DENIED);
        }
        DeviceAuthentication deviceAuthentication = deviceAuthenticationOptional.get();
        UserDTOResponse user = userMapper.userToUserDTOResponse(deviceAuthentication.getUser());
        log.info("User: {}", user);
        if (deviceAuthentication.getExpirationDate().isBefore(LocalDate.now())) {
            return new DeviceAuthenticationStep2ResponseDTO(DeviceAuthenticationStatus.EXPIRED);
        }

        if (user == null) {
            return new DeviceAuthenticationStep2ResponseDTO(DeviceAuthenticationStatus.PENDING);
        }
        deviceAuthenticationRepository.delete(deviceAuthentication);
        return new DeviceAuthenticationStep2ResponseDTO(DeviceAuthenticationStatus.AUTHORIZED, user);
    }

    public void registerDeviceFromUserCode(String userCode, String username) {
        Optional<DeviceAuthentication> deviceAuthenticationOptional =
                deviceAuthenticationRepository.findByUserCode(userCode);
        if (deviceAuthenticationOptional.isEmpty()) {
            throw new VrabiaException(ErrorCodes.INVALID_PARAMETERS);
        }
        DeviceAuthentication deviceAuthentication = deviceAuthenticationOptional.get();
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new VrabiaException(ErrorCodes.INVALID_PARAMETERS);
        }
        deviceAuthentication.setUser(userOptional.get());
        deviceAuthenticationRepository.save(deviceAuthentication);
    }
}
