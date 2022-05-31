package tables.customers;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class CustomersDaoServiceTests {
    private Connection connection;
    private CustomersDaoService daoService;

    @BeforeEach
    public void beforeEach() throws SQLException {
        String connectionUrl = new Prefs().getString("testDbUrl");
        new DatabaseInitService().initDb(connectionUrl);
        connection = DriverManager.getConnection(connectionUrl);
        daoService = new CustomersDaoService(connection);
        daoService.clear();
    }

    @AfterEach
    public void afterEach() throws SQLException {
        connection.close();
    }

    @Test
    public void testCreate() throws NumberOfCharactersExceedsTheLimit, AgeOutOfRange, SQLException {
        List<Customers> originalCustomers = new ArrayList<>();

        Customers fullValueCustomers = new Customers();
        fullValueCustomers.setFirstName("TestFirstName");
        fullValueCustomers.setSecondName("TestSecondName");
        fullValueCustomers.setAge(19);
        originalCustomers.add(fullValueCustomers);

        Customers nullFirstNameCustomers = new Customers();
        nullFirstNameCustomers.setFirstName(null);
        nullFirstNameCustomers.setSecondName("TestSecondName1");
        fullValueCustomers.setAge(20);
        originalCustomers.add(nullFirstNameCustomers);

        Customers nullSecondNameCustomers = new Customers();
        nullSecondNameCustomers.setFirstName("TestFirstName1");
        nullSecondNameCustomers.setSecondName(null);
        nullSecondNameCustomers.setAge(21);
        originalCustomers.add(nullSecondNameCustomers);

        Customers zeroAgeCustomers = new Customers();
        zeroAgeCustomers.setFirstName("TestFirstName2");
        zeroAgeCustomers.setSecondName("TestSecondName2");
        zeroAgeCustomers.setAge(0);
        originalCustomers.add(nullSecondNameCustomers);

        for (Customers original : originalCustomers) {
            long id = daoService.create(original);
            Customers saved = daoService.getById(id);

            Assertions.assertEquals(id, saved.getId());
            Assertions.assertEquals(original.getFirstName(), saved.getFirstName());
            Assertions.assertEquals(original.getSecondName(), saved.getSecondName());
            Assertions.assertEquals(original.getAge(), saved.getAge());
        }
    }

    @Test
    public void getAllTest() throws SQLException, NumberOfCharactersExceedsTheLimit, AgeOutOfRange {
        Customers expected = new Customers();
        expected.setFirstName("TestFirstName");
        expected.setSecondName("TestSecondName");
        expected.setAge(19);

        long id = daoService.create(expected);
        expected.setId(id);

        List<Customers> expectedCustomers = Collections.singletonList(expected);
        List<Customers> actualCustomers = daoService.getAll();

        Assertions.assertEquals(expectedCustomers, actualCustomers);
    }

    @Test
    public void testUpdate() throws SQLException, NumberOfCharactersExceedsTheLimit, AgeOutOfRange {
        Customers original = new Customers();
        original.setFirstName("TestFirstName");
        original.setSecondName("TestSecondName");
        original.setAge(19);

        long id = daoService.create(original);
        original.setId(id);

        List<Customers> originalCustomers = new ArrayList<>();
        Customers fullUpdate = new Customers();
        fullUpdate.setFirstName("TestUpdateFirstName");
        fullUpdate.setSecondName("TestUpdateSecondName");
        fullUpdate.setAge(49);
        daoService.update(fullUpdate);
        originalCustomers.add(daoService.getById(id));

        Customers nullFirstNameUpdate = new Customers();
        nullFirstNameUpdate.setFirstName(null);
        nullFirstNameUpdate.setSecondName("TestUpdateSecondName1");
        nullFirstNameUpdate.setAge(34);
        daoService.update(nullFirstNameUpdate);
        originalCustomers.add(daoService.getById(id));

        Customers nullSecondNameUpdate = new Customers();
        nullSecondNameUpdate.setFirstName("TestUpdateFirstName1");
        nullSecondNameUpdate.setSecondName(null);
        nullSecondNameUpdate.setAge(33);
        daoService.update(nullSecondNameUpdate);
        originalCustomers.add(daoService.getById(id));

        Customers zeroAgeUpdate = new Customers();
        zeroAgeUpdate.setFirstName("TestUpdateFirstName2");
        zeroAgeUpdate.setSecondName("TestUpdateSecondName2");
        zeroAgeUpdate.setAge(0);
        daoService.update(zeroAgeUpdate);
        originalCustomers.add(daoService.getById(id));

        for (Customers updated : originalCustomers) {
            Customers saved = daoService.getById(id);

            Assertions.assertEquals(id, updated.getId());
            Assertions.assertEquals(saved.getFirstName(), updated.getFirstName());
            Assertions.assertEquals(saved.getSecondName(), updated.getSecondName());
            Assertions.assertEquals(saved.getAge(), updated.getAge());
        }
    }

    @Test
    public void testDelete() throws SQLException, NumberOfCharactersExceedsTheLimit, AgeOutOfRange {
        Customers expected = new Customers();
        expected.setFirstName("TestFirstName");
        expected.setSecondName("TestSecondName");
        expected.setAge(23);

        long id = daoService.create(expected);
        daoService.deleteById(id);

        Assertions.assertNull(daoService.getById(id));
    }
}