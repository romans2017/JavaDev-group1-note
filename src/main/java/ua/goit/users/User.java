package ua.goit.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import ua.goit.base.BaseEntity;

import javax.persistence.*;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;
import java.util.List;

import ua.goit.roles.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements BaseEntity<UUID> {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Type(type = "uuid-char")
  private UUID id;

  @Size(min = 5, message = "User name should be at least 5 character.")
  @Column(name = "user_name")
  private String name;

  @Length(min = 5, message = "Password should be at least 5 character.")
  @Column(name = "password")
  private String password;

  @NotNull(message = "User has minimum one role!")
  @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH},
      fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_role",
      joinColumns = { @JoinColumn(name = "user_id") },
      inverseJoinColumns = { @JoinColumn(name = "role_id") }
  )
  private List<Role> roles;
}