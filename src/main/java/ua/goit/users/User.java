package ua.goit.users;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.springframework.transaction.annotation.Transactional;
import ua.goit.base.BaseEntity;
import ua.goit.notes.Note;
import ua.goit.roles.Role;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "users")
public class User implements BaseEntity<UUID> {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private List<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Note> notes;

}