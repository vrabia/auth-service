package app.vrabia.authservice.controller;

import app.vrabia.authservice.dto.UserDTO;
import app.vrabia.authservice.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserManagementResource {

    private final UserManagementService userManagementService;

    @PostMapping
    public void createUser(@RequestBody UserDTO userDTO) {
        log.info("Create user called");
        userManagementService.createUser(userDTO);
    }
}

