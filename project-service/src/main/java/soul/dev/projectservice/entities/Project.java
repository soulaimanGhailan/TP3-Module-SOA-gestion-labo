package soul.dev.projectservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import soul.dev.projectservice.enums.ProjectStatus;

import javax.persistence.*;

@Entity
@Data @NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;
    private long supervisorId;
    private long researcherId;
}