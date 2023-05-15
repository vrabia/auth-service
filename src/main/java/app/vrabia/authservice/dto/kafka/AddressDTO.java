package app.vrabia.authservice.dto.kafka;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
@Setter
@NoArgsConstructor
public class AddressDTO {
    private String country;
    private String city;
    private String zip;
}
