package app.vrabia.authservice.controller;

import app.vrabia.authservice.dto.request.CredentialsDTORequest;
import app.vrabia.authservice.dto.request.RegisterUserDTORequest;
import app.vrabia.authservice.dto.response.UserDTOResponse;
import app.vrabia.authservice.model.AuthenticationType;
import app.vrabia.authservice.service.AuthenticationService;
import app.vrabia.vrcommon.exception.ErrorCodes;
import app.vrabia.vrcommon.exception.VrabiaException;
import app.vrabia.vrcommon.service.JWTService;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final AuthenticationManager authManager;
    private final JWTService jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<Void> loginHandler(@RequestBody CredentialsDTORequest body) {
        log.info("Login request received");

        AuthenticationType authenticationType = checkAuthenticationType(body);
        AuthenticationType usernameType = AuthenticationType.USERNAME;

        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(
                    usernameType.equals(authenticationType) ? body.getUsername() : body.getEmail(),
                    body.getPassword())
            );
        } catch (Exception e) {
            throw new VrabiaException(ErrorCodes.INVALID_CREDENTIALS);
        }

        final UserDetails userDetails = authenticationService.loadUserByUsername(
                usernameType.equals(authenticationType) ? body.getUsername() : body.getEmail()
        );

        if (userDetails == null) {
            throw new VrabiaException(ErrorCodes.INVALID_CREDENTIALS);
        }

        List<String> userRoles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        String token = jwtUtil.createAccessToken(userDetails.getUsername(), userRoles);
        log.info("Token created: {}", token);

        Cookie authCookie = new Cookie("AccessToken", token);

        return ResponseEntity.ok()
                .headers(createHeadersWithCookie(authCookie)).build();

    }

    @PostMapping("/register")
    public ResponseEntity<UserDTOResponse> registerHandler(@RequestBody RegisterUserDTORequest body) {
        log.info("Register request received");

        UserDTOResponse createdUser = authenticationService.registerUser(body);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    private AuthenticationType checkAuthenticationType(CredentialsDTORequest body) {
        if (body.getUsername() != null && !"".equals(body.getUsername())) {
            return AuthenticationType.USERNAME;
        } else if (body.getEmail() != null && !"".equals(body.getEmail())) {
            return AuthenticationType.EMAIL;
        } else {
            throw new VrabiaException(ErrorCodes.INVALID_CREDENTIALS);
        }
    }

    private HttpHeaders createHeadersWithCookie(Cookie cookie) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, String.format("%s=%s", cookie.getName(), cookie.getValue()));
        return headers;
    }
}
