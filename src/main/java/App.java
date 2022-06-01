import exceptions.AgeOutOfRange;
import exceptions.MustNotBeNull;
import exceptions.NumberOfCharactersExceedsTheLimit;
import prefs.Prefs;
import storage.DatabaseInitService;
import storage.Storage;
import tables.companies.Companies;
import tables.companies.CompaniesDaoService;
import tables.customers.Customers;
import tables.customers.CustomersDaoService;
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


        CompaniesDaoService companiesDaoService = new CompaniesDaoService(storage.getConnection());
        List<Companies> companies = new ArrayList<>();

        Companies companies1 = new Companies();
        companies1.setName("Future Technology");
        companies1.setDescription("Approaching humanity to the near future");
        companies.add(companies1);

        Companies companies2 = new Companies();
        companies2.setName("Agro firm");
        companies2.setDescription("Intellectual provision of agricultural machinery");
        companies.add(companies2);

        Companies companies3 = new Companies();
        companies3.setName("Integrate and use");
        companies3.setDescription("Moving your business to the digital world");
        companies.add(companies3);

        for (Companies company : companies) {
            companiesDaoService.create(company);
        }

        CustomersDaoService customersDaoService = new CustomersDaoService(storage.getConnection());
        List<Customers> customers = new ArrayList<>();

        Customers customers1 = new Customers();
        customers1.setFirstName("Aller");
        customers1.setSecondName("Han");
        customers1.setAge(38);
        customers.add(customers1);

        Customers customers2 = new Customers();
        customers2.setFirstName("Kevin");
        customers2.setSecondName("Stoon");
        customers2.setAge(34);
        customers.add(customers2);

        Customers customers3 = new Customers();
        customers3.setFirstName("Liz");
        customers3.setSecondName("Krabse");
        customers3.setAge(40);
        customers.add(customers3);

        for (Customers customer : customers) {
            customersDaoService.create(customer);
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

        System.out.println(companiesDaoService.getAll());
        System.out.println(customersDaoService.getAll());
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