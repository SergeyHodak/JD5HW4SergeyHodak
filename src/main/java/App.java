import exceptions.AgeOutOfRange;
import exceptions.NumberOfCharactersExceedsTheLimit;
import prefs.Prefs;
import storage.DatabaseInitService;
import storage.Storage;
import tables.company.Company;
import tables.company.CompanyDaoService;
import tables.customers.Customers;
import tables.customers.CustomersDaoService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws NumberOfCharactersExceedsTheLimit, SQLException, AgeOutOfRange {
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
    }
//      INSERT INTO projects (name, company_id, customer_id) VALUES
//      ('Artificial intelligence for milling machine',
//        SELECT id
//        FROM companies
//        WHERE name='Future Technology',
//        SELECT id
//        FROM customers
//        WHERE name_surname='Kevin Stoon'
//      ),
//      ('App for simple options',
//        SELECT id
//        FROM companies
//        WHERE name='Integrate and use',
//        SELECT id
//        FROM customers
//        WHERE name_surname='Aller Han'
//      ),
//      ('Finding profitable ways to exchange currencies', 1, 1);
//
//      INSERT INTO developers (name_surname, age, gender) VALUES
//      ('Did Panas', 61, 'male'),
//      ('Fedir Tomson', 45, 'male'),
//      ('Olga Dzi', 50, 'female'),
//      ('Oleg Filli', 23, 'male'),
//      ('Nina Weendi', 24, 'female');
//
//      INSERT INTO projects_developers (project_id, developer_id) VALUES
//      (1, 1),
//      (1, 3),
//      (1, 5),
//      (2, 2),
//      (2, 4),
//      (2, 5),
//      (3, 1),
//      (3, 2);
//
//    INSERT INTO skills (department, skill_level) VALUES
//    ('java',  'junior'),
//    ('java',  'middle'),
//    ('java',  'senior'),
//    ('python',  'junior'),
//    ('python',  'middle'),
//    ('python',  'senior');
//
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