package tables.project_developer;

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
import tables.developer.Developer;
import tables.developer.DeveloperDaoService;
import tables.project.Project;
import tables.project.ProjectDaoService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ProjectDeveloperDaoServiceTests {
    private Connection connection;

    private CompanyDaoService companyDaoService;
    private CustomerDaoService customerDaoService;
    private ProjectDaoService projectDaoService;
    private DeveloperDaoService developerDaoService;

    private ProjectDeveloperDaoService daoService;

    @BeforeEach
    public void beforeEach() throws SQLException {
        String connectionUrl = new Prefs().getString("testDbUrl");
        new DatabaseInitService().initDb(connectionUrl);
        connection = DriverManager.getConnection(connectionUrl);

        companyDaoService = new CompanyDaoService(connection);
        customerDaoService = new CustomerDaoService(connection);
        projectDaoService = new ProjectDaoService(connection);
        developerDaoService = new DeveloperDaoService(connection);

        daoService = new ProjectDeveloperDaoService(connection);
        daoService.clear();
    }

    @AfterEach
    public void afterEach() throws SQLException {
        connection.close();
    }

    @Test
    public void testCreate() throws SQLException, NumberOfCharactersExceedsTheLimit, AgeOutOfRange, MustNotBeNull {
        companyDaoService.create(new Company() {{
            setName("TestNameCompany");
            setDescription("TestDescriptionCompany");
        }});

        customerDaoService.create(new Customer() {{
            setFirstName("TestUpdateFirstName");
            setSecondName("TestUpdateSecondName");
            setAge(49);
        }});

        projectDaoService.create(new Project() {{
            setName("TestName");
            setCompanyId(1);
            setCustomerId(1);
        }});

        developerDaoService.create(new Developer() {{
            setFirstName("TestFirstName");
            setSecondName("TestSecondName");
            setAge(28);
            setGender(Developer.Gender.male);
        }});

        int[][] valuesForCreate = {
                {1, 1},
                {1, 0},
                {0, 1}
        };

        for (int[] create : valuesForCreate) {
            try {
                boolean result = daoService.create(new ProjectDeveloper() {{
                    setProjectId(create[0]);
                    setDeveloperId(create[1]);
                }});
                Assertions.assertTrue(result);
            } catch (MustNotBeNull thrown) {
                Assertions.assertNotEquals("", thrown.getMessage());
            }
        }
    }

    @Test
    public void getAllTest() throws NumberOfCharactersExceedsTheLimit, SQLException, AgeOutOfRange, MustNotBeNull {
        companyDaoService.create(new Company() {{
            setName("TestNameCompany");
            setDescription("TestDescriptionCompany");
        }});

        customerDaoService.create(new Customer() {{
            setFirstName("TestUpdateFirstName");
            setSecondName("TestUpdateSecondName");
            setAge(49);
        }});

        projectDaoService.create(new Project() {{
            setName("TestName");
            setCompanyId(1);
            setCustomerId(1);
        }});

        developerDaoService.create(new Developer() {{
            setFirstName("TestFirstName");
            setSecondName("TestSecondName");
            setAge(28);
            setGender(Developer.Gender.male);
        }});

        ProjectDeveloper expected = new ProjectDeveloper();
        expected.setProjectId(1);
        expected.setDeveloperId(1);
        daoService.create(expected);

        List<ProjectDeveloper> expectedProjectsDevelopers = Collections.singletonList(expected);
        List<ProjectDeveloper> actualProjectsDevelopers = daoService.getAll();

        Assertions.assertEquals(expectedProjectsDevelopers, actualProjectsDevelopers);
    }

    @Test
    public void testGetAllByProjectId() throws NumberOfCharactersExceedsTheLimit, SQLException, AgeOutOfRange, MustNotBeNull {
        companyDaoService.create(new Company() {{
            setName("TestNameCompany");
            setDescription("TestDescriptionCompany");
        }});

        customerDaoService.create(new Customer() {{
            setFirstName("TestUpdateFirstName");
            setSecondName("TestUpdateSecondName");
            setAge(49);
        }});

        String[][] valuesForCreateProjects = {
                {"TestName", "1", "1"},
                {"TestName1", "1", "1"},
                {"TestName2", "1", "1"}
        };

        for (String[] valuesForCreateProject : valuesForCreateProjects) {
            projectDaoService.create(new Project() {{
                setName(valuesForCreateProject[0]);
                setCompanyId(Long.parseLong(valuesForCreateProject[1]));
                setCustomerId(Long.parseLong(valuesForCreateProject[2]));
            }});
        }

        String[][] valuesForCreateDevelopers = {
                {"TestFirstName", "TestSecondName", "28", "male"},
                {"TestFirstName1", "TestSecondName1", "29", "male"},
                {"TestFirstName2", "TestSecondName2", "30", "male"}
        };

        for (String[] valuesForCreateDeveloper : valuesForCreateDevelopers) {
            developerDaoService.create(new Developer() {{
                setFirstName(valuesForCreateDeveloper[0]);
                setSecondName(valuesForCreateDeveloper[1]);
                setAge(Integer.parseInt(valuesForCreateDeveloper[2]));
                setGender(Gender.valueOf(valuesForCreateDeveloper[3]));
            }});
        }

        long[][] valuesForCreateDependencies = {
                {1, 2}, {1, 3}, {2, 1}, {2, 3}, {3, 1}, {3, 2}
        };

        List<ProjectDeveloper> expectedProjectsDevelopers = new ArrayList<>();

        for (long[] valuesForCreateDependency : valuesForCreateDependencies) {
            ProjectDeveloper expected = new ProjectDeveloper();
            expected.setProjectId(valuesForCreateDependency[0]);
            expected.setDeveloperId(valuesForCreateDependency[1]);
            daoService.create(expected);
            expectedProjectsDevelopers.add(expected);
        }

        for (int i = 1; i <= 3; i++) {
            List<ProjectDeveloper> test = new ArrayList<>();
            for (ProjectDeveloper expectedProjectsDeveloper : expectedProjectsDevelopers) {
                if (expectedProjectsDeveloper.getProjectId() == i) {
                    test.add(expectedProjectsDeveloper);
                }
            }

            List<ProjectDeveloper> actualProjectsDevelopers = daoService.getAllByProjectId(i);
            Assertions.assertEquals(test, actualProjectsDevelopers);
        }
    }

    @Test
    public void testGetAllByDeveloperId() throws NumberOfCharactersExceedsTheLimit, SQLException, AgeOutOfRange, MustNotBeNull {
        companyDaoService.create(new Company() {{
            setName("TestNameCompany");
            setDescription("TestDescriptionCompany");
        }});

        customerDaoService.create(new Customer() {{
            setFirstName("TestUpdateFirstName");
            setSecondName("TestUpdateSecondName");
            setAge(49);
        }});

        String[][] valuesForCreateProjects = {
                {"TestName", "1", "1"},
                {"TestName1", "1", "1"},
                {"TestName2", "1", "1"}
        };

        for (String[] valuesForCreateProject : valuesForCreateProjects) {
            projectDaoService.create(new Project() {{
                setName(valuesForCreateProject[0]);
                setCompanyId(Long.parseLong(valuesForCreateProject[1]));
                setCustomerId(Long.parseLong(valuesForCreateProject[2]));
            }});
        }

        String[][] valuesForCreateDevelopers = {
                {"TestFirstName", "TestSecondName", "28", "male"},
                {"TestFirstName1", "TestSecondName1", "29", "male"},
                {"TestFirstName2", "TestSecondName2", "30", "male"}
        };

        for (String[] valuesForCreateDeveloper : valuesForCreateDevelopers) {
            developerDaoService.create(new Developer() {{
                setFirstName(valuesForCreateDeveloper[0]);
                setSecondName(valuesForCreateDeveloper[1]);
                setAge(Integer.parseInt(valuesForCreateDeveloper[2]));
                setGender(Gender.valueOf(valuesForCreateDeveloper[3]));
            }});
        }

        long[][] valuesForCreateDependencies = {
                {1, 2}, {1, 3}, {2, 1}, {2, 3}, {3, 1}, {3, 2}
        };

        List<ProjectDeveloper> expectedProjectsDevelopers = new ArrayList<>();

        for (long[] valuesForCreateDependency : valuesForCreateDependencies) {
            ProjectDeveloper expected = new ProjectDeveloper();
            expected.setProjectId(valuesForCreateDependency[0]);
            expected.setDeveloperId(valuesForCreateDependency[1]);
            daoService.create(expected);
            expectedProjectsDevelopers.add(expected);
        }

        for (int i = 1; i <= 3; i++) {
            List<ProjectDeveloper> test = new ArrayList<>();
            for (ProjectDeveloper expectedProjectsDeveloper : expectedProjectsDevelopers) {
                if (expectedProjectsDeveloper.getDeveloperId() == i) {
                    test.add(expectedProjectsDeveloper);
                }
            }

            List<ProjectDeveloper> actualProjectsDevelopers = daoService.getAllByDeveloperId(i);
            Assertions.assertEquals(test, actualProjectsDevelopers);
        }
    }

//    @Test
//    public void testUpdate() throws SQLException, NumberOfCharactersExceedsTheLimit, AgeOutOfRange, MustNotBeNull {
//        Company company = new Company();
//        company.setName("TestNameCompany");
//        company.setDescription("TestDescriptionCompany");
//        companyDaoService.create(company);
//
//        Company companies2 = new Company();
//        companies2.setName("TestNameCompany2");
//        companies2.setDescription("TestDescriptionCompany2");
//        companyDaoService.create(companies2);
//
//        Customer customer = new Customer();
//        customer.setFirstName("TestFirstName");
//        customer.setSecondName("TestSecondName");
//        customer.setAge(19);
//        customerDaoService.create(customer);
//
//        Customer customers2 = new Customer();
//        customers2.setFirstName("TestFirstName2");
//        customers2.setSecondName("TestSecondName2");
//        customers2.setAge(20);
//        customerDaoService.create(customers2);
//
//        Project original = new Project();
//        original.setName("TestName");
//        original.setCompanyId(2);
//        original.setCustomerId(2);
//
//        long id = daoService.create(original);
//        original.setId(id);
//
//        Project fullUpdate = new Project();
//        fullUpdate.setId(id);
//        fullUpdate.setName("TestUpdateName");
//        fullUpdate.setCompanyId(1);
//        fullUpdate.setCustomerId(1);
//        daoService.update(fullUpdate);
//
//        Project saved = daoService.getById(id);
//        Assertions.assertEquals(id, fullUpdate.getId());
//        Assertions.assertEquals(saved.getName(), fullUpdate.getName());
//        Assertions.assertEquals(saved.getCompanyId(), fullUpdate.getCompanyId());
//        Assertions.assertEquals(saved.getCustomerId(), fullUpdate.getCustomerId());
//
//        try {
//            Project zeroCompanyIdUpdate = new Project();
//            zeroCompanyIdUpdate.setId(id);
//            zeroCompanyIdUpdate.setName("TestUpdateName1");
//            zeroCompanyIdUpdate.setCompanyId(0);
//            zeroCompanyIdUpdate.setCustomerId(1);
//            daoService.update(zeroCompanyIdUpdate);
//
//            Project saved2 = daoService.getById(id);
//            Assertions.assertEquals(id, zeroCompanyIdUpdate.getId());
//            Assertions.assertEquals(saved2.getName(), zeroCompanyIdUpdate.getName());
//            Assertions.assertEquals(saved2.getCompanyId(), zeroCompanyIdUpdate.getCompanyId());
//            Assertions.assertEquals(saved2.getCustomerId(), zeroCompanyIdUpdate.getCustomerId());
//        } catch (SQLException | MustNotBeNull thrown) {
//            System.out.println(1);
//            Assertions.assertNotEquals("", thrown.getMessage());
//        }
//
//        try {
//            Project zeroCustomerIdUpdate = new Project();
//            zeroCustomerIdUpdate.setId(id);
//            zeroCustomerIdUpdate.setName("TestUpdateName2");
//            zeroCustomerIdUpdate.setCompanyId(1);
//            zeroCustomerIdUpdate.setCustomerId(0);
//            daoService.update(zeroCustomerIdUpdate);
//
//            Project saved3 = daoService.getById(id);
//            Assertions.assertEquals(id, zeroCustomerIdUpdate.getId());
//            Assertions.assertEquals(saved3.getName(), zeroCustomerIdUpdate.getName());
//            Assertions.assertEquals(saved3.getCompanyId(), zeroCustomerIdUpdate.getCompanyId());
//            Assertions.assertEquals(saved3.getCustomerId(), zeroCustomerIdUpdate.getCustomerId());
//        } catch (SQLException | MustNotBeNull thrown) {
//            System.out.println(2);
//            Assertions.assertNotEquals("", thrown.getMessage());
//        }
//    }
//
//    @Test
//    public void testDelete() throws SQLException, NumberOfCharactersExceedsTheLimit, AgeOutOfRange, MustNotBeNull {
//        Company company = new Company();
//        company.setName("TestNameCompany");
//        company.setDescription("TestDescriptionCompany");
//        companyDaoService.create(company);
//
//        Customer customer = new Customer();
//        customer.setFirstName("TestFirstName");
//        customer.setSecondName("TestSecondName");
//        customer.setAge(19);
//        customerDaoService.create(customer);
//
//        Project expected = new Project();
//        expected.setName("TestName");
//        expected.setCompanyId(1);
//        expected.setCustomerId(1);
//
//        long id = daoService.create(expected);
//        daoService.deleteById(id);
//
//        Assertions.assertNull(daoService.getById(id));
//    }
}