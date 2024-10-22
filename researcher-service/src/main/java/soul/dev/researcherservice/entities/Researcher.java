package soul.dev.researcherservice.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import soul.dev.researcherservice.models.Project;

import javax.persistence.*;

@Entity @AllArgsConstructor @NoArgsConstructor
@Data @Builder
public class Researcher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String numInscription;
    private String email;
    private Long supervisorId;
    private Long projectId;

    @Transient
    private Project project ;

}
