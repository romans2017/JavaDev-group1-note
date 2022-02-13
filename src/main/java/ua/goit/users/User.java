package ua.goit.users;

import java.util.List;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import ua.goit.base.BaseEntity;
import ua.goit.roles.Role;

@Getter
@Setter
@ToString
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

  @Size(min = 5, message = "User name should be at least 5 character.")
  @Column(name = "user_name")
  private String userName;


  @Length(min = 5, message = "Password should be at least 5 character.")
  @Column(name = "password")
  private String password;

  @NotNull(message = "User has minimum one role!")
  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH},
      fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_role",
      joinColumns = { @JoinColumn(name = "user_id") },
      inverseJoinColumns = { @JoinColumn(name = "role_id") }
  )
  private List<Role> roles;
}