package tables.customers;

import exceptions.AgeOutOfRange;
import exceptions.NumberOfCharactersExceedsTheLimit;

public class Customers {
    private long id;
    private String name_surname;
    private int age;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName_surname() {
        return name_surname;
    }

    public void setName_surname(String name_surname) throws NumberOfCharactersExceedsTheLimit {
        int limit = 100;
        if(limit >= name_surname.length()) {
            this.name_surname = name_surname;
        } else {
            throw new NumberOfCharactersExceedsTheLimit("name_surname", limit, name_surname);
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

    @Override
    public String toString() {
        return "Customers{" +
                "id=" + getId() +
                ", name_surname='" + getName_surname() + '\'' +
                ", age=" + getAge() +
                '}';
    }
}