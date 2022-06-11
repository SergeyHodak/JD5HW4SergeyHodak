package tables.developer;

import exceptions.AgeOutOfRange;
import exceptions.NotNegative;
import exceptions.NumberOfCharactersExceedsTheLimit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DeveloperTests {
    @Test
    public void testSetFirst_name() {
        String[] sets = {
                "Test".repeat(5),
                "Test".repeat(30),
                null
        };

        for (String set : sets) {
            try {
                Developer developer = new Developer();
                developer.setFirstName(set);
                Assertions.assertEquals(set, developer.getFirstName());
            } catch (NumberOfCharactersExceedsTheLimit thrown) {
                assert set != null;
                String result = "The value is too long for the field \"firstName\". Limit = 50. " +
                        "You are transmitting = " + set.length() + " symbol.";
                Assertions.assertEquals(result, thrown.getMessage());
            }
        }
    }

    @Test
    public void testSetSecond_name() {
        String[] sets = {
                "Test".repeat(7),
                "Test".repeat(31),
                null
        };

        for (String set : sets) {
            try {
                Developer developer = new Developer();
                developer.setSecondName(set);
                Assertions.assertEquals(set, developer.getSecondName());
            } catch (NumberOfCharactersExceedsTheLimit thrown) {
                assert set != null;
                String result = "The value is too long for the field \"secondName\". Limit = 50. " +
                        "You are transmitting = " + set.length() + " symbol.";
                Assertions.assertEquals(result, thrown.getMessage());
            }
        }
    }

    @Test
    public void testSetAge() {
        int[] sets = {18, 50, 180, 0, -50};
        for (int set : sets) {
            try {
                Developer developer = new Developer();
                developer.setAge(set);
                Assertions.assertEquals(set, developer.getAge());
            } catch (AgeOutOfRange thrown) {
                String result = "The \"age\" field has a limitation. 0 <= age <= 150. You sent = " + set;
                Assertions.assertEquals(result, thrown.getMessage());
            }
        }
    }

    @Test
    public void testSetSalary() {
        double[] sets = {-10, -1.2, 0, 20, 2500.35};
        for (double set : sets) {
            try {
                Developer developer = new Developer();
                developer.setSalary(set);
                Assertions.assertEquals(set, developer.getSalary());
            } catch (NotNegative thrown) {
                String result = "ERROR. Must be greater than or equal to zero";
                Assertions.assertEquals(result, thrown.getMessage());
            }
        }
    }
}