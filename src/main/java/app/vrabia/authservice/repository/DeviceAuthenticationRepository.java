package app.vrabia.authservice.repository;

import app.vrabia.authservice.model.DeviceAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceAuthenticationRepository extends JpaRepository<DeviceAuthentication, String> {
    Optional<DeviceAuthentication> findByClientIdAndDeviceCode(String clientId, String deviceCode);
    Optional<DeviceAuthentication> findByUserCode(String userCode);
}
