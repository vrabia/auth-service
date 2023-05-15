package app.vrabia.authservice.service;

import app.vrabia.authservice.dto.kafka.AddressDTO;
import app.vrabia.authservice.dto.kafka.UserDTO;
import app.vrabia.authservice.dto.request.RegisterUserDTORequest;
import app.vrabia.authservice.dto.response.UserDTOResponse;
import app.vrabia.authservice.kafka.KafkaProducer;
import app.vrabia.authservice.mappers.UserMapper;
import app.vrabia.authservice.repository.UserRepository;
import app.vrabia.vrcommon.exception.ErrorCodes;
import app.vrabia.vrcommon.exception.VrabiaException;
import app.vrabia.vrcommon.models.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final KafkaProducer kafkaProducer;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<app.vrabia.authservice.model.User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            userOptional = userRepository.findByEmail(username);
        }
        if (userOptional.isEmpty()) {
            throw new VrabiaException(ErrorCodes.INVALID_CREDENTIALS);
        }

        app.vrabia.authservice.model.User user = userOptional.get();

        return new User(user.getUsername(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.toString()))
                        .toList()
        );
    }

    public UserDTOResponse registerUser(RegisterUserDTORequest user) {
        app.vrabia.authservice.model.User newUser = userMapper.registerDTORequestToUser(user);
        newUser.setUsername(generateUsername(user));
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRoles(List.of(Role.USER));
        app.vrabia.authservice.model.User createdUser = userRepository.save(newUser);

        // send message to kafka
        UserDTO newUserToSave = userMapper.registerUserDTORequestToUserDTO(user);
        newUserToSave.setAddress(buildAddress(user));
        newUserToSave.setId(createdUser.getId());
        kafkaProducer.send(newUserToSave);

        return userMapper.userToUserDTOResponse(createdUser);
    }

    private AddressDTO buildAddress(RegisterUserDTORequest user) {
        AddressDTO address = new AddressDTO();
        address.setCountry(user.getAddress().getCountry());
        address.setCity(user.getAddress().getCity());
        address.setZip(user.getAddress().getZip());
        return address;
    }

    private String generateUsername(RegisterUserDTORequest user) {
        String prefix = "@" + user.getName();
        StringBuilder username = new StringBuilder();
        username.append("@");
        List<app.vrabia.authservice.model.User> users = userRepository.findByUsernameStartingWith(prefix);
        if (users.isEmpty()) {
            username.append(user.getName());
        } else {
            List<app.vrabia.authservice.model.User> actualMatches = users.stream().filter(u -> {
                String suffix = u.getUsername().substring(prefix.length());
                return suffix.matches("[0-9]+") || suffix.isBlank();
            }).toList();

            if (!actualMatches.isEmpty()) {
                username.append(user.getName()).append(actualMatches.size());
            }
        }
        return username.toString();
    }

    public UserDTOResponse getUserByUsername(String username) {
        Optional<app.vrabia.authservice.model.User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new VrabiaException(ErrorCodes.BAD_REQUEST);
        }
        return userMapper.userToUserDTOResponse(userOptional.get());
    }
}

