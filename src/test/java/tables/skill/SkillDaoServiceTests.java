package tables.skill;

import exceptions.NumberOfCharactersExceedsTheLimit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prefs.Prefs;
import storage.DatabaseInitService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class SkillDaoServiceTests {
    private Connection connection;
    private SkillDaoService daoService;

    @BeforeEach
    public void beforeEach() throws SQLException {
        String connectionUrl = new Prefs().getString("testDbUrl");
        new DatabaseInitService().initDb(connectionUrl);
        connection = DriverManager.getConnection(connectionUrl);
        daoService = new SkillDaoService(connection);
        daoService.clear();
    }

    @AfterEach
    public void afterEach() throws SQLException {
        connection.close();
    }

    @Test
    public void testCreate() throws SQLException {
        String[][] valuesForNewSkills = {
                {"java", "junior"},
                {"java", null},
                {null, "senior"},
                {"T".repeat(51), "junior"},
                {"python", "T".repeat(51)},
        };

        for (String[] value : valuesForNewSkills) {
            try {
                long id = daoService.create(new Skill() {{
                    setDepartment(value[0]);
                    setSkillLevel(value[1]);
                }});

                Skill saved = daoService.getById(id);

                Assertions.assertEquals(id, saved.getId());
                Assertions.assertEquals(value[0], saved.getDepartment());
                Assertions.assertEquals(value[1], saved.getSkillLevel());
            } catch (NumberOfCharactersExceedsTheLimit thrown) {
                Assertions.assertNotEquals("", thrown.getMessage());
            }
        }
    }

    @Test
    public void getAllTest() throws NumberOfCharactersExceedsTheLimit, SQLException {
        String[][] valuesForNewSkills = {
                {"java", "junior"},
                {"java", "middle"},
                {"java", "senior"},
                {"python", "junior"},
                {"python", "middle"},
                {"python", "senior"}
        };

        List<Skill> expected = new ArrayList<>();

        for (String[] valuesForNewSkill : valuesForNewSkills) {
            Skill skill = new Skill();
            skill.setDepartment(valuesForNewSkill[0]);
            skill.setSkillLevel(valuesForNewSkill[1]);
            skill.setId(daoService.create(skill));

            expected.add(skill);
        }

        List<Skill> actual = daoService.getAll();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testUpdate() throws NumberOfCharactersExceedsTheLimit, SQLException {
        Skill original = new Skill();
        original.setDepartment("java");
        original.setSkillLevel("junior");

        long id = daoService.create(original);
        original.setId(id);

        String[][] valuesForUpdates = {
                {"java", "middle"},
                {"java", null},
                {null, "senior"},
                {"T".repeat(51), "junior"},
                {"python", "T".repeat(51)},
        };

        for (String[] skill : valuesForUpdates) {
            try {
                daoService.update(new Skill() {{
                    setId(id);
                    setDepartment(skill[0]);
                    setSkillLevel(skill[1]);
                }});
                Skill saved = daoService.getById(id);
                Assertions.assertEquals(saved.getId(), id);
                Assertions.assertEquals(saved.getDepartment(), skill[0]);
                Assertions.assertEquals(saved.getSkillLevel(), skill[1]);
            } catch (NumberOfCharactersExceedsTheLimit thrown) {
                Assertions.assertNotEquals("", thrown.getMessage());
            }
        }
    }

    @Test
    public void testDelete() throws SQLException, NumberOfCharactersExceedsTheLimit {
        Skill expected = new Skill();
        expected.setDepartment("java");
        expected.setSkillLevel("junior");

        long id = daoService.create(expected);
        daoService.deleteById(id);

        Assertions.assertNull(daoService.getById(id));
    }
}