package tables.developersSkills;

import exceptions.MustNotBeNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tables.developersSkills.DevelopersSkills;

class DeveloperSkillsTests {
    @Test
    public void testToStringAndGetters() {
        DevelopersSkills developersSkills = new DevelopersSkills();
        String result = "DevelopersSkills{" +
                "developer_id=" + 0 +
                ", skill_id=" + 0 +
                '}';
        Assertions.assertEquals(result, developersSkills.toString());
    }

    @Test
    public void testSetDeveloper_id() {
        int[] sets = {18, 50, 180, 0};
        for (int set : sets) {
            try {
                DevelopersSkills developersSkills = new DevelopersSkills();
                developersSkills.setDeveloper_id(set);
                Assertions.assertEquals(set, developersSkills.getDeveloper_id());
            } catch (MustNotBeNull thrown) {
                Assertions.assertNotEquals("", thrown.getMessage());
            }
        }
    }

    @Test
    public void testSetSkill_id() {
        int[] sets = {18, 50, 180, 0};
        for (int set : sets) {
            try {
                DevelopersSkills developersSkills = new DevelopersSkills();
                developersSkills.setSkill_id(set);
                Assertions.assertEquals(set, developersSkills.getSkill_id());
            } catch (MustNotBeNull thrown) {
                Assertions.assertNotEquals("", thrown.getMessage());
            }
        }
    }
}