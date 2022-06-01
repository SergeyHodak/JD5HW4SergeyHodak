package tables.projectsDevelopers;

import exceptions.MustNotBeNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProjectsDevelopersTests {
    @Test
    public void testToStringAndGetters() {
        ProjectsDevelopers projectsDevelopers = new ProjectsDevelopers();
        String result = "ProjectsDevelopers{" +
                "projectId=" + 0 +
                ", developerId=" + 0 +
                '}';
        Assertions.assertEquals(result, projectsDevelopers.toString());
    }

    @Test
    public void testSetProject_id() {
        int[] sets = {18, 50, 180, 0};
        for (int set : sets) {
            try {
                ProjectsDevelopers projectsDevelopers = new ProjectsDevelopers();
                projectsDevelopers.setProjectId(set);
                Assertions.assertEquals(set, projectsDevelopers.getProjectId());
            } catch (MustNotBeNull thrown) {
                Assertions.assertNotEquals("", thrown.getMessage());
            }
        }
    }

    @Test
    public void testSetDeveloper_id() {
        int[] sets = {18, 50, 180, 0};
        for (int set : sets) {
            try {
                ProjectsDevelopers projectsDevelopers = new ProjectsDevelopers();
                projectsDevelopers.setDeveloperId(set);
                Assertions.assertEquals(set, projectsDevelopers.getDeveloperId());
            } catch (MustNotBeNull thrown) {
                Assertions.assertNotEquals("", thrown.getMessage());
            }
        }
    }
}