package tables.skills;

import exceptions.NumberOfCharactersExceedsTheLimit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tables.skills.Skills;

import java.util.ArrayList;
import java.util.List;

class SkillsTests {
    @Test
    public void testToStringAndGetters() {
        Skills skills = new Skills();
        String result = "Skills{" +
                "id=" + 0 +
                ", department='" + null + '\'' +
                ", skill_level='" + null + '\'' +
                '}';
        Assertions.assertEquals(result, skills.toString());
    }

    @Test
    public void testSetDepartment() {
        List<String> sets = new ArrayList<>();
        sets.add("T".repeat(10));
        sets.add("T".repeat(60));

        for (String set : sets) {
            try {
                Skills skills = new Skills();
                skills.setDepartment(set);
                Assertions.assertEquals(set, skills.getDepartment());
            } catch (NumberOfCharactersExceedsTheLimit thrown) {
                Assertions.assertNotEquals("", thrown.getMessage());
            }
        }
    }

    @Test
    public void testSetSkill_level() {
        List<String> sets = new ArrayList<>();
        sets.add("T".repeat(10));
        sets.add("T".repeat(60));

        for (String set : sets) {
            try {
                Skills skills = new Skills();
                skills.setSkill_level(set);
                Assertions.assertEquals(set, skills.getSkill_level());
            } catch (NumberOfCharactersExceedsTheLimit thrown) {
                Assertions.assertNotEquals("", thrown.getMessage());
            }
        }
    }
}