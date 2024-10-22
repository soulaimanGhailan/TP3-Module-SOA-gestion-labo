package soul.dev.projectservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Teacher {
    private long id;
    private String firstname;
    private String lastname;
    private String cne;
    private String email;
    private String researchField;
}