package customers;

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
                ", name_surname='" + null + '\'' +
                ", age=" + 0 +
                '}';
        Assertions.assertEquals(result, customers.toString());
    }

    @Test
    public void testSetName_surname() {
        List<String> sets = new ArrayList<>();
        sets.add("Test".repeat(20));
        sets.add("Test".repeat(30));

        for (String set : sets) {
            try {
                Customers customers = new Customers();
                customers.setName_surname(set);
                Assertions.assertEquals(set, customers.getName_surname());
            } catch (NumberOfCharactersExceedsTheLimit thrown) {
                Assertions.assertNotEquals("", thrown.getMessage());
            }
        }
    }

    @Test
    public void testSetAge() {
        int[] sets = {18, 50, 180};
        for (int set : sets) {
            try {
                Customers customers = new Customers();
                customers.setAge(set);
                Assertions.assertEquals(set, customers.getAge());
            } catch (AgeOutOfRange thrown) {
                Assertions.assertNotEquals("", thrown.getMessage());
            }
        }
    }
}