package ua.goit.roles;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import ua.goit.base.BaseEntity;
import ua.goit.users.User;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "roles")
public class Role implements BaseEntity<UUID> {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "role_name")
    private String name;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private transient List<User> users;

}