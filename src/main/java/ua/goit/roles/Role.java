package ua.goit.roles;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import ua.goit.base.BaseEntity;
import ua.goit.users.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "roles")
public class Role implements BaseEntity<UUID> {

  private static final long serialVersionUID = 291330219299121609L;

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Type(type = "uuid-char")
  @Column(name = "id", columnDefinition = "VARCHAR(36)", updatable = false, nullable = false)
  private UUID id;

  @Column(name = "role_name")
  private String name;

  @ManyToMany(mappedBy = "roles")
  private List<User> users = new ArrayList<>();
}