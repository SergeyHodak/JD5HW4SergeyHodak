package tables.company;

import exceptions.NumberOfCharactersExceedsTheLimit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class CompanyTests {
    @Test
    public void testSetName() {
        List<String> sets = new ArrayList<>();
        sets.add("Test".repeat(4));
        sets.add("Test".repeat(60));
        sets.add(null);

        for (String set : sets) {
            try {
                Company company = new Company();
                company.setName(set);
                Assertions.assertEquals(set, company.getName());
            } catch (NumberOfCharactersExceedsTheLimit thrown) {
                Assertions.assertNotEquals("", thrown.getMessage());
            }
        }
    }

    @Test
    public void testSetDescription() {
        List<String> sets = new ArrayList<>();
        sets.add("Test".repeat(5));
        sets.add("Test".repeat(80));
        sets.add(null);

        for (String set : sets) {
            try {
                Company company = new Company();
                company.setDescription(set);
                Assertions.assertEquals(set, company.getDescription());
            } catch (NumberOfCharactersExceedsTheLimit thrown) {
                Assertions.assertNotEquals("", thrown.getMessage());
            }
        }
    }
}