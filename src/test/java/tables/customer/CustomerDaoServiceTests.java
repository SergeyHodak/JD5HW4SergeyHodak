package tables.customer;

import exceptions.AgeOutOfRange;
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

class CustomerDaoServiceTests {
    private Connection connection;
    private CustomerDaoService daoService;

    @BeforeEach
    public void beforeEach() throws SQLException {
        String connectionUrl = new Prefs().getString("testDbUrl");
        new DatabaseInitService().initDb(connectionUrl);
        connection = DriverManager.getConnection(connectionUrl);
        daoService = new CustomerDaoService(connection);
        daoService.clear();
    }

    @AfterEach
    public void afterEach() throws SQLException {
        connection.close();
    }

    @Test
    public void testCreate() throws SQLException {
        String[][] valuesForNewCustomers = {
                {"TestFirstName", "TestSecondName", "19"},
                {null, "TestSecondName1", "20"},
                {"TestFirstName1", null, "21"},
                {"TestFirstName2", "TestSecondName2", "0"},
                {"T".repeat(51), "TestSecondName3", "24"},
                {"TestFirstName3", "T".repeat(51), "25"},
                {"TestFirstName4", "TestSecondName4", "151"},
        };

        for (String[] customer : valuesForNewCustomers) {
            try {
                long id = daoService.create(new Customer() {{
                    setFirstName(customer[0]);
                    setSecondName(customer[1]);
                    setAge(Integer.parseInt(customer[2]));
                }});

                Customer saved = daoService.getById(id);

                Assertions.assertEquals(id, saved.getId());
                Assertions.assertEquals(customer[0], saved.getFirstName());
                Assertions.assertEquals(customer[1], saved.getSecondName());
                Assertions.assertEquals(Integer.parseInt(customer[2]), saved.getAge());
            } catch (NumberOfCharactersExceedsTheLimit | AgeOutOfRange thrown) {
                Assertions.assertNotEquals("", thrown.getMessage());
            }
        }
    }

    @Test
    public void getAllTest() throws SQLException, NumberOfCharactersExceedsTheLimit, AgeOutOfRange {
        Customer expected = new Customer();
        expected.setFirstName("TestFirstName");
        expected.setSecondName("TestSecondName");
        expected.setAge(19);

        long id = daoService.create(expected);
        expected.setId(id);

        List<Customer> expectedCustomers = Collections.singletonList(expected);
        List<Customer> actualCustomers = daoService.getAll();

        Assertions.assertEquals(expectedCustomers, actualCustomers);
    }

    @Test
    public void testUpdate() throws SQLException, NumberOfCharactersExceedsTheLimit, AgeOutOfRange {
        Customer original = new Customer();
        original.setFirstName("TestFirstName");
        original.setSecondName("TestSecondName");
        original.setAge(19);

        long id = daoService.create(original);
        original.setId(id);

        String[][] valuesForUpdates = {
                {"TestUpdateFirstName", "TestUpdateSecondName", "49"},
                {null, "TestUpdateSecondName1", "34"},
                {"TestUpdateFirstName1", null, "33"},
                {"TestUpdateFirstName2", "TestUpdateSecondName2", "0"},
                {"T".repeat(51), "TestUpdateSecondName3", "24"},
                {"TestUpdateFirstName3", "T".repeat(51), "25"},
                {"TestUpdateFirstName4", "TestUpdateSecondName4", "151"},
        };

        for (String[] update : valuesForUpdates) {
            try {
                daoService.update(new Customer() {{
                    setId(id);
                    setFirstName(update[0]);
                    setSecondName(update[1]);
                    setAge(Integer.parseInt(update[2]));
                }});

                Customer saved = daoService.getById(id);

                Assertions.assertEquals(saved.getId(), original.getId());
                Assertions.assertEquals(saved.getFirstName(), update[0]);
                Assertions.assertEquals(saved.getSecondName(), update[1]);
                Assertions.assertEquals(saved.getAge(), Integer.parseInt(update[2]));
            } catch (NumberOfCharactersExceedsTheLimit | AgeOutOfRange thrown) {
                Assertions.assertNotEquals("", thrown.getMessage());
            }

        }
    }

    @Test
    public void testDelete() throws SQLException, NumberOfCharactersExceedsTheLimit, AgeOutOfRange {
        Customer expected = new Customer();
        expected.setFirstName("TestFirstName");
        expected.setSecondName("TestSecondName");
        expected.setAge(23);

        long id = daoService.create(expected);
        daoService.deleteById(id);

        Assertions.assertNull(daoService.getById(id));
    }
}