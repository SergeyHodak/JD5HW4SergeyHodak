package tables.customer;

import exceptions.AgeOutOfRange;
import exceptions.NumberOfCharactersExceedsTheLimit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CustomerTests {
    @Test
    public void testSetFirst_name() {
        String[] sets = {
                "Test".repeat(5),
                "T".repeat(51),
                null
        };

        for (String set : sets) {
            try {
                Customer customer = new Customer();
                customer.setFirstName(set);
                Assertions.assertEquals(set, customer.getFirstName());
            } catch (NumberOfCharactersExceedsTheLimit thrown) {
                assert set != null;
                String result = "The value is too long for the field \"firstName\". " +
                        "Limit = 50. You are transmitting = " + set.length() + " symbol.";
                Assertions.assertEquals(result, thrown.getMessage());
            }
        }
    }

    @Test
    public void testSetSecond_name() {
        String[] sets = {
                "Test".repeat(1),
                "T".repeat(51),
                null
        };

        for (String set : sets) {
            try {
                Customer customer = new Customer();
                customer.setSecondName(set);
                Assertions.assertEquals(set, customer.getSecondName());
            } catch (NumberOfCharactersExceedsTheLimit thrown) {
                assert set != null;
                String result = "The value is too long for the field \"secondName\". " +
                        "Limit = 50. You are transmitting = " + set.length() + " symbol.";
                Assertions.assertEquals(result, thrown.getMessage());
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
                String result = "The \"age\" field has a limitation. 0 <= age <= 150. You sent = " + set;
                Assertions.assertEquals(result, thrown.getMessage());
            }
        }
    }
}