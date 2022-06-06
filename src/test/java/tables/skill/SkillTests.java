package tables.skill;

import exceptions.NumberOfCharactersExceedsTheLimit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class SkillTests {
    @Test
    public void testSetDepartment() {
        List<String> sets = new ArrayList<>();
        sets.add("T".repeat(10));
        sets.add("T".repeat(60));
        sets.add(null);

        for (String set : sets) {
            try {
                Skill skill = new Skill();
                skill.setDepartment(set);
                Assertions.assertEquals(set, skill.getDepartment());
            } catch (NumberOfCharactersExceedsTheLimit thrown) {
                Assertions.assertNotEquals("", thrown.getMessage());
            }
        }
    }

    @Test
    public void testSetSkillLevel() {
        List<String> sets = new ArrayList<>();
        sets.add("T".repeat(10));
        sets.add("T".repeat(60));
        sets.add(null);

        for (String set : sets) {
            try {
                Skill skill = new Skill();
                skill.setSkillLevel(set);
                Assertions.assertEquals(set, skill.getSkillLevel());
            } catch (NumberOfCharactersExceedsTheLimit thrown) {
                Assertions.assertNotEquals("", thrown.getMessage());
            }
        }
    }
}