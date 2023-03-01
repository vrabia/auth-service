package app.vrabia.authservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "VRUSERS")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "UserIdGenerator")
    @GenericGenerator(
            name = "UserIdGenerator",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "BIRTHDATE")
    private LocalDate birthdate;

    @Column(name = "ABOUT")
    private String about;

    @Column(name = "GENRE")
    @Enumerated(EnumType.STRING)
    private MusicGenre genre;

    @Column(name = "CREATED")
    private Date createdAt;

    @Column(name = "UPDATED")
    private Date updatedAt;

    @Column(name = "VERSION")
    private int version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADDRESS_ID")
    private Address address;
}
