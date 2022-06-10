import cli.CliFSM;
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
import tables.developer.Developer;
import tables.developer.DeveloperDaoService;
import tables.developer_skill.DeveloperSkill;
import tables.developer_skill.DeveloperSkillDaoService;
import tables.project.Project;
import tables.project.ProjectDaoService;
import tables.project_developer.ProjectDeveloper;
import tables.project_developer.ProjectDeveloperDaoService;
import tables.skill.Skill;
import tables.skill.SkillDaoService;

import java.sql.SQLException;
import java.time.LocalDate;

public class App {
    public static void main(String[] args) throws NumberOfCharactersExceedsTheLimit, SQLException, AgeOutOfRange, MustNotBeNull {
        new DatabaseInitService().initDb(new Prefs().getString("dbUrl"));
        Storage storage = Storage.getInstance();

        //TODO deComment this, to populate the database with data.
        //todo *start
//        CompanyDaoService companyDaoService = new CompanyDaoService(storage.getConnection());
//        CustomerDaoService customerDaoService = new CustomerDaoService(storage.getConnection());
//        ProjectDaoService projectDaoService = new ProjectDaoService(storage.getConnection());
//        DeveloperDaoService developerDaoService = new DeveloperDaoService(storage.getConnection());
//        ProjectDeveloperDaoService projectDeveloperDaoService = new ProjectDeveloperDaoService(storage.getConnection());
//        SkillDaoService skillDaoService = new SkillDaoService(storage.getConnection());
//        DeveloperSkillDaoService developerSkillDaoService = new DeveloperSkillDaoService(storage.getConnection());
//
//        String[][] valuesForCreateCompanies = {
//                {"Future Technology", "Approaching humanity to the near future"},
//                {"Agro firm", "Intellectual provision of agricultural machinery"},
//                {"Integrate and use", "Moving your business to the digital world"}
//        };
//
//        for (String[] values : valuesForCreateCompanies) {
//            companyDaoService.create(new Company() {{
//                setName(values[0]);
//                setDescription(values[1]);
//            }});
//        }
//
//        String[][] valuesForCreateCustomers = {
//                {"Aller", "Han", "38"},
//                {"Kevin", "Stoon", "34"},
//                {"Liz", "Krabse", "40"}
//        };
//
//        for (String[] values : valuesForCreateCustomers) {
//            customerDaoService.create(new Customer() {{
//                setFirstName(values[0]);
//                setSecondName(values[1]);
//                setAge(Integer.parseInt(values[2]));
//            }});
//        }
//
//        String[][] valuesForCreateProjects = {
//                {"Artificial intelligence for milling machine", "1", "2", "2022-06-07"},
//                {"App for simple options", "3", "1", "2022-06-07"},
//                {"Finding profitable ways to exchange currencies", "1", "1", "2022-06-07"}
//        };
//
//        for (String[] values : valuesForCreateProjects) {
//            projectDaoService.create(new Project() {{
//                setName(values[0]);
//                setCompanyId(Long.parseLong(values[1]));
//                setCustomerId(Long.parseLong(values[2]));
//                setCreationDate(LocalDate.parse(values[3]));
//            }});
//        }
//
//        String[][] valuesForCreateDevelopers = {
//                {"Did", "Panas", "61", "male", "7000"},
//                {"Fedir", "Tomson", "45", "male", "4000"},
//                {"Olga", "Dzi", "50", "female", "1000"},
//                {"Oleg", "Filli", "23", "male", "200"},
//                {"Nina", "Weendi", "24", "female", "500"},
//        };
//
//        for (String[] values : valuesForCreateDevelopers) {
//            developerDaoService.create(new Developer() {{
//                setFirstName(values[0]);
//                setSecondName(values[1]);
//                setAge(Integer.parseInt(values[2]));
//                setGender(Gender.valueOf(values[3]));
//                setSalary(Double.parseDouble(values[4]));
//            }});
//        }
//
//        long[][] valueForCreateProjectDevelopers = {
//                {1, 1},
//                {1, 3},
//                {1, 5},
//                {2, 2},
//                {2, 4},
//                {2, 5},
//                {3, 1},
//                {3, 2}
//        };
//
//        for (long[] values : valueForCreateProjectDevelopers) {
//            projectDeveloperDaoService.create(new ProjectDeveloper() {{
//                setProjectId(values[0]);
//                setDeveloperId(values[1]);
//            }});
//        }
//
//        String[][] valuesForCreateSkills = {
//                {"java", "junior"},
//                {"java", "middle"},
//                {"java", "senior"},
//                {"python", "junior"},
//                {"python", "middle"},
//                {"python", "senior"}
//        };
//
//        for (String[] values : valuesForCreateSkills) {
//            skillDaoService.create(new Skill() {{
//                setDepartment(values[0]);
//                setSkillLevel(values[1]);
//            }});
//        }
//
//        long[][] valueForCreateDeveloperSkills = {
//                {1, 3},
//                {1, 6},
//                {2, 3},
//                {3, 2},
//                {4, 4},
//                {5, 4},
//                {5, 1}
//        };
//
//        for (long[] values : valueForCreateDeveloperSkills) {
//            developerSkillDaoService.create(new DeveloperSkill() {{
//                setDeveloperId(values[0]);
//                setSkillId(values[1]);
//            }});
//        }
        //todo *finish

        new CliFSM(storage);
        storage.close();
    }
}