package tables.company;

import exceptions.NumberOfCharactersExceedsTheLimit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CompanyTests {
    @Test
    public void testSetName() {
        String[] sets = {
                "Test".repeat(4),
                "Test".repeat(60),
                null
        };

        for (String set : sets) {
            try {
                Company company = new Company();
                company.setName(set);
                Assertions.assertEquals(set, company.getName());
            } catch (NumberOfCharactersExceedsTheLimit thrown) {
                assert set != null;
                String result =
                        "The value is too long for the field \"name\"."
                                + " Limit = 200. You are transmitting = "
                                + set.length() + " symbol.";
                Assertions.assertEquals(result, thrown.getMessage());
            }
        }
    }

    @Test
    public void testSetDescription() {
        String[] sets = {
                "Test".repeat(5),
                "Test".repeat(80),
                null
        };

        for (String set : sets) {
            try {
                Company company = new Company();
                company.setDescription(set);
                Assertions.assertEquals(set, company.getDescription());
            } catch (NumberOfCharactersExceedsTheLimit thrown) {
                assert set != null;
                String result =
                        "The value is too long for the field \"description\"."
                                + " Limit = 200. You are transmitting = "
                                + set.length() + " symbol.";
                Assertions.assertEquals(result, thrown.getMessage());
            }
        }
    }
}