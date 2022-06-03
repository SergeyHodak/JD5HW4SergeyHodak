package tables.projectsDevelopers;

class ProjectsDevelopersDaoServiceTests {
//    private Connection connection;
//    private ProjectsDevelopersDaoService daoService;
//    private CompanyDaoService companyDaoService;
//    private CustomerDaoService customersDaoService;
//    private ProjectsDaoService projectsDaoService;
//    private DevelopersDaoService developersDaoService;
//
//    @BeforeEach
//    public void beforeEach() throws SQLException {
//        String connectionUrl = new Prefs().getString("testDbUrl");
//        new DatabaseInitService().initDb(connectionUrl);
//        connection = DriverManager.getConnection(connectionUrl);
//
//        daoService = new ProjectsDevelopersDaoService(connection);
//        companyDaoService = new CompanyDaoService(connection);
//        customersDaoService = new CustomerDaoService(connection);
//        projectsDaoService = new ProjectsDaoService(connection);
//        developersDaoService = new DevelopersDaoService(connection);
//
//        daoService.clear();
//    }
//
//    @AfterEach
//    public void afterEach() throws SQLException {
//        connection.close();
//    }
//
//    @Test
//    public void testCreate() throws NumberOfCharactersExceedsTheLimit, AgeOutOfRange, SQLException, MustNotBeNull {
//        Company company = new Company();
//        company.setName("TestNameCompany");
//        company.setDescription("TestDescriptionCompany");
//        companyDaoService.create(company);
//
//        Customer customer = new Customer();
//        customer.setFirstName("TestUpdateFirstName");
//        customer.setSecondName("TestUpdateSecondName");
//        customer.setAge(49);
//        customersDaoService.create(customer);
//
//        Projects projects = new Projects();
//        projects.setName("TestName");
//        projects.setCompanyId(1);
//        projects.setCustomerId(1);
//        projectsDaoService.create(projects);
//
//        Developers developers = new Developers();
//        developers.setFirstName("TestFirstName");
//        developers.setSecondName("TestSecondName");
//        developers.setAge(28);
//        developers.setGender(Developers.Gender.male);
//        developersDaoService.create(developers);
//
//        int[][] valuesForCreate = {{1, 1}, {2, 1}, {1, 2}, {1, 0}, {0, 1}};

//        for (int[] create : valuesForCreate) {
//            try {
//                ProjectsDevelopers projectsDevelopers = new ProjectsDevelopers();
//                projectsDevelopers.setProjectId(create[0]);
//                projectsDevelopers.setDeveloperId(create[1]);
//
//                long id = daoService.create(projectsDevelopers);
//                ProjectsDevelopers saved = daoService.getById(id);
//                Assertions.assertEquals(projectsDevelopers.getId(), saved.getId());
//                Assertions.assertEquals(projectsDevelopers.getProjectId(), saved.getProjectId());
//                Assertions.assertEquals(projectsDevelopers.getDeveloperId(), saved.getDeveloperId());
//            } catch (MustNotBeNull | SQLException thrown) {
//                Assertions.assertNotEquals("", thrown.getMessage());
//            }
//        }
//    }

//    @Test
//    public void getAllTest() throws NumberOfCharactersExceedsTheLimit, SQLException, AgeOutOfRange, MustNotBeNull {
//        Company company = new Company();
//        company.setName("TestNameCompany");
//        company.setDescription("TestDescriptionCompany");
//        companyDaoService.create(company);
//
//        Customer customer = new Customer();
//        customer.setFirstName("TestFirstName");
//        customer.setSecondName("TestSecondName");
//        customer.setAge(19);
//        customersDaoService.create(customer);
//
//        Projects expected = new Projects();
//        expected.setName("TestName");
//        expected.setCompanyId(1);
//        expected.setCustomerId(1);
//
//        long id = daoService.create(expected);
//        expected.setId(id);
//
//        List<Projects> expectedCustomers = Collections.singletonList(expected);
//        List<Projects> actualCustomers = daoService.getAll();
//
//        Assertions.assertEquals(expectedCustomers, actualCustomers);
//    }
//
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
//        customersDaoService.create(customer);
//
//        Customer customers2 = new Customer();
//        customers2.setFirstName("TestFirstName2");
//        customers2.setSecondName("TestSecondName2");
//        customers2.setAge(20);
//        customersDaoService.create(customers2);
//
//        Projects original = new Projects();
//        original.setName("TestName");
//        original.setCompanyId(2);
//        original.setCustomerId(2);
//
//        long id = daoService.create(original);
//        original.setId(id);
//
//        Projects fullUpdate = new Projects();
//        fullUpdate.setId(id);
//        fullUpdate.setName("TestUpdateName");
//        fullUpdate.setCompanyId(1);
//        fullUpdate.setCustomerId(1);
//        daoService.update(fullUpdate);
//
//        Projects saved = daoService.getById(id);
//        Assertions.assertEquals(id, fullUpdate.getId());
//        Assertions.assertEquals(saved.getName(), fullUpdate.getName());
//        Assertions.assertEquals(saved.getCompanyId(), fullUpdate.getCompanyId());
//        Assertions.assertEquals(saved.getCustomerId(), fullUpdate.getCustomerId());
//
//        try {
//            Projects zeroCompanyIdUpdate = new Projects();
//            zeroCompanyIdUpdate.setId(id);
//            zeroCompanyIdUpdate.setName("TestUpdateName1");
//            zeroCompanyIdUpdate.setCompanyId(0);
//            zeroCompanyIdUpdate.setCustomerId(1);
//            daoService.update(zeroCompanyIdUpdate);
//
//            Projects saved2 = daoService.getById(id);
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
//            Projects zeroCustomerIdUpdate = new Projects();
//            zeroCustomerIdUpdate.setId(id);
//            zeroCustomerIdUpdate.setName("TestUpdateName2");
//            zeroCustomerIdUpdate.setCompanyId(1);
//            zeroCustomerIdUpdate.setCustomerId(0);
//            daoService.update(zeroCustomerIdUpdate);
//
//            Projects saved3 = daoService.getById(id);
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
//        customersDaoService.create(customer);
//
//        Projects expected = new Projects();
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