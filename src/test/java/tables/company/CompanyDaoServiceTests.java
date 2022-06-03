package tables.company;

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
        String[][] valuesForNewCompanies = {
                {"TestNameCompany", "TestDescriptionCompany"},
                {null, "TestDescriptionCompany"},
                {"TestNameCompany", null},
                {"T".repeat(201), "New Description For Company2"},
                {"New Name2", "T".repeat(201)}
        };

        for (String[] company : valuesForNewCompanies) {
            try {
                long id = daoService.create(new Company() {{
                    setName(company[0]);
                    setDescription(company[1]);
                }});
                Company saved = daoService.getById(id);

                Assertions.assertEquals(id, saved.getId());
                Assertions.assertEquals(company[0], saved.getName());
                Assertions.assertEquals(company[1], saved.getDescription());
            } catch (NumberOfCharactersExceedsTheLimit thrown) {
                Assertions.assertNotEquals("", thrown.getMessage());
            }
        }
    }

    @Test
    public void getAllTest() throws SQLException, NumberOfCharactersExceedsTheLimit {
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
    public void testUpdate() throws SQLException, NumberOfCharactersExceedsTheLimit {
        Company original = new Company();
        original.setName("TestNameCompany");
        original.setDescription("TestDescriptionCompany");

        long id = daoService.create(original);
        original.setId(id);

        String[][] valuesForUpdates = {
                {"New Name", "New Description For Company"},
                {null, "New Description For Company1"},
                {"New Name1", null},
                {"T".repeat(201), "New Description For Company2"},
                {"New Name2", "T".repeat(201)}
        };

        for (String[] company : valuesForUpdates) {
            try {
                daoService.update(new Company() {{
                    setId(id);
                    setName(company[0]);
                    setDescription(company[1]);
                }});
                Company saved = daoService.getById(id);
                Assertions.assertEquals(saved.getId(), id);
                Assertions.assertEquals(saved.getName(), company[0]);
                Assertions.assertEquals(saved.getDescription(), company[1]);
            } catch (NumberOfCharactersExceedsTheLimit thrown) {
                Assertions.assertNotEquals("", thrown.getMessage());
            }
        }
    }

    @Test
    public void testDelete() throws SQLException, NumberOfCharactersExceedsTheLimit {
        Company expected = new Company();
        expected.setName("TestName");
        expected.setDescription("TestDescriptionCompany");

        long id = daoService.create(expected);
        daoService.deleteById(id);

        Assertions.assertNull(daoService.getById(id));
    }
}