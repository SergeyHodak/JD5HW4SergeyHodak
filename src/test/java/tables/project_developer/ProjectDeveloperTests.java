package tables.project_developer;

import exceptions.MustNotBeNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProjectDeveloperTests {
    @Test
    public void testSetProject_id() {
        int[] sets = {18, 50, 180, 0, -3};
        for (int set : sets) {
            try {
                ProjectDeveloper projectDeveloper = new ProjectDeveloper();
                projectDeveloper.setProjectId(set);
                Assertions.assertEquals(set, projectDeveloper.getProjectId());
            } catch (MustNotBeNull thrown) {
                String result = "The value passed for writing must be greater than zero.";
                Assertions.assertEquals(result, thrown.getMessage());
            }
        }
    }

    @Test
    public void testSetDeveloper_id() {
        int[] sets = {18, 50, 180, 0, -34};
        for (int set : sets) {
            try {
                ProjectDeveloper projectDeveloper = new ProjectDeveloper();
                projectDeveloper.setDeveloperId(set);
                Assertions.assertEquals(set, projectDeveloper.getDeveloperId());
            } catch (MustNotBeNull thrown) {
                String result = "The value passed for writing must be greater than zero.";
                Assertions.assertEquals(result, thrown.getMessage());
            }
        }
    }
}