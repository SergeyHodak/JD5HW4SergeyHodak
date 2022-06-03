package tables.company;

import exceptions.NumberOfCharactersExceedsTheLimit;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class Company {
    @Setter
    @Getter
    private long id;
    @Getter
    private String name;
    @Getter
    private String description;

    public void setName(String name) throws NumberOfCharactersExceedsTheLimit {
        if (name == null) {
            this.name = null;
            return;
        }
        int limit = 200;
        if (limit >= name.length()) {
            this.name = name;
        } else {
            throw new NumberOfCharactersExceedsTheLimit("name", limit, name);
        }
    }

    public void setDescription(String description) throws NumberOfCharactersExceedsTheLimit {
        if (description == null) {
            this.description = null;
            return;
        }
        int limit = 200;
        if (limit >= description.length()) {
            this.description = description;
        } else {
            throw new NumberOfCharactersExceedsTheLimit("description", limit, description);
        }
    }
}