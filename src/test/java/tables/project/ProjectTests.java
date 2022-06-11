package tables.project;

import exceptions.MustNotBeNull;
import exceptions.NumberOfCharactersExceedsTheLimit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProjectTests {
    @Test
    public void testSetName() {
        String[] sets = {
                "Test".repeat(20),
                "Test".repeat(70),
        };

        for (String set : sets) {
            try {
                Project project = new Project();
                project.setName(set);
                Assertions.assertEquals(set, project.getName());
            } catch (NumberOfCharactersExceedsTheLimit thrown) {
                String result =
                        "The value is too long for the field \"name\"."
                                + " Limit = 200. You are transmitting = "
                                + set.length() + " symbol.";
                Assertions.assertEquals(result, thrown.getMessage());
            }
        }
    }

    @Test
    public void testSetCompanyId() {
        int[] sets = {18, 50, 180, -30, 0};
        for (int set : sets) {
            try {
                Project project = new Project();
                project.setCompanyId(set);
                Assertions.assertEquals(set, project.getCompanyId());
            } catch (MustNotBeNull thrown) {
                Assertions.assertEquals("The value passed for writing must be greater than zero.",
                        thrown.getMessage());
            }
        }
    }

    @Test
    public void testSetCustomerId() {
        int[] sets = {18, 50, 180, 0, -2};
        for (int set : sets) {
            try {
                Project project = new Project();
                project.setCustomerId(set);
                Assertions.assertEquals(set, project.getCustomerId());
            } catch (MustNotBeNull thrown) {
                Assertions.assertEquals("The value passed for writing must be greater than zero.",
                        thrown.getMessage());
            }
        }
    }
}