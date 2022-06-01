package tables.projects;

import exceptions.AgeOutOfRange;
import exceptions.MustNotBeNull;
import exceptions.NumberOfCharactersExceedsTheLimit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prefs.Prefs;
import storage.DatabaseInitService;
import tables.companies.Companies;
import tables.companies.CompaniesDaoService;
import tables.customers.Customers;
import tables.customers.CustomersDaoService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ProjectsDaoServiceTests {
    private Connection connection;
    private ProjectsDaoService daoService;
    private CompaniesDaoService companiesDaoService;
    private CustomersDaoService customersDaoService;

    @BeforeEach
    public void beforeEach() throws SQLException {
        String connectionUrl = new Prefs().getString("testDbUrl");
        new DatabaseInitService().initDb(connectionUrl);
        connection = DriverManager.getConnection(connectionUrl);

        daoService = new ProjectsDaoService(connection);
        companiesDaoService = new CompaniesDaoService(connection);
        customersDaoService = new CustomersDaoService(connection);

        daoService.clear();
    }

    @AfterEach
    public void afterEach() throws SQLException {
        connection.close();
    }

    @Test
    public void testCreate() throws NumberOfCharactersExceedsTheLimit, SQLException, MustNotBeNull, AgeOutOfRange {
        Companies companies = new Companies();
        companies.setName("TestNameCompany");
        companies.setDescription("TestDescriptionCompany");
        companiesDaoService.create(companies);

        Customers customers = new Customers();
        customers.setFirstName("TestFirstName");
        customers.setSecondName("TestSecondName");
        customers.setAge(19);
        customersDaoService.create(customers);

        List<Projects> originalProjects = new ArrayList<>();

        Projects fullValueProjects = new Projects();
        fullValueProjects.setName("TestName");
        fullValueProjects.setCompanyId(1);
        fullValueProjects.setCustomerId(1);
        originalProjects.add(fullValueProjects);

        Projects nullNameProjects = new Projects();
        nullNameProjects.setName(null);
        nullNameProjects.setCompanyId(1);
        nullNameProjects.setCustomerId(1);
        originalProjects.add(nullNameProjects);

        for (Projects original : originalProjects) {
            long id = daoService.create(original);
            Projects saved = daoService.getById(id);

            Assertions.assertEquals(id, saved.getId());
            Assertions.assertEquals(original.getName(), saved.getName());
            Assertions.assertEquals(original.getCompanyId(), saved.getCompanyId());
            Assertions.assertEquals(original.getCustomerId(), saved.getCustomerId());
        }
    }

    @Test
    public void getAllTest() throws NumberOfCharactersExceedsTheLimit, SQLException, AgeOutOfRange, MustNotBeNull {
        Companies companies = new Companies();
        companies.setName("TestNameCompany");
        companies.setDescription("TestDescriptionCompany");
        companiesDaoService.create(companies);

        Customers customers = new Customers();
        customers.setFirstName("TestFirstName");
        customers.setSecondName("TestSecondName");
        customers.setAge(19);
        customersDaoService.create(customers);

        Projects expected = new Projects();
        expected.setName("TestName");
        expected.setCompanyId(1);
        expected.setCustomerId(1);

        long id = daoService.create(expected);
        expected.setId(id);

        List<Projects> expectedCustomers = Collections.singletonList(expected);
        List<Projects> actualCustomers = daoService.getAll();

        Assertions.assertEquals(expectedCustomers, actualCustomers);
    }

    @Test
    public void testUpdate() throws SQLException, NumberOfCharactersExceedsTheLimit, AgeOutOfRange, MustNotBeNull {
        Companies companies = new Companies();
        companies.setName("TestNameCompany");
        companies.setDescription("TestDescriptionCompany");
        companiesDaoService.create(companies);

        Companies companies2 = new Companies();
        companies2.setName("TestNameCompany2");
        companies2.setDescription("TestDescriptionCompany2");
        companiesDaoService.create(companies2);

        Customers customers = new Customers();
        customers.setFirstName("TestFirstName");
        customers.setSecondName("TestSecondName");
        customers.setAge(19);
        customersDaoService.create(customers);

        Customers customers2 = new Customers();
        customers2.setFirstName("TestFirstName2");
        customers2.setSecondName("TestSecondName2");
        customers2.setAge(20);
        customersDaoService.create(customers2);

        Projects original = new Projects();
        original.setName("TestName");
        original.setCompanyId(2);
        original.setCustomerId(2);

        long id = daoService.create(original);
        original.setId(id);

        Projects fullUpdate = new Projects();
        fullUpdate.setId(id);
        fullUpdate.setName("TestUpdateName");
        fullUpdate.setCompanyId(1);
        fullUpdate.setCustomerId(1);
        daoService.update(fullUpdate);

        Projects saved = daoService.getById(id);
        Assertions.assertEquals(id, fullUpdate.getId());
        Assertions.assertEquals(saved.getName(), fullUpdate.getName());
        Assertions.assertEquals(saved.getCompanyId(), fullUpdate.getCompanyId());
        Assertions.assertEquals(saved.getCustomerId(), fullUpdate.getCustomerId());

        try {
            Projects zeroCompanyIdUpdate = new Projects();
            zeroCompanyIdUpdate.setId(id);
            zeroCompanyIdUpdate.setName("TestUpdateName1");
            zeroCompanyIdUpdate.setCompanyId(0);
            zeroCompanyIdUpdate.setCustomerId(1);
            daoService.update(zeroCompanyIdUpdate);

            Projects saved2 = daoService.getById(id);
            Assertions.assertEquals(id, zeroCompanyIdUpdate.getId());
            Assertions.assertEquals(saved2.getName(), zeroCompanyIdUpdate.getName());
            Assertions.assertEquals(saved2.getCompanyId(), zeroCompanyIdUpdate.getCompanyId());
            Assertions.assertEquals(saved2.getCustomerId(), zeroCompanyIdUpdate.getCustomerId());
        } catch (SQLException | MustNotBeNull thrown) {
            System.out.println(1);
            Assertions.assertNotEquals("", thrown.getMessage());
        }

        try {
            Projects zeroCustomerIdUpdate = new Projects();
            zeroCustomerIdUpdate.setId(id);
            zeroCustomerIdUpdate.setName("TestUpdateName2");
            zeroCustomerIdUpdate.setCompanyId(1);
            zeroCustomerIdUpdate.setCustomerId(0);
            daoService.update(zeroCustomerIdUpdate);

            Projects saved3 = daoService.getById(id);
            Assertions.assertEquals(id, zeroCustomerIdUpdate.getId());
            Assertions.assertEquals(saved3.getName(), zeroCustomerIdUpdate.getName());
            Assertions.assertEquals(saved3.getCompanyId(), zeroCustomerIdUpdate.getCompanyId());
            Assertions.assertEquals(saved3.getCustomerId(), zeroCustomerIdUpdate.getCustomerId());
        } catch (SQLException | MustNotBeNull thrown) {
            System.out.println(2);
            Assertions.assertNotEquals("", thrown.getMessage());
        }
    }

    @Test
    public void testDelete() throws SQLException, NumberOfCharactersExceedsTheLimit, AgeOutOfRange, MustNotBeNull {
        Companies companies = new Companies();
        companies.setName("TestNameCompany");
        companies.setDescription("TestDescriptionCompany");
        companiesDaoService.create(companies);

        Customers customers = new Customers();
        customers.setFirstName("TestFirstName");
        customers.setSecondName("TestSecondName");
        customers.setAge(19);
        customersDaoService.create(customers);

        Projects expected = new Projects();
        expected.setName("TestName");
        expected.setCompanyId(1);
        expected.setCustomerId(1);

        long id = daoService.create(expected);
        daoService.deleteById(id);

        Assertions.assertNull(daoService.getById(id));
    }
}