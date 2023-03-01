package app.vrabia.authservice.service;

import app.vrabia.authservice.dto.UserDTO;
import app.vrabia.authservice.model.MusicGenre;
import app.vrabia.authservice.model.User;
import app.vrabia.authservice.repository.AddressRepository;
import app.vrabia.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserManagementService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    public void createUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setUsername("test");
        user.setEmail("email");
        user.setPassword("password");
        user.setGenre(MusicGenre.BLUES);
        user.setAddress(addressRepository.getReferenceById("1"));
        userRepository.save(user);
    }
}
