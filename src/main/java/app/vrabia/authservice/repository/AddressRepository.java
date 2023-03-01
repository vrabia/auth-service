package app.vrabia.authservice.repository;

import app.vrabia.authservice.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, String> {
}
