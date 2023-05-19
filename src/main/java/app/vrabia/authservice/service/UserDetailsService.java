package app.vrabia.authservice.service;

import app.vrabia.authservice.dto.response.UserDTOResponse;

import java.util.List;

public interface UserDetailsService {
    List<UserDTOResponse> getUsersDetails(List<String> userIds);
}
