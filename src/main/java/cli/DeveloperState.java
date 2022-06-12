package cli;

import exceptions.AgeOutOfRange;
import exceptions.NumberOfCharactersExceedsTheLimit;
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
        while (status) {
            System.out.println("Home/Developer. Enter command:");

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

        while (true) {
            System.out.println("Home/Developer/Create. Enter firstName:");
            String firstName =  scanner.nextLine();
            try {
                developer.setFirstName(firstName);
                break;
            } catch (NumberOfCharactersExceedsTheLimit e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            System.out.println("Home/Developer/Create. Enter secondName:");
            String secondName =  scanner.nextLine();
            try {
                developer.setSecondName(secondName);
                break;
            } catch (NumberOfCharactersExceedsTheLimit e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            System.out.println("Home/Developer/Create. Enter age:");
            try {
                int age = Integer.parseInt(scanner.nextLine());
                developer.setAge(age);
                break;
            } catch (AgeOutOfRange e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("!!! error. enter an integer value !!!");
            }
        }

        while (true) {
            System.out.println("Home/Developer/Create. Enter gender:");
            try {
                Developer.Gender gender = Developer.Gender.valueOf(scanner.nextLine());
                developer.setGender(gender);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("enter one of " + Arrays.toString(Developer.Gender.values()));
            }
        }

        while (true) {
            System.out.println("Home/Developer/Create. Enter salary:");
            try {
                double salary = Double.parseDouble(scanner.nextLine());
                developer.setSalary(salary);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        try {
            new DeveloperDaoService(storage.getConnection()).create(developer);
            System.out.println("--new developer creation completed successfully--");
        } catch (SQLException e) {
            System.out.println("!!! new developer creation completed with following ERROR from database:");
            System.out.println(e.getMessage());
        }

        developerInputLoop();
    }

    private void getById() throws SQLException {
        int id;

        while (true) {
            System.out.println("Home/Developer/GetById. Enter id:");
            try {
                id = Integer.parseInt(scanner.nextLine());
                if (id <= 0) {
                    System.out.println("!!! enter id with a value greater than zero !!!");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("!!! error. enter an integer value !!!");
            }
        }

        try {
            Developer byId = new DeveloperDaoService(storage.getConnection()).getById(id);
            System.out.println(byId);
        } catch (SQLException e) {
            System.out.println("!!! action completed with following error from database:");
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

        while (true) {
            System.out.println("Home/Developer/Update. Enter id:");
            try {
                int id = Integer.parseInt(scanner.nextLine());
                if (id <= 0) {
                    System.out.println("!!! enter id with a value greater than zero !!!");
                } else {
                    developer.setId(id);
                    break;
                }
            } catch (Exception e) {
                System.out.println("!!! error. enter an integer value !!!");
            }
        }

        while (true) {
            System.out.println("Home/Developer/Update. Enter firstName:");
            String firstName =  scanner.nextLine();
            try {
                developer.setFirstName(firstName);
                break;
            } catch (NumberOfCharactersExceedsTheLimit e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            System.out.println("Home/Developer/Update. Enter secondName:");
            String secondName =  scanner.nextLine();
            try {
                developer.setSecondName(secondName);
                break;
            } catch (NumberOfCharactersExceedsTheLimit e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            System.out.println("Home/Developer/Update. Enter age:");
            try {
                int age = Integer.parseInt(scanner.nextLine());
                developer.setAge(age);
                break;
            } catch (AgeOutOfRange e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("!!! error. enter an integer value !!!");
            }
        }

        while (true) {
            System.out.println("Home/Developer/Update. Enter gender:");
            try {
                Developer.Gender gender = Developer.Gender.valueOf(scanner.nextLine());
                developer.setGender(gender);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("enter one of " + Arrays.toString(Developer.Gender.values()));
            }
        }

        while (true) {
            System.out.println("Home/Developer/Update. Enter salary:");
            try {
                double salary = Double.parseDouble(scanner.nextLine());
                developer.setSalary(salary);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        try {
            new DeveloperDaoService(storage.getConnection()).update(developer);
            System.out.println("--developer update, completed successfully--");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        developerInputLoop();
    }

    private void deleteById() throws SQLException {
        int id;

        while (true) {
            System.out.println("Home/Developer/DeleteById. Enter id:");
            try {
                id = Integer.parseInt(scanner.nextLine());
                if (id <= 0) {
                    System.out.println("!!! enter id with a value greater than zero !!!");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("!!! error. enter an integer value !!!");
            }
        }

        try {
            new DeveloperDaoService(storage.getConnection()).deleteById(id);
            System.out.println("--developer record successfully deleted--");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        developerInputLoop();
    }

    private void getDevelopersByDepartment() throws SQLException {
        while (true) {
            System.out.println("Home/Developer/GetDevelopersByDepartment. Enter department:");
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
        while (true) {
            System.out.println("Home/Developer/GetDevelopersBySkillLevel. Enter skillLevel:");
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
}