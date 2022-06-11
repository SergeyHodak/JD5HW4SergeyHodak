package tables.developer;

import exceptions.AgeOutOfRange;
import exceptions.NotNegative;
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
import java.util.Collections;
import java.util.List;

class DeveloperDaoServiceTests {
    private Connection connection;
    private DeveloperDaoService daoService;

    @BeforeEach
    public void beforeEach() throws SQLException {
        String connectionUrl = new Prefs().getString("testDbUrl");
        new DatabaseInitService().initDb(connectionUrl);
        connection = DriverManager.getConnection(connectionUrl);
        daoService = new DeveloperDaoService(connection);
        daoService.clear();
    }

    @AfterEach
    public void afterEach() throws SQLException {
        connection.close();
    }

    @Test
    public void testCreate() throws SQLException {
        String[][] valuesForNewDevelopers = {
                {"TestFirstName", "TestSecondName", "28", "male", "1000"},
                {null, "TestSecondName1", "20", "female", "1000"},
                {"TestFirstName1", null, "21", "male", "1000"},
                {"TestFirstName2", "TestSecondName2", "0", "female", "1000"},
                {"TestFirstName3", "TestSecondName3", "-50", "male", "1000"},
                {"TestFirstName4", "TestSecondName4", "200", "female", "1000"},
                {"TestFirstName5", "TestSecondName5", "15", null, "1000"},
                {"T".repeat(51), "TestSecondName6", "37", "male", "1000"},
                {"TestFirstName6", "T".repeat(51), "33", "female", "1000"}
        };

        for (String[] newDeveloper : valuesForNewDevelopers) {
            try {
                long id = daoService.create(new Developer() {{
                    setFirstName(newDeveloper[0]);
                    setSecondName(newDeveloper[1]);
                    setAge(Integer.parseInt(newDeveloper[2]));
                    setGender(Gender.valueOf(newDeveloper[3]));
                    setSalary(Double.parseDouble(newDeveloper[4]));
                }});

                Developer saved = daoService.getById(id);

                Assertions.assertEquals(id, saved.getId());
                Assertions.assertEquals(newDeveloper[0], saved.getFirstName());
                Assertions.assertEquals(newDeveloper[1], saved.getSecondName());
                Assertions.assertEquals(Integer.parseInt(newDeveloper[2]), saved.getAge());
                Assertions.assertEquals(Developer.Gender.valueOf(newDeveloper[3]), saved.getGender());
                Assertions.assertEquals(Double.parseDouble(newDeveloper[4]), saved.getSalary());
            } catch (AgeOutOfRange | NullPointerException | NumberOfCharactersExceedsTheLimit | NotNegative thrown) {
                Assertions.assertNotEquals("", thrown.getMessage());
            }
        }
    }

    @Test
    public void getAllTest() throws SQLException, NumberOfCharactersExceedsTheLimit, AgeOutOfRange, NotNegative {
        Developer expected = new Developer();
        expected.setFirstName("TestFirstName");
        expected.setSecondName("TestSecondName");
        expected.setAge(19);
        expected.setGender(Developer.Gender.female);
        expected.setSalary(1000);

        long id = daoService.create(expected);
        expected.setId(id);

        List<Developer> expectedCustomers = Collections.singletonList(expected);
        List<Developer> actualCustomers = daoService.getAll();

        Assertions.assertEquals(expectedCustomers, actualCustomers);
    }

    @Test
    public void testUpdate() throws NumberOfCharactersExceedsTheLimit, AgeOutOfRange, SQLException {
        Developer original = new Developer();
        original.setFirstName("TestFirstName");
        original.setSecondName("TestSecondName");
        original.setAge(19);
        original.setGender(Developer.Gender.male);

        long id = daoService.create(original);
        original.setId(id);

        String[][] valuesForUpdates = {
                {"TestUpdateFirstName", "TestUpdateSecondName", "49", "female", "1000"},
                {null, "TestUpdateSecondName1", "34", "male", "1500"},
                {"TestUpdateFirstName1", null, "33", "female", "1124.9"},
                {"TestUpdateFirstName2", "TestUpdateSecondName2", "0", "male", "1039"},
                {"TestFirstName3", "TestSecondName3", "-50", "female", "1000"},
                {"TestFirstName4", "TestSecondName4", "200", "male", "1000"},
                {"TestFirstName5", "TestSecondName5", "15", null, "1000"},
                {"T".repeat(51), "TestSecondName6", "37", "female", "1000"},
                {"TestFirstName6", "T".repeat(51), "33", "male", "1000"}
        };

        for (String[] valuesForUpdate : valuesForUpdates) {
            try {
                daoService.update(new Developer() {{
                    setId(id);
                    setFirstName(valuesForUpdate[0]);
                    setSecondName(valuesForUpdate[1]);
                    setAge(Integer.parseInt(valuesForUpdate[2]));
                    setGender(Gender.valueOf(valuesForUpdate[3]));
                    setSalary(Double.parseDouble(valuesForUpdate[4]));
                }});

                Developer saved = daoService.getById(id);

                Assertions.assertEquals(saved.getId(), id);
                Assertions.assertEquals(saved.getFirstName(), valuesForUpdate[0]);
                Assertions.assertEquals(saved.getSecondName(), valuesForUpdate[1]);
                Assertions.assertEquals(saved.getAge(), Integer.parseInt(valuesForUpdate[2]));
                Assertions.assertEquals(saved.getGender(), Developer.Gender.valueOf(valuesForUpdate[3]));
                Assertions.assertEquals(saved.getSalary(), Double.parseDouble(valuesForUpdate[4]));
            } catch (AgeOutOfRange | NullPointerException | NumberOfCharactersExceedsTheLimit | NotNegative thrown) {
                Assertions.assertNotEquals("", thrown.getMessage());
            }
        }
    }

    @Test
    public void testDelete() throws SQLException, NumberOfCharactersExceedsTheLimit, AgeOutOfRange {
        Developer expected = new Developer();
        expected.setFirstName("TestFirstName");
        expected.setSecondName("TestSecondName");
        expected.setAge(23);
        expected.setGender(Developer.Gender.male);

        long id = daoService.create(expected);
        daoService.deleteById(id);

        Assertions.assertNull(daoService.getById(id));
    }
}