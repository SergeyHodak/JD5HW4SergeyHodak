package tables.customer;

import exceptions.AgeOutOfRange;
import exceptions.NumberOfCharactersExceedsTheLimit;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class Customer {
    @Setter
    @Getter
    private long id;
    @Getter
    private String firstName;
    @Getter
    private String secondName;
    @Getter
    private int age;

    public void setFirstName(String firstName) throws NumberOfCharactersExceedsTheLimit {
        if (firstName == null) {
            this.firstName = null;
            return;
        }
        int limit = 50;
        if (limit >= firstName.length()) {
            this.firstName = firstName;
        } else {
            throw new NumberOfCharactersExceedsTheLimit("firstName", limit, firstName);
        }
    }

    public void setSecondName(String secondName) throws NumberOfCharactersExceedsTheLimit {
        if (secondName == null) {
            this.secondName = null;
            return;
        }
        int limit = 50;
        if (limit >= secondName.length()) {
            this.secondName = secondName;
        } else {
            throw new NumberOfCharactersExceedsTheLimit("secondName", limit, secondName);
        }
    }

    public void setAge(int age) throws AgeOutOfRange {
        int min = 0;
        int max = 150;
        if (min <= age & age <= max) {
            this.age = age;
        } else {
            throw new AgeOutOfRange("age", min, max, age);
        }
    }
}