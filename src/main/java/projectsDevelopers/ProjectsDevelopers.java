package projectsDevelopers;

import exceptions.MustNotBeNull;

public class ProjectsDevelopers {
    private long project_id;
    private long developer_id;

    public long getProject_id() {
        return project_id;
    }

    public void setProject_id(long project_id) throws MustNotBeNull {
        if (project_id > 0) {
            this.project_id = project_id;
        } else {
            throw new MustNotBeNull();
        }
    }

    public long getDeveloper_id() {
        return developer_id;
    }

    public void setDeveloper_id(long developer_id) throws MustNotBeNull {
        if (developer_id > 0) {
            this.developer_id = developer_id;
        } else {
            throw new MustNotBeNull();
        }
    }

    @Override
    public String toString() {
        return "ProjectsDevelopers{" +
                "project_id=" + getProject_id() +
                ", developer_id=" + getDeveloper_id() +
                '}';
    }
}