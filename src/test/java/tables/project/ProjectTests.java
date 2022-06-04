package tables.project;

import exceptions.MustNotBeNull;
import exceptions.NumberOfCharactersExceedsTheLimit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ProjectTests {
    @Test
    public void testSetName() {
        List<String> sets = new ArrayList<>();
        sets.add("Test".repeat(20));
        sets.add("Test".repeat(70));
        sets.add(null);

        for (String set : sets) {
            try {
                Project project = new Project();
                project.setName(set);
                Assertions.assertEquals(set, project.getName());
            } catch (NumberOfCharactersExceedsTheLimit | NullPointerException thrown) {
                Assertions.assertNotEquals("", thrown.getMessage());
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
                Assertions.assertNotEquals("", thrown.getMessage());
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
                Assertions.assertNotEquals("", thrown.getMessage());
            }
        }
    }
}