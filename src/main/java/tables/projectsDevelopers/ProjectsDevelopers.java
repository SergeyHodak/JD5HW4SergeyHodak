package tables.projectsDevelopers;

import exceptions.MustNotBeNull;

public class ProjectsDevelopers {
    private long id;
    private long projectId;
    private long developerId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) throws MustNotBeNull {
        if (projectId > 0) {
            this.projectId = projectId;
        } else {
            throw new MustNotBeNull();
        }
    }

    public long getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(long developerId) throws MustNotBeNull {
        if (developerId > 0) {
            this.developerId = developerId;
        } else {
            throw new MustNotBeNull();
        }
    }

    @Override
    public String toString() {
        return "ProjectsDevelopers{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", developerId=" + developerId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectsDevelopers)) return false;

        ProjectsDevelopers that = (ProjectsDevelopers) o;

        if (getId() != that.getId()) return false;
        if (getProjectId() != that.getProjectId()) return false;
        return getDeveloperId() == that.getDeveloperId();
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (int) (getProjectId() ^ (getProjectId() >>> 32));
        result = 31 * result + (int) (getDeveloperId() ^ (getDeveloperId() >>> 32));
        return result;
    }
}