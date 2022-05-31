package tables.developers;

import exceptions.AgeOutOfRange;
import exceptions.NumberOfCharactersExceedsTheLimit;

public class Developers {
    private long id;
    private String firstName;
    private String secondName;
    private int age;
    private Gender gender;

    public enum Gender {
        male,
        female
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) throws NumberOfCharactersExceedsTheLimit {
        int limit = 50;
        if(limit >= firstName.length()) {
            this.firstName = firstName;
        } else {
            throw new NumberOfCharactersExceedsTheLimit("name_surname", limit, firstName);
        }
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) throws NumberOfCharactersExceedsTheLimit {
        int limit = 50;
        if(limit >= secondName.length()) {
            this.secondName = secondName;
        } else {
            throw new NumberOfCharactersExceedsTheLimit("name_surname", limit, secondName);
        }
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) throws AgeOutOfRange {
        int min = 0;
        int max = 150;
        if (min < age & age < max) {
            this.age = age;
        } else {
            throw new AgeOutOfRange("age", min, max, age);
        }
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Developers{" +
                "id=" + getId() +
                ", firstName='" + getFirstName() + '\'' +
                ", secondName='" + getSecondName() + '\'' +
                ", age=" + getAge() +
                ", gender=" + (getGender() == null ? null : getGender().name()) +
                '}';
    }
}