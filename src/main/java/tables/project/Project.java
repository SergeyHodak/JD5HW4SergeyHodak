package tables.project;

import exceptions.MustNotBeNull;
import exceptions.NumberOfCharactersExceedsTheLimit;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class Project {
    @Setter
    @Getter
    private long id;
    @Getter
    private String name;
    @Getter
    private long companyId;
    @Getter
    private long customerId;

    public void setName(String name) throws NumberOfCharactersExceedsTheLimit {
        int limit = 200;
        if(limit >= name.length()) {
            this.name = name;
        } else {
            throw new NumberOfCharactersExceedsTheLimit("name", limit, name);
        }
    }

    public void setCompanyId(long companyId) throws MustNotBeNull {
        if (companyId > 0) {
            this.companyId = companyId;
        } else {
            throw new MustNotBeNull();
        }
    }

    public void setCustomerId(long customerId) throws MustNotBeNull {
        if (customerId > 0) {
            this.customerId = customerId;
        } else {
            throw new MustNotBeNull();
        }
    }
}