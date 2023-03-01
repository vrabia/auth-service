package app.vrabia.authservice.repository;

import app.vrabia.authservice.model.DeviceAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceAuthenticationRepository extends JpaRepository<DeviceAuthentication, String> {
}
