package ua.goit.roles;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import ua.goit.base.BaseEntity;

import javax.persistence.*;
import java.util.UUID;
import javax.validation.constraints.Size;
import java.util.List;

import ua.goit.users.User;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role implements BaseEntity<UUID> {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Type(type = "uuid-char")
  private UUID id;

  @Size(min = 3, message = "Role should be at least 3 character.")
  @Column(name = "role_name")
  private String name;

  @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH},
      fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_role",
      joinColumns = { @JoinColumn(name = "role_id") },
      inverseJoinColumns = { @JoinColumn(name = "user_id") }
  )
  private transient List<User> users;
}