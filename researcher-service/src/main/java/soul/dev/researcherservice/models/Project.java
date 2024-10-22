package soul.dev.researcherservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    private long id;
    private String title;
    private String description;
    private ProjectStatus status;
    private long supervisorId;
    private long researcherId;
}