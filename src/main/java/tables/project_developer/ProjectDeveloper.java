package tables.project_developer;

import exceptions.MustNotBeNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class ProjectDeveloper {
    @Getter
    private long projectId;
    @Getter
    private long developerId;

    public void setProjectId(long projectId) throws MustNotBeNull {
        if (projectId > 0) {
            this.projectId = projectId;
        } else {
            throw new MustNotBeNull();
        }
    }

    public void setDeveloperId(long developerId) throws MustNotBeNull {
        if (developerId > 0) {
            this.developerId = developerId;
        } else {
            throw new MustNotBeNull();
        }
    }
}