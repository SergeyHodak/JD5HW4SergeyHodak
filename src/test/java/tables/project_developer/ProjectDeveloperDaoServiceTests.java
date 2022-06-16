package tables.project_developer;

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
import java.time.LocalDate;
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
    public void testCreate() throws SQLException {
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
            setCreationDate(LocalDate.now());
        }});

        developerDaoService.create(new Developer() {{
            setFirstName("TestFirstName");
            setSecondName("TestSecondName");
            setAge(28);
            setGender(Developer.Gender.male);
        }});

        boolean result = daoService.create(new ProjectDeveloper() {{
            setProjectId(1);
            setDeveloperId(1);
        }});

        Assertions.assertTrue(result);
    }

    @Test
    public void getAllTest() throws SQLException {
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
            setCreationDate(LocalDate.now());
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
    public void testGetAllByProjectId() throws SQLException{
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
                {"TestName", "1", "1", "2022-06-07"},
                {"TestName1", "1", "1", "2022-06-07"},
                {"TestName2", "1", "1", "2022-06-07"}
        };

        for (String[] valuesForCreateProject : valuesForCreateProjects) {
            projectDaoService.create(new Project() {{
                setName(valuesForCreateProject[0]);
                setCompanyId(Long.parseLong(valuesForCreateProject[1]));
                setCustomerId(Long.parseLong(valuesForCreateProject[2]));
                setCreationDate(LocalDate.parse(valuesForCreateProject[3]));
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
                {1, 2},
                {1, 3},
                {2, 1},
                {2, 3},
                {3, 1},
                {3, 2}
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
    public void testGetAllByDeveloperId() throws SQLException {
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
                {"TestName", "1", "1", "2022-06-07"},
                {"TestName1", "1", "1", "2022-06-07"},
                {"TestName2", "1", "1", "2022-06-07"}
        };

        for (String[] valuesForCreateProject : valuesForCreateProjects) {
            projectDaoService.create(new Project() {{
                setName(valuesForCreateProject[0]);
                setCompanyId(Long.parseLong(valuesForCreateProject[1]));
                setCustomerId(Long.parseLong(valuesForCreateProject[2]));
                setCreationDate(LocalDate.parse(valuesForCreateProject[3]));
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
                {1, 2},
                {1, 3},
                {2, 1},
                {2, 3},
                {3, 1},
                {3, 2}
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

    @Test
    public void testUpdate() throws SQLException {
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
                {"TestName", "1", "1", "2022-06-07"},
                {"TestName1", "1", "1", "2022-06-07"},
                {"TestName2", "1", "1", "2022-06-07"}
        };

        for (String[] valuesForCreateProject : valuesForCreateProjects) {
            projectDaoService.create(new Project() {{
                setName(valuesForCreateProject[0]);
                setCompanyId(Long.parseLong(valuesForCreateProject[1]));
                setCustomerId(Long.parseLong(valuesForCreateProject[2]));
                setCreationDate(LocalDate.parse(valuesForCreateProject[3]));
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

        ProjectDeveloper original = new ProjectDeveloper();
        original.setProjectId(1);
        original.setDeveloperId(1);
        daoService.create(original);

        long[][] valuesForUpdates = {
                {1, 2},
                {1, 3},
                {2, 1},
                {2, 3},
                {3, 1},
                {3, 2},
                {2, 2},
                {3, 3}
        };

        long oldProjectId = original.getProjectId();
        long oldDeveloperId = original.getDeveloperId();

        for (long[] valuesForUpdate : valuesForUpdates) {
            ProjectDeveloper test = new ProjectDeveloper();
            test.setProjectId(valuesForUpdate[0]);
            test.setDeveloperId(valuesForUpdate[1]);

            daoService.update(
                    oldProjectId, oldDeveloperId,
                    test
            );

            Assertions.assertTrue(daoService.exist(valuesForUpdate[0], valuesForUpdate[1]));

            oldProjectId = valuesForUpdate[0];
            oldDeveloperId = valuesForUpdate[1];
        }
    }

    @Test
    public void testDelete() throws SQLException {
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
            setCreationDate(LocalDate.now());
        }});

        developerDaoService.create(new Developer() {{
            setFirstName("TestFirstName");
            setSecondName("TestSecondName");
            setAge(28);
            setGender(Developer.Gender.male);
        }});

        ProjectDeveloper test = new ProjectDeveloper();
        test.setProjectId(1);
        test.setDeveloperId(1);

        daoService.create(test);
        Assertions.assertNotNull(daoService.getAll());

        daoService.delete(test);
        Assertions.assertEquals(new ArrayList<>(), daoService.getAll());
    }
}