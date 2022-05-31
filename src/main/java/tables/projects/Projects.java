package tables.projects;

import exceptions.MustNotBeNull;
import exceptions.NumberOfCharactersExceedsTheLimit;

public class Projects {
    private long id;
    private String name;
    private long companyId;
    private long customerId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws NumberOfCharactersExceedsTheLimit {
        if (name == null) {
            this.name = null;
            return;
        }
        int limit = 200;
        if(limit >= name.length()) {
            this.name = name;
        } else {
            throw new NumberOfCharactersExceedsTheLimit("name", limit, name);
        }
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) throws MustNotBeNull {
        if (companyId > 0) {
            this.companyId = companyId;
        } else {
            throw new MustNotBeNull();
        }
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) throws MustNotBeNull {
        if (companyId > 0) {
            this.customerId = customerId;
        } else {
            throw new MustNotBeNull();
        }
    }

    @Override
    public String toString() {
        return "Projects{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", companyId=" + getCompanyId() +
                ", customerId=" + getCustomerId() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Projects)) return false;

        Projects projects = (Projects) o;

        if (getId() != projects.getId()) return false;
        if (getCompanyId() != projects.getCompanyId()) return false;
        if (getCustomerId() != projects.getCustomerId()) return false;
        return getName().equals(projects.getName());
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getName().hashCode();
        result = 31 * result + (int) (getCompanyId() ^ (getCompanyId() >>> 32));
        result = 31 * result + (int) (getCustomerId() ^ (getCustomerId() >>> 32));
        return result;
    }
}