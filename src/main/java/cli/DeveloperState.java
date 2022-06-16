package cli;

import storage.Storage;
import tables.developer.Developer;
import tables.developer.DeveloperDaoService;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DeveloperState extends CliState {
    private final Scanner scanner;
    private final CliState state;
    private final Storage storage;

    public DeveloperState(CliFSM fsm) {
        super(fsm);
        scanner = fsm.getScanner();
        state = fsm.getState();
        storage = fsm.getStorage();
    }

    private final String path = "Home/Developer";
    private final String exit = "exit";
    private final String show = "show";
    private final String back = "back";
    private final String create = "create";
    private final String getById = "getById";
    private final String getAll = "getAll";
    private final String update = "update";
    private final String deleteById = "deleteById";
    private final String getDevelopersByDepartment = "getDevelopersByDepartment";
    private final String getDevelopersBySkillLevel = "getDevelopersBySkillLevel";

    List<String> availableCmd = List.of(
            exit,
            show,
            back,
            create,
            getById,
            getAll,
            update,
            deleteById,
            getDevelopersByDepartment,
            getDevelopersBySkillLevel
    );

    @Override
    public void init() throws SQLException {
        developerInputLoop();
    }

    private void developerInputLoop() throws SQLException {
        String command = "";
        boolean status = true;
        String navigation = path + ". Enter command:";
        while (status) {
            System.out.println(navigation);
            command =  scanner.nextLine();
            if (availableCmd.contains(command)) {
                switch (command) {
                    case exit: {
                        System.exit(0);
                        status = false;
                        break;
                    }
                    case show: {
                        System.out.println(availableCmd);
                        break;
                    }
                    default: {
                        status = false;
                        break;
                    }
                }
            } else {
                unknownCommand(command);
            }
        }

        switch (command) {
            case back: {
                idleState();
                break;
            }
            case create: {
                create();
                break;
            }
            case getById: {
                getById();
                break;
            }
            case getAll: {
                getAll();
                break;
            }
            case update: {
                update();
                break;
            }
            case deleteById: {
                deleteById();
                break;
            }
            case getDevelopersByDepartment: {
                getDevelopersByDepartment();
                break;
            }
            case getDevelopersBySkillLevel: {
                getDevelopersBySkillLevel();
                break;
            }
        }
    }

    public void unknownCommand(String cmd) {
        state.unknownCommand(cmd);
    }

    @Override
    public void idleState() throws SQLException {
        new CliFSM(storage);
    }

    private void create() throws SQLException {
        Developer developer = new Developer();
        setFirstName(developer, create);
        setSecondName(developer, create);
        setAge(developer, create);
        setGender(developer, create);
        setSalary(developer, create);

        try {
            new DeveloperDaoService(storage.getConnection()).create(developer);
            System.out.println(true);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        developerInputLoop();
    }

    private void getById() throws SQLException {
        long id = setId(new Developer(), getById).getId();
        try {
            Developer byId = new DeveloperDaoService(storage.getConnection()).getById(id);
            System.out.println(byId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        developerInputLoop();
    }

    private void getAll() throws SQLException {
        try {
            List<Developer> all = new DeveloperDaoService(storage.getConnection()).getAll();
            System.out.println(all);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        developerInputLoop();
    }

    private void update() throws SQLException {
        Developer developer = new Developer();
        setId(developer, update);
        setFirstName(developer, update);
        setSecondName(developer, update);
        setAge(developer, update);
        setGender(developer, update);
        setSalary(developer, update);

        try {
            new DeveloperDaoService(storage.getConnection()).update(developer);
            System.out.println(true);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        developerInputLoop();
    }

    private void deleteById() throws SQLException {
        long id = setId(new Developer(), deleteById).getId();
        try {
            new DeveloperDaoService(storage.getConnection()).deleteById(id);
            System.out.println(true);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        developerInputLoop();
    }

    private void getDevelopersByDepartment() throws SQLException {
        String navigation = path + "/" + getDevelopersByDepartment + ". Enter Department:";
        while (true) {
            System.out.println(navigation);
            try {
                String department = scanner.nextLine();
                List<Developer> developersByDepartment = new DeveloperDaoService(storage.getConnection())
                        .getDevelopersByDepartment(department);
                System.out.println(developersByDepartment);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        developerInputLoop();
    }

    private void getDevelopersBySkillLevel() throws SQLException {
        String navigation = path + "/" + getDevelopersBySkillLevel + ". Enter SkillLevel:";

        while (true) {
            System.out.println(navigation);
            try {
                String skillLevel = scanner.nextLine();
                List<Developer> developersByDepartment = new DeveloperDaoService(storage.getConnection())
                        .getDevelopersBySkillLevel(skillLevel);
                System.out.println(developersByDepartment);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        developerInputLoop();
    }

    private Developer setId(Developer developer, String nameCmd) {
        String navigation = path + "/" + nameCmd + ". Enter Id:";
        while (true) {
            System.out.println(navigation);
            try {
                long id = Long.parseLong(scanner.nextLine());
                developer.setId(id);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return developer;
    }

    private void setFirstName(Developer developer, String nameCmd) {
        String navigation = path + "/" + nameCmd + ". Enter FirstName:";
        while (true) {
            System.out.println(navigation);
            String firstName =  scanner.nextLine();
            try {
                developer.setFirstName(firstName);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void setSecondName(Developer developer, String nameCmd) {
        String navigation = path + "/" + nameCmd + ". Enter SecondName:";
        while (true) {
            System.out.println(navigation);
            String secondName =  scanner.nextLine();
            try {
                developer.setSecondName(secondName);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void setAge(Developer developer, String nameCmd) {
        String navigation = path + "/" + nameCmd + ". Enter Age:";
        while (true) {
            System.out.println(navigation);
            try {
                int age = Integer.parseInt(scanner.nextLine());
                developer.setAge(age);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void setGender(Developer developer, String nameCmd) {
        String navigation = path + "/" + nameCmd + ". Enter Gender:";
        while (true) {
            System.out.println(navigation);
            try {
                Developer.Gender gender = Developer.Gender.valueOf(scanner.nextLine());
                developer.setGender(gender);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Enter one of " + Arrays.toString(Developer.Gender.values()));
            }
        }
    }

    private void setSalary(Developer developer, String nameCmd) {
        String navigation = path + "/" + nameCmd + ". Enter Salary:";
        while (true) {
            System.out.println(navigation);
            try {
                double salary = Double.parseDouble(scanner.nextLine());
                developer.setSalary(salary);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}