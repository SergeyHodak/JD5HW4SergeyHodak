package tables.developers;

import exceptions.AgeOutOfRange;
import exceptions.NumberOfCharactersExceedsTheLimit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tables.developers.Developers;

import java.util.ArrayList;
import java.util.List;

class DevelopersTests {
    @Test
    public void testToStringAndGetters() {
        Developers developers = new Developers();
        String result = "Developers{" +
                "id=" + 0 +
                ", name_surname='" + null + '\'' +
                ", age=" + 0 +
                ", gender=" + null +
                '}';
        Assertions.assertEquals(result, developers.toString());
    }

    @Test
    public void testSetName_surname() {
        List<String> sets = new ArrayList<>();
        sets.add("Test".repeat(20));
        sets.add("Test".repeat(30));

        for (String set : sets) {
            try {
                Developers developers = new Developers();
                developers.setName_surname(set);
                Assertions.assertEquals(set, developers.getName_surname());
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
                Developers developers = new Developers();
                developers.setAge(set);
                Assertions.assertEquals(set, developers.getAge());
            } catch (AgeOutOfRange thrown) {
                Assertions.assertNotEquals("", thrown.getMessage());
            }
        }
    }
}