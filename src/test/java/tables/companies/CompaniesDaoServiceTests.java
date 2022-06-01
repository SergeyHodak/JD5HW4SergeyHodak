package tables.companies;

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

class CompaniesDaoServiceTests {
    private Connection connection;
    private CompaniesDaoService daoService;

    @BeforeEach
    public void beforeEach() throws SQLException {
        String connectionUrl = new Prefs().getString("testDbUrl");
        new DatabaseInitService().initDb(connectionUrl);
        connection = DriverManager.getConnection(connectionUrl);
        daoService = new CompaniesDaoService(connection);
        daoService.clear();
    }

    @AfterEach
    public void afterEach() throws SQLException {
        connection.close();
    }

    @Test
    public void testCreateNewCompany() throws NumberOfCharactersExceedsTheLimit, SQLException {
        List<Companies> originalCompanies = new ArrayList<>();

        Companies fullValueCompanies = new Companies();
        fullValueCompanies.setName("TestNameCompany");
        fullValueCompanies.setDescription("TestDescriptionCompany");
        originalCompanies.add(fullValueCompanies);

        Companies nullNameCompanies = new Companies();
        nullNameCompanies.setName(null);
        nullNameCompanies.setDescription("TestDescriptionCompany");
        originalCompanies.add(nullNameCompanies);

        Companies nullDescriptionCompanies = new Companies();
        nullDescriptionCompanies.setName("TestNameCompany");
        nullDescriptionCompanies.setDescription(null);
        originalCompanies.add(nullDescriptionCompanies);

        for (Companies original : originalCompanies) {
            long id = daoService.create(original);
            Companies saved = daoService.getById(id);

            Assertions.assertEquals(id, saved.getId());
            Assertions.assertEquals(original.getName(), saved.getName());
            Assertions.assertEquals(original.getDescription(), saved.getDescription());
        }
    }

    @Test
    public void getAllTest() throws SQLException, NumberOfCharactersExceedsTheLimit {
        Companies expected = new Companies();
        expected.setName("TestNameCompany");
        expected.setDescription("TestDescriptionCompany");

        long id = daoService.create(expected);
        expected.setId(id);

        List<Companies> expectedHumans = Collections.singletonList(expected);
        List<Companies> actualHumans = daoService.getAll();

        Assertions.assertEquals(expectedHumans, actualHumans);
    }

    @Test
    public void testUpdate() throws SQLException, NumberOfCharactersExceedsTheLimit {
        Companies original = new Companies();
        original.setName("TestNameCompany");
        original.setDescription("TestDescriptionCompany");

        long id = daoService.create(original);
        original.setId(id);

        List<Companies> originalCompanies = new ArrayList<>();
        Companies fullUpdate = new Companies();
        fullUpdate.setName("New Name");
        fullUpdate.setDescription("New Description For Companies");
        daoService.update(fullUpdate);
        originalCompanies.add(daoService.getById(id));

        Companies nullNameUpdate = new Companies();
        nullNameUpdate.setName(null);
        nullNameUpdate.setDescription("New Description For Company1");
        daoService.update(nullNameUpdate);
        originalCompanies.add(daoService.getById(id));

        Companies nullDescriptionUpdate = new Companies();
        nullDescriptionUpdate.setName("New Name1");
        nullDescriptionUpdate.setDescription(null);
        daoService.update(nullDescriptionUpdate);
        originalCompanies.add(daoService.getById(id));

        for (Companies updated : originalCompanies) {
            Companies saved = daoService.getById(id);

            Assertions.assertEquals(id, updated.getId());
            Assertions.assertEquals(saved.getName(), updated.getName());
            Assertions.assertEquals(saved.getDescription(), updated.getDescription());
        }
    }

    @Test
    public void testDelete() throws SQLException, NumberOfCharactersExceedsTheLimit {
        Companies expected = new Companies();
        expected.setName("TestName");
        expected.setDescription("TestDescriptionCompany");

        long id = daoService.create(expected);
        daoService.deleteById(id);

        Assertions.assertNull(daoService.getById(id));
    }
}