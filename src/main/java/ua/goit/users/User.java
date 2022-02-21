package ua.goit.users;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import ua.goit.base.BaseEntity;
import ua.goit.notes.Note;
import ua.goit.roles.Role;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements BaseEntity<UUID> {

    private static final long serialVersionUID = 8810565761339301095L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-char")
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE})
    private List<Note> notes;
}