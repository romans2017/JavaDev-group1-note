package ua.goit.users;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import ua.goit.base.BaseEntity;
import ua.goit.notes.Note;
import ua.goit.roles.Role;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.*;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "users")
public class User implements BaseEntity<UUID> {

  private static final long serialVersionUID = 5686706842794258419L;

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Type(type = "uuid-char")
  @Column(name = "id", columnDefinition = "VARCHAR(36)")
  private UUID id;

  @Length(min = 5, message = "First name should be at least 5 character.")
  @Column(name = "name")
  private String name;

  @Length(min = 5, message = "Password should be at least 5 character.")
  @Column(name = "password")
  private String password;

  @Size(min = 1, message = "User has minimum one role!")
  @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH},
          fetch = FetchType.EAGER)
  @JoinTable(
          name = "user_role",
          joinColumns = {@JoinColumn(name = "user_id")},
          inverseJoinColumns = {@JoinColumn(name = "role_id")}
  )
  private List<Role> roles = new ArrayList<>();

  @OneToMany(mappedBy = "user")
  private List<Note> notes;
}