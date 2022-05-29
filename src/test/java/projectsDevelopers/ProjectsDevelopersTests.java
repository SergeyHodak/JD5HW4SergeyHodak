package projectsDevelopers;

import exceptions.MustNotBeNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProjectsDevelopersTests {
    @Test
    public void testToStringAndGetters() {
        ProjectsDevelopers projectsDevelopers = new ProjectsDevelopers();
        String result = "ProjectsDevelopers{" +
                "project_id=" + 0 +
                ", developer_id=" + 0 +
                '}';
        Assertions.assertEquals(result, projectsDevelopers.toString());
    }

    @Test
    public void testSetProject_id() {
        int[] sets = {18, 50, 180, 0};
        for (int set : sets) {
            try {
                ProjectsDevelopers projectsDevelopers = new ProjectsDevelopers();
                projectsDevelopers.setProject_id(set);
                Assertions.assertEquals(set, projectsDevelopers.getProject_id());
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
                projectsDevelopers.setDeveloper_id(set);
                Assertions.assertEquals(set, projectsDevelopers.getDeveloper_id());
            } catch (MustNotBeNull thrown) {
                Assertions.assertNotEquals("", thrown.getMessage());
            }
        }
    }
}