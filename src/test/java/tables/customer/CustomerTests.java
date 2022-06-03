package tables.customer;

import exceptions.AgeOutOfRange;
import exceptions.NumberOfCharactersExceedsTheLimit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class CustomerTests {
    @Test
    public void testSetFirst_name() {
        List<String> sets = new ArrayList<>();
        sets.add("Test".repeat(5));
        sets.add("T".repeat(51));
        sets.add(null);

        for (String set : sets) {
            try {
                Customer customer = new Customer();
                customer.setFirstName(set);
                Assertions.assertEquals(set, customer.getFirstName());
            } catch (NumberOfCharactersExceedsTheLimit thrown) {
                Assertions.assertNotEquals("", thrown.getMessage());
            }
        }
    }

    @Test
    public void testSetSecond_name() {
        List<String> sets = new ArrayList<>();
        sets.add("Test".repeat(1));
        sets.add("T".repeat(51));
        sets.add(null);

        for (String set : sets) {
            try {
                Customer customer = new Customer();
                customer.setSecondName(set);
                Assertions.assertEquals(set, customer.getSecondName());
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
                Customer customer = new Customer();
                customer.setAge(set);
                Assertions.assertEquals(set, customer.getAge());
            } catch (AgeOutOfRange thrown) {
                System.out.println(1);
                Assertions.assertNotEquals("", thrown.getMessage());
            }
        }
    }
}