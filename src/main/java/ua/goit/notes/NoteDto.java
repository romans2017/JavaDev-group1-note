package ua.goit.notes;

import lombok.Data;
import lombok.NoArgsConstructor;
import ua.goit.base.Dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@NoArgsConstructor(force = true)
public class NoteDto implements Dto {

    private UUID id;

    @NotBlank
    @Size(min = 5, max = 100)
    private String name;

    @NotBlank
    @Size(min = 5, max = 10000)
    private String description;
}
