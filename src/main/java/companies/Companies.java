package companies;

import exceptions.NumberOfCharactersExceedsTheLimit;

public class Companies {
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
}