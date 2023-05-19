package app.vrabia.authservice.mappers;

import app.vrabia.authservice.dto.kafka.UserDTO;
import app.vrabia.authservice.dto.request.RegisterUserDTORequest;
import app.vrabia.authservice.dto.response.UserDTOResponse;
import app.vrabia.authservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface UserMapper {

    UserDTOResponse userToUserDTOResponse(User user);

    User userDTOResponseToUser(UserDTOResponse userDTOResponse);

    User registerDTORequestToUser(RegisterUserDTORequest registerUserDTORequest);

    UserDTO registerUserDTORequestToUserDTO(RegisterUserDTORequest registerUserDTORequest);

    List<UserDTOResponse> usersToUsersDTOResponse(List<User> users);

}
