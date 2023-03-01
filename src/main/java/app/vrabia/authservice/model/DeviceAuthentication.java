package app.vrabia.authservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Entity
@Table(name = "DEVICE_AUTHENTICATION")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceAuthentication {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "UserIdGenerator")
    @GenericGenerator(
            name = "UserIdGenerator",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "CLIENT_ID")
    private String clientId;

    @Column(name = "DEVICE_CODE")
    private String deviceCode;

    @Column(name = "USER_CODE")
    private String userCode;

    @Column(name = "EXIRATION_DATE")
    private Date expirationDate;
}
