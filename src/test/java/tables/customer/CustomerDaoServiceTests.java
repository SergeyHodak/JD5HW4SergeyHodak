package tables.customer;

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
        Customer expected = new Customer();
        expected.setFirstName("TestFirstName");
        expected.setSecondName("TestSecondName");
        expected.setAge(19);

        long id = daoService.create(expected);

        Customer actual = daoService.getById(id);

        Assertions.assertEquals(id, actual.getId());
        Assertions.assertEquals(expected.getFirstName(), actual.getFirstName());
        Assertions.assertEquals(expected.getSecondName(), actual.getSecondName());
        Assertions.assertEquals(expected.getAge(), actual.getAge());
    }

    @Test
    public void getAllTest() throws SQLException {
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
    public void testUpdate() throws SQLException {
        Customer original = new Customer();
        original.setFirstName("TestFirstName");
        original.setSecondName("TestSecondName");
        original.setAge(19);

        long id = daoService.create(original);
        original.setId(id);

        Customer expected = new Customer();
        expected.setId(id);
        expected.setFirstName("TestUpdateFirstName");
        expected.setSecondName("TestUpdateSecondName");
        expected.setAge(49);

        daoService.update(expected);

        Customer actual = daoService.getById(id);

        Assertions.assertEquals(actual.getId(), expected.getId());
        Assertions.assertEquals(actual.getFirstName(), expected.getFirstName());
        Assertions.assertEquals(actual.getSecondName(), expected.getSecondName());
        Assertions.assertEquals(actual.getAge(), expected.getAge());
    }

    @Test
    public void testDelete() throws SQLException {
        Customer expected = new Customer();
        expected.setFirstName("TestFirstName");
        expected.setSecondName("TestSecondName");
        expected.setAge(23);

        long id = daoService.create(expected);
        daoService.deleteById(id);

        Assertions.assertNull(daoService.getById(id));
    }
}