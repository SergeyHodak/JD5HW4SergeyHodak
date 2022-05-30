package tables.company;

import exceptions.NumberOfCharactersExceedsTheLimit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class CompanyTests {

    @Test
    public void testToStringAndGetters() {
        Company companies = new Company();
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
                Company companies = new Company();
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
                Company companies = new Company();
                companies.setDescription(set);
                Assertions.assertEquals(set, companies.getDescription());
            } catch (NumberOfCharactersExceedsTheLimit thrown) {
                Assertions.assertNotEquals("", thrown.getMessage());
            }
        }
    }
}