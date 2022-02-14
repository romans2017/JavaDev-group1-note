package ua.goit.notes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import ua.goit.base.BaseEntity;
import ua.goit.users.User;

import javax.persistence.*;
import java.util.UUID;
import javax.validation.constraints.NotBlank;

@Data
@Builder
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
    @NotBlank
    private String name;

    @Column(name = "text", length = 10000)
    @NotBlank
    private String text;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private AccessType accessType;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}