package tables.company;

import exceptions.NumberOfCharactersExceedsTheLimit;

public class Company {
    private long id;
    private String name;
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws NumberOfCharactersExceedsTheLimit {
        if (description == null) {
            this.description = null;
            return;
        }
        int limit = 200;
        if(limit >= description.length()) {
            this.description = description;
        } else {
            throw new NumberOfCharactersExceedsTheLimit("description", limit, description);
        }
    }

    @Override
    public String toString() {
        return "Companies{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company)) return false;

        Company company = (Company) o;

        if (getId() != company.getId()) return false;
        if (!getName().equals(company.getName())) return false;
        return getDescription().equals(company.getDescription());
    }
}