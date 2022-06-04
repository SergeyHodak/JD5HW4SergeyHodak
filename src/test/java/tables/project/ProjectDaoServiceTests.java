package tables.project;

import exceptions.AgeOutOfRange;
import exceptions.MustNotBeNull;
import exceptions.NumberOfCharactersExceedsTheLimit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prefs.Prefs;
import storage.DatabaseInitService;
import tables.company.Company;
import tables.company.CompanyDaoService;
import tables.customer.Customer;
import tables.customer.CustomerDaoService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

class ProjectDaoServiceTests {
    private Connection connection;
    private ProjectDaoService daoService;
    private CompanyDaoService companyDaoService;
    private CustomerDaoService customerDaoService;

    @BeforeEach
    public void beforeEach() throws SQLException {
        String connectionUrl = new Prefs().getString("testDbUrl");
        new DatabaseInitService().initDb(connectionUrl);
        connection = DriverManager.getConnection(connectionUrl);

        daoService = new ProjectDaoService(connection);
        companyDaoService = new CompanyDaoService(connection);
        customerDaoService = new CustomerDaoService(connection);

        daoService.clear();
    }

    @AfterEach
    public void afterEach() throws SQLException {
        connection.close();
    }

    @Test
    public void testCreate() throws NumberOfCharactersExceedsTheLimit, SQLException, AgeOutOfRange {
        companyDaoService.create(new Company() {{
            setName("TestNameCompany");
            setDescription("TestDescriptionCompany");
        }});

        customerDaoService.create(new Customer() {{
            setFirstName("TestFirstName");
            setSecondName("TestSecondName");
            setAge(19);
        }});

        String[][] valuesForCreated = {
                {"TestName", "1", "1"},
                {null, "1", "1"},
                {"TestName1", "0", "1"},
                {"TestName2", "1", "0"}
        };

        for (String[] value : valuesForCreated) {
            try {
                Project project = new Project();
                project.setName(value[0]);
                project.setCompanyId(Long.parseLong(value[1]));
                project.setCustomerId(Long.parseLong(value[2]));

                long id = daoService.create(project);

                Project saved = daoService.getById(id);

                Assertions.assertEquals(id, saved.getId());
                Assertions.assertEquals(value[0], saved.getName());
                Assertions.assertEquals(Long.parseLong(value[1]), saved.getCompanyId());
                Assertions.assertEquals(Long.parseLong(value[2]), saved.getCustomerId());
            } catch (NumberOfCharactersExceedsTheLimit | MustNotBeNull | NullPointerException thrown) {
                Assertions.assertNotEquals("", thrown.getMessage());
            }
        }
    }

    @Test
    public void getAllTest() throws NumberOfCharactersExceedsTheLimit, SQLException, AgeOutOfRange, MustNotBeNull {
        Company company = new Company();
        company.setName("TestNameCompany");
        company.setDescription("TestDescriptionCompany");
        companyDaoService.create(company);

        Customer customer = new Customer();
        customer.setFirstName("TestFirstName");
        customer.setSecondName("TestSecondName");
        customer.setAge(19);
        customerDaoService.create(customer);

        Project expected = new Project();
        expected.setName("TestName");
        expected.setCompanyId(1);
        expected.setCustomerId(1);

        long id = daoService.create(expected);
        expected.setId(id);

        List<Project> expectedCustomers = Collections.singletonList(expected);
        List<Project> actualCustomers = daoService.getAll();

        Assertions.assertEquals(expectedCustomers, actualCustomers);
    }

    @Test
    public void testUpdate() throws NumberOfCharactersExceedsTheLimit, SQLException, AgeOutOfRange, MustNotBeNull {
        String[][] companies = {
                {"TestNameCompany", "TestDescriptionCompany"},
                {"TestNameCompany2", "TestDescriptionCompany2"}
        };

        for (String[] company : companies) {
            companyDaoService.create(new Company() {{
                setName(company[0]);
                setDescription(company[1]);
            }});
        }

        String[][] customers = {
                {"TestFirstName", "TestSecondName", "19"},
                {"TestFirstName2", "TestSecondName2", "20"}
        };

        for (String[] customer : customers) {
            customerDaoService.create(new Customer() {{
                setFirstName(customer[0]);
                setSecondName(customer[1]);
                setAge(Integer.parseInt(customer[2]));
            }});
        }

        Project original = new Project();
        original.setName("TestName");
        original.setCompanyId(1);
        original.setCustomerId(1);

        long id = daoService.create(original);
        original.setId(id);


        String[][] projects = {
                {"TestUpdateName", "2", "2"},
                {"TestUpdateName1", "0", "1"},
                {"TestUpdateName2", "1", "0"},
                {"TestUpdateName3", "1", "3"},
                {null, "1", "1"}
        };

        for (String[] project : projects) {
            try {
                daoService.update(new Project() {{
                    setId(id);
                    setName(project[0]);
                    setCompanyId(Long.parseLong(project[1]));
                    setCustomerId(Long.parseLong(project[2]));
                }});

                Project saved = daoService.getById(id);

                Assertions.assertEquals(saved.getId(), original.getId());
                Assertions.assertEquals(saved.getName(), project[0]);
                Assertions.assertEquals(saved.getCompanyId(), Long.parseLong(project[1]));
                Assertions.assertEquals(saved.getCustomerId(), Long.parseLong(project[2]));
            } catch (SQLException | NumberOfCharactersExceedsTheLimit | MustNotBeNull | NullPointerException thrown) {
                Assertions.assertNotEquals("", thrown.getMessage());
            }
        }
    }

    @Test
    public void testDelete() throws SQLException, NumberOfCharactersExceedsTheLimit, AgeOutOfRange, MustNotBeNull {
        Company company = new Company();
        company.setName("TestNameCompany");
        company.setDescription("TestDescriptionCompany");
        companyDaoService.create(company);

        Customer customer = new Customer();
        customer.setFirstName("TestFirstName");
        customer.setSecondName("TestSecondName");
        customer.setAge(19);
        customerDaoService.create(customer);

        Project expected = new Project();
        expected.setName("TestName");
        expected.setCompanyId(1);
        expected.setCustomerId(1);

        long id = daoService.create(expected);
        daoService.deleteById(id);

        Assertions.assertNull(daoService.getById(id));
    }
}