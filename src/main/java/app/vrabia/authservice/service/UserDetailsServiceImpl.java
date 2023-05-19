package app.vrabia.authservice.service;

import app.vrabia.authservice.dto.response.UserDTOResponse;
import app.vrabia.authservice.mappers.UserMapper;
import app.vrabia.authservice.model.User;
import app.vrabia.authservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public List<UserDTOResponse> getUsersDetails(List<String> userIds) {
        List<User> users = userRepository.findAllById(userIds);
        return userMapper.usersToUsersDTOResponse(users);
    }
}
