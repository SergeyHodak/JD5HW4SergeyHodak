package tables.customers;

import exceptions.AgeOutOfRange;
import exceptions.NumberOfCharactersExceedsTheLimit;

public class Customers {
    private long id;
    private String firstName;
    private String secondName;
    private int age;

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
        if (firstName == null) {
            this.firstName = null;
            return;
        }
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
        if (secondName == null) {
            this.secondName = null;
            return;
        }
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
        if (min <= age & age <= max) {
            this.age = age;
        } else {
            throw new AgeOutOfRange("age", min, max, age);
        }
    }

    @Override
    public String toString() {
        return "Customers{" +
                "id=" + getId() +
                ", firstName='" + getFirstName() + '\'' +
                ", secondName='" + getSecondName() + '\'' +
                ", age=" + getAge() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customers)) return false;

        Customers customers = (Customers) o;

        if (getId() != customers.getId()) return false;
        if (getAge() != customers.getAge()) return false;
        if (!getFirstName().equals(customers.getFirstName())) return false;
        return getSecondName().equals(customers.getSecondName());
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getFirstName().hashCode();
        result = 31 * result + getSecondName().hashCode();
        result = 31 * result + getAge();
        return result;
    }
}