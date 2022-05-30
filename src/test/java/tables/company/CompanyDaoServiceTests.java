package tables.company;

import exceptions.NumberOfCharactersExceedsTheLimit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.DatabaseInitService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class CompanyDaoServiceTests {
    private Connection connection;
    private CompanyDaoService daoService;

    @BeforeEach
    public void beforeEach() throws SQLException {
        String connectionUrl = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
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
    public void testCreateNewCompany() throws NumberOfCharactersExceedsTheLimit, SQLException {
        List<Company> originalCompany = new ArrayList<>();

        Company fullValueCompany = new Company();
        fullValueCompany.setName("TestNameCompany");
        fullValueCompany.setDescription("TestDescriptionCompany");
        originalCompany.add(fullValueCompany);

        Company nullNameCompany = new Company();
        nullNameCompany.setDescription("TestDescriptionCompany");
        originalCompany.add(nullNameCompany);

        Company nullDescriptionCompany = new Company();
        nullDescriptionCompany.setName("TestNameCompany");
        originalCompany.add(nullDescriptionCompany);

        for (Company original : originalCompany) {
            long id = daoService.create(original);
            Company saved = daoService.getById(id);

            Assertions.assertEquals(id, saved.getId());
            Assertions.assertEquals(original.getName(), saved.getName());
            Assertions.assertEquals(original.getDescription(), saved.getDescription());
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

        List<Company> originalCompanies = new ArrayList<>();
        Company fullUpdate = new Company();
        fullUpdate.setName("New Name");
        fullUpdate.setDescription("New Description For Company");
        daoService.update(fullUpdate);
        originalCompanies.add(daoService.getById(id));

        Company nullNameUpdate = new Company();
        nullNameUpdate.setName(null);
        nullNameUpdate.setDescription("New Description For Company1");
        daoService.update(nullNameUpdate);
        originalCompanies.add(daoService.getById(id));

        Company nullDescriptionUpdate = new Company();
        nullDescriptionUpdate.setName("New Name1");
        nullDescriptionUpdate.setDescription(null);
        daoService.update(nullDescriptionUpdate);
        originalCompanies.add(daoService.getById(id));

        for (Company updated : originalCompanies) {
            Company saved = daoService.getById(id);

            Assertions.assertEquals(id, updated.getId());
            Assertions.assertEquals(saved.getName(), updated.getName());
            Assertions.assertEquals(saved.getDescription(), updated.getDescription());
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