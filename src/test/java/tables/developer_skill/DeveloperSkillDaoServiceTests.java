package tables.developer_skill;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prefs.Prefs;
import storage.DatabaseInitService;
import tables.developer.Developer;
import tables.developer.DeveloperDaoService;
import tables.skill.Skill;
import tables.skill.SkillDaoService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class DeveloperSkillDaoServiceTests {
    private Connection connection;

    private DeveloperDaoService developerDaoService;
    private SkillDaoService skillDaoService;

    private DeveloperSkillDaoService daoService;

    @BeforeEach
    public void beforeEach() throws SQLException {
        String connectionUrl = new Prefs().getString("testDbUrl");
        new DatabaseInitService().initDb(connectionUrl);
        connection = DriverManager.getConnection(connectionUrl);

        developerDaoService = new DeveloperDaoService(connection);
        skillDaoService = new SkillDaoService(connection);

        daoService = new DeveloperSkillDaoService(connection);
        daoService.clear();
    }

    @AfterEach
    public void afterEach() throws SQLException {
        connection.close();
    }

    @Test
    public void testCreate() throws SQLException {
        developerDaoService.create(new Developer() {{
            setFirstName("TestFirstName");
            setSecondName("TestSecondName");
            setAge(28);
            setGender(Gender.MALE);
        }});

        skillDaoService.create(new Skill() {{
            setDepartment("java");
            setSkillLevel("junior");
        }});

        boolean result = daoService.create(new DeveloperSkill() {{
            setDeveloperId(1);
            setSkillId(1);
        }});

        Assertions.assertTrue(result);
    }

    @Test
    public void getAllTest() throws SQLException {
        developerDaoService.create(new Developer() {{
            setFirstName("TestFirstName");
            setSecondName("TestSecondName");
            setAge(28);
            setGender(Developer.Gender.MALE);
        }});

        skillDaoService.create(new Skill() {{
            setDepartment("java");
            setSkillLevel("junior");
        }});

        DeveloperSkill test = new DeveloperSkill();
        test.setDeveloperId(1);
        test.setSkillId(1);
        daoService.create(test);

        List<DeveloperSkill> expected = Collections.singletonList(test);
        List<DeveloperSkill> actual = daoService.getAll();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testGetAllByProjectId() throws SQLException {
        String[][] valuesForCreateDevelopers = {
                {"TestFirstName", "TestSecondName", "28", "MALE"},
                {"TestFirstName1", "TestSecondName1", "29", "FEMALE"},
                {"TestFirstName2", "TestSecondName2", "30", "MALE"}
        };

        for (String[] valuesForCreateDeveloper : valuesForCreateDevelopers) {
            developerDaoService.create(new Developer() {{
                setFirstName(valuesForCreateDeveloper[0]);
                setSecondName(valuesForCreateDeveloper[1]);
                setAge(Integer.parseInt(valuesForCreateDeveloper[2]));
                setGender(Gender.valueOf(valuesForCreateDeveloper[3]));
            }});
        }

        String[][] valuesForCreateSkills = {
                {"java", "junior"},
                {"java", "middle"},
                {"java", "senior"}
        };

        for (String[] valuesForCreateSkill : valuesForCreateSkills) {
            skillDaoService.create(new Skill() {{
                setDepartment(valuesForCreateSkill[0]);
                setSkillLevel(valuesForCreateSkill[1]);
            }});
        }

        long[][] valuesForCreateDependencies = {
                {1, 2},
                {1, 3},
                {2, 1},
                {2, 3},
                {3, 1},
                {3, 2}
        };

        List<DeveloperSkill> expectedDevelopersSkills = new ArrayList<>();

        for (long[] valuesForCreateDependency : valuesForCreateDependencies) {
            DeveloperSkill expected = new DeveloperSkill();
            expected.setDeveloperId(valuesForCreateDependency[0]);
            expected.setSkillId(valuesForCreateDependency[1]);
            daoService.create(expected);
            expectedDevelopersSkills.add(expected);
        }

        for (int i = 1; i <= 3; i++) {
            List<DeveloperSkill> test = new ArrayList<>();
            for (DeveloperSkill expectedDeveloperSkill : expectedDevelopersSkills) {
                if (expectedDeveloperSkill.getDeveloperId() == i) {
                    test.add(expectedDeveloperSkill);
                }
            }

            List<DeveloperSkill> actualDevelopersSkills = daoService.getAllByDeveloperId(i);
            Assertions.assertEquals(test, actualDevelopersSkills);
        }
    }

    @Test
    public void testGetAllBySkillId() throws SQLException {
        String[][] valuesForCreateDevelopers = {
                {"TestFirstName", "TestSecondName", "28", "MALE"},
                {"TestFirstName1", "TestSecondName1", "29", "FEMALE"},
                {"TestFirstName2", "TestSecondName2", "30", "MALE"}
        };

        for (String[] valuesForCreateDeveloper : valuesForCreateDevelopers) {
            developerDaoService.create(new Developer() {{
                setFirstName(valuesForCreateDeveloper[0]);
                setSecondName(valuesForCreateDeveloper[1]);
                setAge(Integer.parseInt(valuesForCreateDeveloper[2]));
                setGender(Gender.valueOf(valuesForCreateDeveloper[3]));
            }});
        }

        String[][] valuesForCreateSkills = {
                {"java", "junior"},
                {"java", "middle"},
                {"java", "senior"}
        };

        for (String[] valuesForCreateSkill : valuesForCreateSkills) {
            skillDaoService.create(new Skill() {{
                setDepartment(valuesForCreateSkill[0]);
                setSkillLevel(valuesForCreateSkill[1]);
            }});
        }

        long[][] valuesForCreateDependencies = {
                {1, 2},
                {1, 3},
                {2, 1},
                {2, 3},
                {3, 1},
                {3, 2}
        };

        List<DeveloperSkill> expectedDevelopersSkills = new ArrayList<>();

        for (long[] valuesForCreateDependency : valuesForCreateDependencies) {
            DeveloperSkill expected = new DeveloperSkill();
            expected.setDeveloperId(valuesForCreateDependency[0]);
            expected.setSkillId(valuesForCreateDependency[1]);
            daoService.create(expected);
            expectedDevelopersSkills.add(expected);
        }

        for (int i = 1; i <= 3; i++) {
            List<DeveloperSkill> test = new ArrayList<>();
            for (DeveloperSkill expectedDeveloperSkill : expectedDevelopersSkills) {
                if (expectedDeveloperSkill.getSkillId() == i) {
                    test.add(expectedDeveloperSkill);
                }
            }

            List<DeveloperSkill> actualDevelopersSkills = daoService.getAllBySkillId(i);
            Assertions.assertEquals(test, actualDevelopersSkills);
        }
    }

    @Test
    public void testUpdate() throws SQLException {
        String[][] valuesForCreateDevelopers = {
                {"TestFirstName", "TestSecondName", "28", "MALE"},
                {"TestFirstName1", "TestSecondName1", "29", "FEMALE"},
                {"TestFirstName2", "TestSecondName2", "30", "MALE"}
        };

        for (String[] valuesForCreateDeveloper : valuesForCreateDevelopers) {
            developerDaoService.create(new Developer() {{
                setFirstName(valuesForCreateDeveloper[0]);
                setSecondName(valuesForCreateDeveloper[1]);
                setAge(Integer.parseInt(valuesForCreateDeveloper[2]));
                setGender(Gender.valueOf(valuesForCreateDeveloper[3]));
            }});
        }

        String[][] valuesForCreateSkills = {
                {"java", "junior"},
                {"java", "middle"},
                {"java", "senior"}
        };

        for (String[] valuesForCreateSkill : valuesForCreateSkills) {
            skillDaoService.create(new Skill() {{
                setDepartment(valuesForCreateSkill[0]);
                setSkillLevel(valuesForCreateSkill[1]);
            }});
        }

        DeveloperSkill original = new DeveloperSkill();
        original.setDeveloperId(1);
        original.setSkillId(1);
        daoService.create(original);

        long[][] valuesForUpdates = {
                {1, 2},
                {1, 3},
                {2, 1},
                {2, 3},
                {3, 1},
                {3, 2},
                {2, 2},
                {3, 3}
        };

        long oldProjectId = original.getDeveloperId();
        long oldDeveloperId = original.getSkillId();

        for (long[] valuesForUpdate : valuesForUpdates) {
            DeveloperSkill test = new DeveloperSkill();
            test.setDeveloperId(valuesForUpdate[0]);
            test.setSkillId(valuesForUpdate[1]);

            daoService.update(
                    oldProjectId, oldDeveloperId,
                    test
            );

            Assertions.assertTrue(daoService.exist(valuesForUpdate[0], valuesForUpdate[1]));

            oldProjectId = valuesForUpdate[0];
            oldDeveloperId = valuesForUpdate[1];
        }
    }

    @Test
    public void testDelete() throws SQLException {
        developerDaoService.create(new Developer() {{
            setFirstName("TestFirstName");
            setSecondName("TestSecondName");
            setAge(28);
            setGender(Developer.Gender.MALE);
        }});

        skillDaoService.create(new Skill() {{
            setDepartment("java");
            setSkillLevel("junior");
        }});

        DeveloperSkill test = new DeveloperSkill();
        test.setDeveloperId(1);
        test.setSkillId(1);

        daoService.create(test);
        Assertions.assertNotEquals(new ArrayList<>(), daoService.getAll());

        daoService.delete(test);
        Assertions.assertEquals(new ArrayList<>(), daoService.getAll());
    }
}