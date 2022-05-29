package projects;

import exceptions.MustNotBeNull;
import exceptions.NumberOfCharactersExceedsTheLimit;

public class Projects {
    private long id;
    private String name;
    private long company_id;
    private long customer_id;

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

    public long getCompany_id() {
        return company_id;
    }

    public void setCompany_id(long company_id) throws MustNotBeNull {
        if (company_id > 0) {
            this.company_id = company_id;
        } else {
            throw new MustNotBeNull();
        }
    }

    public long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(long customer_id) throws MustNotBeNull {
        if (company_id > 0) {
            this.customer_id = customer_id;
        } else {
            throw new MustNotBeNull();
        }
    }

    @Override
    public String toString() {
        return "Projects{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", company_id=" + getCompany_id() +
                ", customer_id=" + getCustomer_id() +
                '}';
    }
}