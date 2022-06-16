package tables.skill;

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
        Skill expected = new Skill();
        expected.setDepartment("java");
        expected.setSkillLevel("junior");

        long id = daoService.create(expected);

        Skill actual = daoService.getById(id);

        Assertions.assertEquals(id, actual.getId());
        Assertions.assertEquals(expected.getDepartment(), actual.getDepartment());
        Assertions.assertEquals(expected.getSkillLevel(), actual.getSkillLevel());
    }

    @Test
    public void getAllTest() throws SQLException {
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
    public void testUpdate() throws SQLException {
        Skill original = new Skill();
        original.setDepartment("java");
        original.setSkillLevel("junior");

        long id = daoService.create(original);

        Skill expected = new Skill();
        expected.setId(id);
        expected.setDepartment("python");
        expected.setSkillLevel("middle");
        daoService.update(expected);

        Skill actual = daoService.getById(id);

        Assertions.assertEquals(id, actual.getId());
        Assertions.assertEquals(expected.getDepartment(), actual.getDepartment());
        Assertions.assertEquals(expected.getSkillLevel(), actual.getSkillLevel());
    }

    @Test
    public void testDelete() throws SQLException {
        Skill expected = new Skill();
        expected.setDepartment("java");
        expected.setSkillLevel("junior");

        long id = daoService.create(expected);
        daoService.deleteById(id);

        Assertions.assertNull(daoService.getById(id));
    }
}