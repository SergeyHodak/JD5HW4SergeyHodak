package tables.projects;

import exceptions.MustNotBeNull;
import exceptions.NumberOfCharactersExceedsTheLimit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ProjectsTests {
    @Test
    public void testToStringAndGetters() {
        Projects projects = new Projects();
        String result = "Projects{" +
                "id=" + 0 +
                ", name='" + null + '\'' +
                ", companyId=" + 0 +
                ", customerId=" + 0 +
                '}';
        Assertions.assertEquals(result, projects.toString());
    }

    @Test
    public void testSetName() {
        List<String> sets = new ArrayList<>();
        sets.add("Test".repeat(20));
        sets.add("Test".repeat(70));

        for (String set : sets) {
            try {
                Projects projects = new Projects();
                projects.setName(set);
                Assertions.assertEquals(set, projects.getName());
            } catch (NumberOfCharactersExceedsTheLimit thrown) {
                Assertions.assertNotEquals("", thrown.getMessage());
            }
        }
    }

    @Test
    public void testSetCompanyId() {
        int[] sets = {18, 50, 180, 0};
        for (int set : sets) {
            try {
                Projects projects = new Projects();
                projects.setCompanyId(set);
                Assertions.assertEquals(set, projects.getCompanyId());
            } catch (MustNotBeNull thrown) {
                Assertions.assertNotEquals("", thrown.getMessage());
            }
        }
    }

    @Test
    public void testSetCustomerId() {
        int[] sets = {18, 50, 180, 0};
        for (int set : sets) {
            try {
                Projects projects = new Projects();
                projects.setCustomerId(set);
                Assertions.assertEquals(set, projects.getCustomerId());
            } catch (MustNotBeNull thrown) {
                Assertions.assertNotEquals("", thrown.getMessage());
            }
        }
    }
}