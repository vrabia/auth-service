package app.vrabia.authservice.mappers;

import app.vrabia.authservice.dto.request.AddressDTORequest;
import app.vrabia.authservice.dto.request.RegisterUserDTORequest;
import app.vrabia.authservice.dto.response.UserDTOResponse;
import app.vrabia.authservice.model.Address;
import app.vrabia.authservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.control.MappingControl;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface UserMapper {

    UserDTOResponse userToUserDTOResponse(User user);

    User userDTOResponseToUser(UserDTOResponse userDTOResponse);

    Address addressDTORequestToAddress(AddressDTORequest addressDTORequest);

    User registerDTORequestToUser(RegisterUserDTORequest registerUserDTORequest);
}
