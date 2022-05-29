package projects;

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
                ", company_id=" + 0 +
                ", customer_id=" + 0 +
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
    public void testSetCompany_id() {
        int[] sets = {18, 50, 180, 0};
        for (int set : sets) {
            try {
                Projects projects = new Projects();
                projects.setCompany_id(set);
                Assertions.assertEquals(set, projects.getCompany_id());
            } catch (MustNotBeNull thrown) {
                Assertions.assertNotEquals("", thrown.getMessage());
            }
        }
    }

    @Test
    public void testSetCustomer_id() {
        int[] sets = {18, 50, 180, 0};
        for (int set : sets) {
            try {
                Projects projects = new Projects();
                projects.setCustomer_id(set);
                Assertions.assertEquals(set, projects.getCustomer_id());
            } catch (MustNotBeNull thrown) {
                Assertions.assertNotEquals("", thrown.getMessage());
            }
        }
    }
}