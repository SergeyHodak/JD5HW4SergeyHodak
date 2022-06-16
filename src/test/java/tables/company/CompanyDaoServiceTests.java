package tables.company;

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

class CompanyDaoServiceTests {
    private Connection connection;
    private CompanyDaoService daoService;

    @BeforeEach
    public void beforeEach() throws SQLException {
        String connectionUrl = new Prefs().getString("testDbUrl");
        new DatabaseInitService().initDb(connectionUrl);
        connection = DriverManager.getConnection(connectionUrl);
        daoService = new CompanyDaoService(connection);
        daoService.clear();
    }

    @AfterEach
    public void afterEach() throws SQLException {
        connection.close();
    }

    @Test
    public void testCreateNewCompany() throws SQLException {
        Company expected = new Company();
        expected.setName("TestNameCompany");
        expected.setDescription("TestDescriptionCompany");

        long id = daoService.create(expected);
        Company actual = daoService.getById(id);

        Assertions.assertEquals(id, actual.getId());
        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertEquals(expected.getDescription(), actual.getDescription());
    }

    @Test
    public void getAllTest() throws SQLException {
        Company expected = new Company();
        expected.setName("TestNameCompany");
        expected.setDescription("TestDescriptionCompany");

        long id = daoService.create(expected);
        expected.setId(id);

        List<Company> expectedHumans = Collections.singletonList(expected);
        List<Company> actualHumans = daoService.getAll();

        Assertions.assertEquals(expectedHumans, actualHumans);
    }

    @Test
    public void testUpdate() throws SQLException {
        Company original = new Company();
        original.setName("TestNameCompany");
        original.setDescription("TestDescriptionCompany");

        long id = daoService.create(original);
        original.setId(id);

        Company expected = new Company();
        expected.setId(id);
        expected.setName("New Name");
        expected.setDescription("New Description For Company");

        daoService.update(expected);

        Company actual = daoService.getById(id);

        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertEquals(expected.getDescription(), actual.getDescription());
    }

    @Test
    public void testDelete() throws SQLException {
        Company expected = new Company();
        expected.setName("TestName");
        expected.setDescription("TestDescriptionCompany");

        long id = daoService.create(expected);
        daoService.deleteById(id);

        Assertions.assertNull(daoService.getById(id));
    }
}