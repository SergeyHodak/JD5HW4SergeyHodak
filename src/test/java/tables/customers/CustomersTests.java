package tables.customers;

import exceptions.AgeOutOfRange;
import exceptions.NumberOfCharactersExceedsTheLimit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class CustomersTests {
    @Test
    public void testToStringAndGetters() {
        Customers customers = new Customers();
        String result = "Customers{" +
                "id=" + 0 +
                ", firstName='" + null + '\'' +
                ", secondName='" + null + '\'' +
                ", age=" + 0 +
                '}';
        Assertions.assertEquals(result, customers.toString());
    }

    @Test
    public void testSetFirst_name() {
        List<String> sets = new ArrayList<>();
        sets.add("Test".repeat(5));
        sets.add("Test".repeat(30));

        for (String set : sets) {
            try {
                Customers customers = new Customers();
                customers.setFirstName(set);
                Assertions.assertEquals(set, customers.getFirstName());
            } catch (NumberOfCharactersExceedsTheLimit thrown) {
                Assertions.assertNotEquals("", thrown.getMessage());
            }
        }
    }

    @Test
    public void testSetSecond_name() {
        List<String> sets = new ArrayList<>();
        sets.add("Test".repeat(7));
        sets.add("Test".repeat(31));

        for (String set : sets) {
            try {
                Customers customers = new Customers();
                customers.setSecondName(set);
                Assertions.assertEquals(set, customers.getSecondName());
            } catch (NumberOfCharactersExceedsTheLimit thrown) {
                Assertions.assertNotEquals("", thrown.getMessage());
            }
        }
    }

    @Test
    public void testSetAge() {
        int[] sets = {18, 50, 180, 0};
        for (int set : sets) {
            try {
                Customers customers = new Customers();
                customers.setAge(set);
                Assertions.assertEquals(set, customers.getAge());
            } catch (AgeOutOfRange thrown) {
                System.out.println(1);
                Assertions.assertNotEquals("", thrown.getMessage());
            }
        }
    }
}