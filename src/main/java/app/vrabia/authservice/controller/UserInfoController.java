package app.vrabia.authservice.controller;

import app.vrabia.authservice.dto.request.UserDetailsListRequestDTO;
import app.vrabia.authservice.dto.response.UserDTOResponse;
import app.vrabia.authservice.service.UserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserInfoController {
    private final UserDetailsService userDetailsService;

    @PostMapping
    public ResponseEntity<List<UserDTOResponse>> getUserDetails(@RequestBody UserDetailsListRequestDTO userIds) {
        log.info("Get user details");
        List<UserDTOResponse> userDetailsResponseDTO = userDetailsService.getUsersDetails(userIds.getIds());
        return ResponseEntity.ok(userDetailsResponseDTO);
    }
}
