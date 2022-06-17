package tables.developer;

import lombok.Data;

@Data
public class Developer {
    private long id;
    private String firstName;
    private String secondName;
    private int age;
    private Gender gender;
    private double salary;

    public enum Gender {
        MALE,
        FEMALE
    }
}