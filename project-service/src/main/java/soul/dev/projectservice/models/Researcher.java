package soul.dev.projectservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class Researcher {
    private Long id;
    private String firstname;
    private String lastname;
    private String numInscription;
    private String email;
    private Long supervisorId;
    private Long projectId;
}
