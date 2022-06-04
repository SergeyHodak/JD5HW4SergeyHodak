package tables.developer;

import exceptions.AgeOutOfRange;
import exceptions.NumberOfCharactersExceedsTheLimit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class DeveloperTests {
    @Test
    public void testSetFirst_name() {
        List<String> sets = new ArrayList<>();
        sets.add("Test".repeat(5));
        sets.add("Test".repeat(30));
        sets.add(null);

        for (String set : sets) {
            try {
                Developer developer = new Developer();
                developer.setFirstName(set);
                Assertions.assertEquals(set, developer.getFirstName());
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
        sets.add(null);

        for (String set : sets) {
            try {
                Developer developer = new Developer();
                developer.setSecondName(set);
                Assertions.assertEquals(set, developer.getSecondName());
            } catch (NumberOfCharactersExceedsTheLimit thrown) {
                Assertions.assertNotEquals("", thrown.getMessage());
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
                Assertions.assertNotEquals("", thrown.getMessage());
            }
        }
    }
}