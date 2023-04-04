package app.vrabia.authservice.service;

import app.vrabia.authservice.dto.request.RegisterUserDTORequest;
import app.vrabia.authservice.dto.response.UserDTOResponse;
import app.vrabia.authservice.mappers.UserMapper;
import app.vrabia.authservice.model.Address;
import app.vrabia.authservice.repository.AddressRepository;
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
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

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
        app.vrabia.authservice.model.User newUser = new app.vrabia.authservice.model.User();
        newUser.setUsername(generateUsername(user));
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setEmail(user.getEmail());
        newUser.setName(user.getName());
        newUser.setRoles(List.of(Role.USER));
        newUser.setGenre(user.getGenre());
        newUser.setBirthdate(user.getBirthdate());
        newUser.setAbout(user.getAbout());
        newUser.setAddress(buildAddress(user));
        addressRepository.save(newUser.getAddress());
        app.vrabia.authservice.model.User createdUser = userRepository.save(newUser);
        newUser.getAddress().setUser(newUser);
        try {
            addressRepository.saveAndFlush(newUser.getAddress());
        } catch (Exception e) {
            throw new VrabiaException(ErrorCodes.UNIQUE_EMAIL);
        }

        return userMapper.userToUserDTOResponse(createdUser);
    }

    private Address buildAddress(RegisterUserDTORequest user) {
        Address address = new Address();
        address.setCountry(user.getAddress().getCountry());
        address.setCity(user.getAddress().getCity());
        address.setZip(user.getAddress().getZip());
        return address;
    }

    private String generateUsername(RegisterUserDTORequest user) {
        StringBuilder username = new StringBuilder();
        username.append("@");
        List<app.vrabia.authservice.model.User> users = userRepository.findAllByName(user.getName());
        if (users.isEmpty()) {
            username.append(user.getName());
        } else {
            username.append(user.getName()).append(users.size());
        }
        return username.toString();
    }
}

