package tables.project;

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
import tables.developer.Developer;
import tables.developer.DeveloperDaoService;
import tables.project_developer.ProjectDeveloper;
import tables.project_developer.ProjectDeveloperDaoService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ProjectDaoServiceTests {
    private Connection connection;
    private ProjectDaoService daoService;
    private CompanyDaoService companyDaoService;
    private CustomerDaoService customerDaoService;
    private DeveloperDaoService developerDaoService;
    private ProjectDeveloperDaoService projectDeveloperDaoService;

    @BeforeEach
    public void beforeEach() throws SQLException {
        String connectionUrl = new Prefs().getString("testDbUrl");
        new DatabaseInitService().initDb(connectionUrl);
        connection = DriverManager.getConnection(connectionUrl);

        daoService = new ProjectDaoService(connection);
        companyDaoService = new CompanyDaoService(connection);
        customerDaoService = new CustomerDaoService(connection);
        developerDaoService = new DeveloperDaoService(connection);
        projectDeveloperDaoService = new ProjectDeveloperDaoService(connection);

        projectDeveloperDaoService.clear();
        daoService.clear();
    }

    @AfterEach
    public void afterEach() throws SQLException {
        connection.close();
    }

    @Test
    public void testCreate() throws SQLException {
        companyDaoService.create(new Company() {{
            setName("TestNameCompany");
            setDescription("TestDescriptionCompany");
        }});

        customerDaoService.create(new Customer() {{
            setFirstName("TestFirstName");
            setSecondName("TestSecondName");
            setAge(19);
        }});

        Project expected = new Project();
        expected.setName("TestName");
        expected.setCompanyId(1);
        expected.setCustomerId(1);
        expected.setCost(10_000);
        expected.setCreationDate(LocalDate.now());

        long id = daoService.create(expected);

        Project actual = daoService.getById(id);

        Assertions.assertEquals(id, actual.getId());
        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertEquals(expected.getCompanyId(), actual.getCompanyId());
        Assertions.assertEquals(expected.getCustomerId(), actual.getCustomerId());
        Assertions.assertEquals(expected.getCost(), actual.getCost());
        Assertions.assertEquals(expected.getCreationDate(), actual.getCreationDate());
    }

    @Test
    public void getAllTest() throws SQLException {
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
        expected.setCost(10_000);
        expected.setCreationDate(LocalDate.now());

        long id = daoService.create(expected);
        expected.setId(id);

        List<Project> expectedCustomers = Collections.singletonList(expected);
        List<Project> actualCustomers = daoService.getAll();

        Assertions.assertEquals(expectedCustomers, actualCustomers);
    }

    @Test
    public void testUpdate() throws SQLException {
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
        original.setCost(10_000);
        original.setCreationDate(LocalDate.now());

        long id = daoService.create(original);

        Project expected = new Project();
        expected.setId(id);
        expected.setName("TestUpdateName");
        expected.setCompanyId(2);
        expected.setCustomerId(2);
        expected.setCost(11_000);
        expected.setCreationDate(LocalDate.parse("2022-06-07"));
        daoService.update(expected);

        Project actual = daoService.getById(id);

        Assertions.assertEquals(id, actual.getId());
        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertEquals(expected.getCompanyId(), actual.getCompanyId());
        Assertions.assertEquals(expected.getCustomerId(), actual.getCustomerId());
        Assertions.assertEquals(expected.getCost(), actual.getCost());
        Assertions.assertEquals(expected.getCreationDate(), actual.getCreationDate());
    }

    @Test
    public void testDelete() throws SQLException {
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
        expected.setCost(10_000);
        expected.setCreationDate(LocalDate.now());

        long id = daoService.create(expected);
        daoService.deleteById(id);

        Assertions.assertNull(daoService.getById(id));
    }

    @Test
    public void testGetCostById() throws SQLException {
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
        expected.setCost(10_000);
        expected.setCreationDate(LocalDate.now());

        long id = daoService.create(expected);
        double costById = daoService.getCostById(id);

        Assertions.assertEquals(expected.getCost(), costById);
    }

    @Test
    public void testUpdateCostById() throws SQLException {
        Company company = new Company();
        company.setName("TestNameCompany");
        company.setDescription("TestDescriptionCompany");
        companyDaoService.create(company);

        Customer customer = new Customer();
        customer.setFirstName("TestFirstName");
        customer.setSecondName("TestSecondName");
        customer.setAge(19);
        customerDaoService.create(customer);

        long id = daoService.create(new Project() {{
            setName("TestName");
            setCompanyId(1);
            setCustomerId(1);
            setCost(0);
            setCreationDate(LocalDate.now());
        }});

        String[][] valuesForCreateDevelopers = {
                {"TestFirstName", "TestSecondName", "28", "male", "1000"},
                {"TestFirstName", "TestSecondName", "28", "male", "1100"}
        };

        List<Long> developerIds = new ArrayList<>();
        for (String[] valuesForCreateDeveloper : valuesForCreateDevelopers) {
            long developerId = developerDaoService.create(new Developer() {{
                setFirstName(valuesForCreateDeveloper[0]);
                setSecondName(valuesForCreateDeveloper[1]);
                setAge(Integer.parseInt(valuesForCreateDeveloper[2]));
                setGender(Gender.valueOf(valuesForCreateDeveloper[3]));
                setSalary(Double.parseDouble(valuesForCreateDeveloper[4]));
            }});
            developerIds.add(developerId);
        }

        long[][] valuesForCreateProjectDevelopers = {
                {id, developerIds.get(0)},
                {id, developerIds.get(1)}
        };

        for (long[] valuesForCreateProjectDeveloper : valuesForCreateProjectDevelopers) {
            projectDeveloperDaoService.create(new ProjectDeveloper() {{
                setProjectId(valuesForCreateProjectDeveloper[0]);
                setDeveloperId(valuesForCreateProjectDeveloper[1]);
            }});
        }

        daoService.updateCostById(id);
        Assertions.assertEquals(
                (Double.parseDouble(valuesForCreateDevelopers[0][4]) +
                Double.parseDouble(valuesForCreateDevelopers[1][4])),
                daoService.getCostById(id)
        );
    }

    @Test
    public void testGetAllBySpecialFormat() throws SQLException {
        companyDaoService.create(new Company() {{
            setName("TestNameCompany");
            setDescription("TestDescriptionCompany");
        }});

        customerDaoService.create(new Customer() {{
            setFirstName("TestFirstName");
            setSecondName("TestSecondName");
            setAge(19);
        }});

        long id = daoService.create(new Project() {{
            setName("TestName");
            setCompanyId(1);
            setCustomerId(1);
            setCost(10_000);
            setCreationDate(LocalDate.parse("2022-06-07"));
        }});

        String[][] valuesForNewDevelopers = {
                {"TestFirstName1", "TestSecondName2", "28", "male", "1000"},
                {"TestFirstName2", "TestSecondName2", "30", "female", "1000"},
                {"TestFirstName3", "TestSecondName3", "27", "male", "1000"},
        };

        for (String[] newDeveloper : valuesForNewDevelopers) {
            developerDaoService.create(new Developer() {{
                setFirstName(newDeveloper[0]);
                setSecondName(newDeveloper[1]);
                setAge(Integer.parseInt(newDeveloper[2]));
                setGender(Gender.valueOf(newDeveloper[3]));
                setSalary(Double.parseDouble(newDeveloper[4]));
            }});
        }

        int[][] valuesForCreate = {
                {1, 1},
                {1, 2},
                {1, 3}
        };

        for (int[] create : valuesForCreate) {
            projectDeveloperDaoService.create(new ProjectDeveloper() {{
                setProjectId(create[0]);
                setDeveloperId(create[1]);
            }});
        }

        List<ProjectDaoService.project> allBySpecialFormat = daoService.getAllBySpecialFormat();

        ProjectDaoService.project expected = new ProjectDaoService.project();
        expected.setCreationDate(LocalDate.parse("2022-06-07"));
        expected.setName("TestName");
        expected.setDeveloperCount(3);

        List<ProjectDaoService.project> expectedList = new ArrayList<>();
        expectedList.add(expected);

        Assertions.assertEquals(expectedList, allBySpecialFormat);
    }
}