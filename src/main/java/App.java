import exceptions.AgeOutOfRange;
import exceptions.MustNotBeNull;
import exceptions.NumberOfCharactersExceedsTheLimit;
import prefs.Prefs;
import storage.DatabaseInitService;
import storage.Storage;
import tables.company.Company;
import tables.company.CompanyDaoService;
import tables.customer.Customer;
import tables.customer.CustomerDaoService;
import tables.developers.Developers;
import tables.developers.DevelopersDaoService;
import tables.projects.Projects;
import tables.projects.ProjectsDaoService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws NumberOfCharactersExceedsTheLimit, SQLException, AgeOutOfRange, MustNotBeNull {
        new DatabaseInitService().initDb(new Prefs().getString("dbUrl"));
        Storage storage = Storage.getInstance();


        CompanyDaoService companyDaoService = new CompanyDaoService(storage.getConnection());
        List<Company> companies = new ArrayList<>();

        Company company1 = new Company();
        company1.setName("Future Technology");
        company1.setDescription("Approaching humanity to the near future");
        companies.add(company1);

        Company company2 = new Company();
        company2.setName("Agro firm");
        company2.setDescription("Intellectual provision of agricultural machinery");
        companies.add(company2);

        Company company3 = new Company();
        company3.setName("Integrate and use");
        company3.setDescription("Moving your business to the digital world");
        companies.add(company3);

        for (Company company : companies) {
            companyDaoService.create(company);
        }

        CustomerDaoService customerDaoService = new CustomerDaoService(storage.getConnection());
        List<Customer> customers = new ArrayList<>();

        Customer customer1 = new Customer();
        customer1.setFirstName("Aller");
        customer1.setSecondName("Han");
        customer1.setAge(38);
        customers.add(customer1);

        Customer customer2 = new Customer();
        customer2.setFirstName("Kevin");
        customer2.setSecondName("Stoon");
        customer2.setAge(34);
        customers.add(customer2);

        Customer customer3 = new Customer();
        customer3.setFirstName("Liz");
        customer3.setSecondName("Krabse");
        customer3.setAge(40);
        customers.add(customer3);

        for (Customer customer : customers) {
            customerDaoService.create(customer);
        }

        ProjectsDaoService projectsDaoService = new ProjectsDaoService(storage.getConnection());
        List<Projects>  projects = new ArrayList<>();

        Projects project1 = new Projects();
        project1.setName("Artificial intelligence for milling machine");
        project1.setCompanyId(1);
        project1.setCustomerId(2);
        projects.add(project1);

        Projects project2 = new Projects();
        project2.setName("App for simple options");
        project2.setCompanyId(3);
        project2.setCustomerId(1);
        projects.add(project2);

        Projects project3 = new Projects();
        project3.setName("Finding profitable ways to exchange currencies");
        project3.setCompanyId(1);
        project3.setCustomerId(1);
        projects.add(project3);

        for (Projects project : projects) {
            projectsDaoService.create(project);
        }


        DevelopersDaoService developersDaoService = new DevelopersDaoService(storage.getConnection());
        List<Developers>  developers = new ArrayList<>();

        Developers developer1 = new Developers();
        developer1.setFirstName("Did");
        developer1.setFirstName("Panas");
        developer1.setAge(61);
        developer1.setGender(Developers.Gender.male);
        developers.add(developer1);

        Developers developer2 = new Developers();
        developer2.setFirstName("Fedir");
        developer2.setFirstName("Tomson");
        developer2.setAge(45);
        developer2.setGender(Developers.Gender.male);
        developers.add(developer2);

        Developers developer3 = new Developers();
        developer3.setFirstName("Olga");
        developer3.setFirstName("Dzi");
        developer3.setAge(50);
        developer3.setGender(Developers.Gender.female);
        developers.add(developer3);

        Developers developer4 = new Developers();
        developer4.setFirstName("Oleg");
        developer4.setFirstName("Filli");
        developer4.setAge(23);
        developer4.setGender(Developers.Gender.male);
        developers.add(developer4);

        Developers developer5 = new Developers();
        developer5.setFirstName("Nina");
        developer5.setFirstName("Weendi");
        developer5.setAge(24);
        developer5.setGender(Developers.Gender.female);
        developers.add(developer5);

        for (Developers developer : developers) {
            developersDaoService.create(developer);
        }

        System.out.println(companyDaoService.getAll());
        System.out.println(customerDaoService.getAll());
        System.out.println(projectsDaoService.getAll());
        System.out.println(developersDaoService.getAll());
    }
//      INSERT INTO projects_developers (project_id, developer_id) VALUES
//      (1, 1),
//      (1, 3),
//      (1, 5),
//      (2, 2),
//      (2, 4),
//      (2, 5),
//      (3, 1),
//      (3, 2);

//    INSERT INTO skills (department, skill_level) VALUES
//    ('java',  'junior'),
//    ('java',  'middle'),
//    ('java',  'senior'),
//    ('python',  'junior'),
//    ('python',  'middle'),
//    ('python',  'senior');

//    INSERT INTO developers_skills (developer_id, skill_id) VALUES
//    (1, 3),
//    (1, 6),
//    (2, 3),
//    (
//      SELECT id
//      FROM developers
//      WHERE name_surname='Olga Dzi',
//      SELECT id
//      FROM skills
//      WHERE department='java' and skill_level='middle'
//    ),
//    (4, 4),
//    (5, 4),
//    (5, 1);
}