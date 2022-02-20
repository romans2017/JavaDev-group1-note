package ua.goit.notes;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import ua.goit.base.BaseEntity;
import ua.goit.users.User;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notes")
public class Note implements BaseEntity<UUID> {

    private static final long serialVersionUID = 6174182882601741785L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-char")
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    private UUID id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "text", length = 10000)
    private String text;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private AccessType accessType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}