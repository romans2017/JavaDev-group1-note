package ua.goit.roles;

import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.transaction.annotation.Transactional;
import ua.goit.base.BaseEntity;
import ua.goit.users.User;

@Data
@Entity
@Table(name = "roles")
@Transactional
public class Role implements BaseEntity<UUID> {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(
      name = "UUID",
      strategy = "org.hibernate.id.UUIDGenerator"
  )
  @Column(name = "id", updatable = false, nullable = false)
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

  @Override
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<User> getUsers() {
    return users;
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }
}