package companies;

import exceptions.NumberOfCharactersExceedsTheLimit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class CompaniesTests {

    @Test
    public void testToStringAndGetters() {
        Companies companies = new Companies();
        String result = "Companies{" +
                "id=" + 0 +
                ", name='" + null + '\'' +
                ", description='" + null + '\'' +
                '}';
        Assertions.assertEquals(result, companies.toString());
    }

    @Test
    public void testSetName() {
        List<String> sets = new ArrayList<>();
        sets.add("Test".repeat(49));
        sets.add("Test".repeat(51));

        for (String set : sets) {
            try {
                Companies companies = new Companies();
                companies.setName(set);
                Assertions.assertEquals(set, companies.getName());
            } catch (NumberOfCharactersExceedsTheLimit thrown) {
                Assertions.assertNotEquals("", thrown.getMessage());
            }
        }
    }

    @Test
    public void testSetDescription() {
        List<String> sets = new ArrayList<>();
        sets.add("Test".repeat(49));
        sets.add("Test".repeat(51));

        for (String set : sets) {
            try {
                Companies companies = new Companies();
                companies.setDescription(set);
                Assertions.assertEquals(set, companies.getDescription());
            } catch (NumberOfCharactersExceedsTheLimit thrown) {
                Assertions.assertNotEquals("", thrown.getMessage());
            }
        }
    }
}