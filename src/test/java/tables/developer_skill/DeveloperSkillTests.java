package tables.developer_skill;

import exceptions.MustNotBeNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DeveloperSkillTests {
    @Test
    public void testSetDeveloperId() {
        int[] sets = {18, 50, 180, 0, -1};
        for (int set : sets) {
            try {
                DeveloperSkill developerSkill = new DeveloperSkill();
                developerSkill.setDeveloperId(set);
                Assertions.assertEquals(set, developerSkill.getDeveloperId());
            } catch (MustNotBeNull thrown) {
                Assertions.assertNotEquals("", thrown.getMessage());
            }
        }
    }

    @Test
    public void testSetSkillId() {
        int[] sets = {18, 50, 180, 0, -2};
        for (int set : sets) {
            try {
                DeveloperSkill developerSkill = new DeveloperSkill();
                developerSkill.setSkillId(set);
                Assertions.assertEquals(set, developerSkill.getSkillId());
            } catch (MustNotBeNull thrown) {
                Assertions.assertNotEquals("", thrown.getMessage());
            }
        }
    }
}