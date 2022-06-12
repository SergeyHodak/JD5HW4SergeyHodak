package tables.project;

import exceptions.AgeOutOfRange;
import exceptions.MustNotBeNull;
import exceptions.NotNegative;
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
                {"TestName", "1", "1", "10000", "2022-06-07"},
                {null, "1", "1", "10000", "2022-06-07"},
                {"TestName1", "0", "1", "10000", "2022-06-07"},
                {"TestName2", "1", "0", "10000", "2022-06-07"}
        };

        for (String[] value : valuesForCreated) {
            try {
                Project project = new Project();
                project.setName(value[0]);
                project.setCompanyId(Long.parseLong(value[1]));
                project.setCustomerId(Long.parseLong(value[2]));
                project.setCost(Double.parseDouble(value[3]));
                project.setCreationDate(LocalDate.parse(value[4]));

                long id = daoService.create(project);

                Project saved = daoService.getById(id);

                Assertions.assertEquals(id, saved.getId());
                Assertions.assertEquals(value[0], saved.getName());
                Assertions.assertEquals(Long.parseLong(value[1]), saved.getCompanyId());
                Assertions.assertEquals(Long.parseLong(value[2]), saved.getCustomerId());
                Assertions.assertEquals(Double.parseDouble(value[3]), saved.getCost());
                Assertions.assertEquals(LocalDate.parse(value[4]), saved.getCreationDate());
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
        expected.setCost(10_000);
        expected.setCreationDate(LocalDate.now());

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
        original.setCost(10_000);
        original.setCreationDate(LocalDate.now());

        long id = daoService.create(original);
        original.setId(id);

        String[][] projects = {
                {"TestUpdateName", "2", "2", "11000", "2022-06-07"},
                {"TestUpdateName1", "0", "1", "12000", "2022-05-07"},
                {"TestUpdateName2", "1", "0", "13000", "2022-04-07"},
                {"TestUpdateName3", "1", "3", "14000", "2022-03-07"},
                {null, "1", "1", "15000", "2022-02-07"}
        };

        for (String[] project : projects) {
            try {
                daoService.update(new Project() {{
                    setId(id);
                    setName(project[0]);
                    setCompanyId(Long.parseLong(project[1]));
                    setCustomerId(Long.parseLong(project[2]));
                    setCost(Double.parseDouble(project[3]));
                    setCreationDate(LocalDate.parse(project[4]));
                }});

                Project saved = daoService.getById(id);

                Assertions.assertEquals(saved.getId(), original.getId());
                Assertions.assertEquals(saved.getName(), project[0]);
                Assertions.assertEquals(saved.getCompanyId(), Long.parseLong(project[1]));
                Assertions.assertEquals(saved.getCustomerId(), Long.parseLong(project[2]));
                Assertions.assertEquals(saved.getCost(), Double.parseDouble(project[3]));
                Assertions.assertEquals(saved.getCreationDate(), LocalDate.parse(project[4]));
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
        expected.setCost(10_000);
        expected.setCreationDate(LocalDate.now());

        long id = daoService.create(expected);
        daoService.deleteById(id);

        Assertions.assertNull(daoService.getById(id));
    }

    @Test
    public void testGetCostById() throws NumberOfCharactersExceedsTheLimit, SQLException, AgeOutOfRange, MustNotBeNull {
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
    public void testUpdateCostById() throws NumberOfCharactersExceedsTheLimit, SQLException, AgeOutOfRange, MustNotBeNull, NotNegative {
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
    public void testGetAllBySpecialFormat() throws NumberOfCharactersExceedsTheLimit, SQLException, AgeOutOfRange, MustNotBeNull, NotNegative {
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