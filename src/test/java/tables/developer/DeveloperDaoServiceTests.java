package tables.developer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prefs.Prefs;
import storage.DatabaseInitService;
import tables.developer_skill.DeveloperSkill;
import tables.developer_skill.DeveloperSkillDaoService;
import tables.skill.Skill;
import tables.skill.SkillDaoService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class DeveloperDaoServiceTests {
    private Connection connection;
    private SkillDaoService skillDaoService;
    private DeveloperDaoService daoService;
    private DeveloperSkillDaoService developerSkillDaoService;

    @BeforeEach
    public void beforeEach() throws SQLException {
        String connectionUrl = new Prefs().getString("testDbUrl");
        new DatabaseInitService().initDb(connectionUrl);
        connection = DriverManager.getConnection(connectionUrl);
        developerSkillDaoService = new DeveloperSkillDaoService(connection);
        skillDaoService = new SkillDaoService(connection);
        daoService = new DeveloperDaoService(connection);
        developerSkillDaoService.clear();
        daoService.clear();
    }

    @AfterEach
    public void afterEach() throws SQLException {
        connection.close();
    }

    @Test
    public void testCreate() throws SQLException {
        Developer expected = new Developer();
        expected.setFirstName("TestFirstName");
        expected.setSecondName("TestSecondName");
        expected.setAge(28);
        expected.setGender(Developer.Gender.MALE);
        expected.setSalary(1_000);

        long id = daoService.create(expected);

        Developer actual = daoService.getById(id);

        Assertions.assertEquals(id, actual.getId());
        Assertions.assertEquals(expected.getFirstName(), actual.getFirstName());
        Assertions.assertEquals(expected.getSecondName(), actual.getSecondName());
        Assertions.assertEquals(expected.getAge(), actual.getAge());
        Assertions.assertEquals(expected.getGender(), actual.getGender());
        Assertions.assertEquals(expected.getSalary(), actual.getSalary());
    }

    @Test
    public void getAllTest() throws SQLException {
        Developer expected = new Developer();
        expected.setFirstName("TestFirstName");
        expected.setSecondName("TestSecondName");
        expected.setAge(19);
        expected.setGender(Developer.Gender.FEMALE);
        expected.setSalary(1_000);

        long id = daoService.create(expected);
        expected.setId(id);

        List<Developer> expectedCustomers = Collections.singletonList(expected);
        List<Developer> actualCustomers = daoService.getAll();

        Assertions.assertEquals(expectedCustomers, actualCustomers);
    }

    @Test
    public void testUpdate() throws SQLException {
        Developer original = new Developer();
        original.setFirstName("TestFirstName");
        original.setSecondName("TestSecondName");
        original.setAge(19);
        original.setGender(Developer.Gender.MALE);

        long id = daoService.create(original);
        original.setId(id);

        Developer expected = new Developer();
        expected.setId(id);
        expected.setFirstName("TestUpdateFirstName");
        expected.setSecondName("TestUpdateSecondName");
        expected.setAge(49);
        expected.setGender(Developer.Gender.FEMALE);
        expected.setSalary(1_000);

        daoService.update(expected);

        Developer actual = daoService.getById(id);

        Assertions.assertEquals(id, actual.getId());
        Assertions.assertEquals(expected.getFirstName(), actual.getFirstName());
        Assertions.assertEquals(expected.getSecondName(), actual.getSecondName());
        Assertions.assertEquals(expected.getAge(), actual.getAge());
        Assertions.assertEquals(expected.getGender(), actual.getGender());
        Assertions.assertEquals(expected.getSalary(), actual.getSalary());
    }

    @Test
    public void testDelete() throws SQLException {
        Developer expected = new Developer();
        expected.setFirstName("TestFirstName");
        expected.setSecondName("TestSecondName");
        expected.setAge(23);
        expected.setGender(Developer.Gender.MALE);

        long id = daoService.create(expected);
        daoService.deleteById(id);

        Assertions.assertNull(daoService.getById(id));
    }

    @Test
    public void testGetDevelopersBySkillLevel() throws SQLException {
        String[][] valuesForNewDevelopers = {
                {"TestFirstName1", "TestSecondName1", "28", "MALE", "1000"},
                {"TestFirstName2", "TestSecondName2", "20", "FEMALE", "1000"},
                {"TestFirstName3", "TestSecondName3", "21", "MALE", "1000"}
        };

        List<Long> developerIds = new ArrayList<>();
        for (String[] newDeveloper : valuesForNewDevelopers) {
            try {
                long developerId = daoService.create(new Developer() {{
                    setFirstName(newDeveloper[0]);
                    setSecondName(newDeveloper[1]);
                    setAge(Integer.parseInt(newDeveloper[2]));
                    setGender(Gender.valueOf(newDeveloper[3]));
                    setSalary(Double.parseDouble(newDeveloper[4]));
                }});
                developerIds.add(developerId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String[][] valuesForNewSkills = {
                {"java", "junior"},
                {"python", "middle"}
        };

        List<Long> skillIds = new ArrayList<>();
        for (String[] value : valuesForNewSkills) {
            try {
                long skillId = skillDaoService.create(new Skill() {{
                    setDepartment(value[0]);
                    setSkillLevel(value[1]);
                }});
                skillIds.add(skillId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        long[][] valuesForCreate = {
                {developerIds.get(0), skillIds.get(0)},
                {developerIds.get(1), skillIds.get(1)},
                {developerIds.get(2), skillIds.get(0)}
        };

        for (long[] create : valuesForCreate) {
            try {
                developerSkillDaoService.create(new DeveloperSkill() {{
                    setDeveloperId(create[0]);
                    setSkillId(create[1]);
                }});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        List<Developer> java = daoService.getDevelopersBySkillLevel("middle");

        Assertions.assertEquals(valuesForNewDevelopers[1][0], java.get(0).getFirstName());
        Assertions.assertEquals(1, java.size());
    }

    @Test
    public void testGetDevelopersByDepartment() throws SQLException {
        String[][] valuesForNewDevelopers = {
                {"TestFirstName4", "TestSecondName4", "28", "MALE", "1000"},
                {"TestFirstName5", "TestSecondName5", "20", "FEMALE", "1000"},
                {"TestFirstName6", "TestSecondName6", "21", "MALE", "1000"}
        };

        List<Long> developerIds = new ArrayList<>();
        for (String[] newDeveloper : valuesForNewDevelopers) {
            try {
                long developerId = daoService.create(new Developer() {{
                    setFirstName(newDeveloper[0]);
                    setSecondName(newDeveloper[1]);
                    setAge(Integer.parseInt(newDeveloper[2]));
                    setGender(Gender.valueOf(newDeveloper[3]));
                    setSalary(Double.parseDouble(newDeveloper[4]));
                }});
                developerIds.add(developerId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String[][] valuesForNewSkills = {
                {"java", "senior"},
                {"python", "senior"}
        };

        List<Long> skillIds = new ArrayList<>();
        for (String[] value : valuesForNewSkills) {
            try {
                long skillId = skillDaoService.create(new Skill() {{
                    setDepartment(value[0]);
                    setSkillLevel(value[1]);
                }});
                skillIds.add(skillId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        long[][] valuesForCreate = {
                {developerIds.get(0), skillIds.get(0)},
                {developerIds.get(1), skillIds.get(1)},
                {developerIds.get(2), skillIds.get(0)}
        };

        for (long[] create : valuesForCreate) {
            try {
                developerSkillDaoService.create(new DeveloperSkill() {{
                    setDeveloperId(create[0]);
                    setSkillId(create[1]);
                }});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        List<Developer> java = daoService.getDevelopersByDepartment("java");

        Assertions.assertEquals(2, java.size());

        String firstName1 = java.get(0).getFirstName();
        Assertions.assertEquals("TestFirstName4", firstName1);

        String firstName2 = java.get(1).getFirstName();
        Assertions.assertEquals("TestFirstName6", firstName2);
    }
}