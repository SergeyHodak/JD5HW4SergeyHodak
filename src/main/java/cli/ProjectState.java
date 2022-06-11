package cli;

import storage.Storage;
import tables.project.Project;
import tables.project.ProjectDaoService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ProjectState extends CliState {
    private final Scanner scanner;
    private final CliState state;
    private final Storage storage;

    public ProjectState(CliFSM fsm) {
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

    List<String> availableCmd = List.of(
            exit,
            show,
            back,
            create,
            getById,
            getAll,
            update,
            deleteById
    );

    @Override
    public void init() {
        projectInputLoop();
    }

    private void projectInputLoop() {
        String command = "";

        boolean status = true;
        while (status) {
            System.out.println("Home/Project. Enter command:");

            command = scanner.nextLine();

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
        }
    }

    public void unknownCommand(String cmd) {
        state.unknownCommand(cmd);
    }

    @Override
    public void idleState() {
        new CliFSM(storage);
    }

    private void create() {
        Project project = new Project();

        while (true) {
            System.out.println("Home/Project/Create. Enter name:");
            String name = scanner.nextLine();
            try {
                project.setName(name);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            System.out.println("Home/Project/Create. Enter companyId:");
            try {
                long companyId = Long.parseLong(scanner.nextLine());
                project.setCompanyId(companyId);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            System.out.println("Home/Project/Create. Enter customerId:");
            try {
                long customerId = Integer.parseInt(scanner.nextLine());
                project.setCustomerId(customerId);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            System.out.println("Home/Project/Create. Enter creationDate:");
            try {
                LocalDate creationDate = LocalDate.parse(scanner.nextLine());
                project.setCreationDate(creationDate);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("date format = year-month-day. For example: 2022-06-07");
            }
        }

        try {
            new ProjectDaoService(storage.getConnection()).create(project);
            System.out.println("--new project creation completed successfully--");
        } catch (SQLException e) {
            System.out.println("!!! new project creation completed with following ERROR from database:");
            System.out.println(e.getMessage());
        }

        projectInputLoop();
    }

    private void getById() {
        int id;

        while (true) {
            System.out.println("Home/Project/GetById. Enter id:");
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
            Project byId = new ProjectDaoService(storage.getConnection()).getById(id);
            System.out.println(byId);
        } catch (SQLException e) {
            System.out.println("!!! action completed with following error from database:");
            System.out.println(e.getMessage());
        }

        projectInputLoop();
    }

    private void getAll() {
        try {
            List<Project> all = new ProjectDaoService(storage.getConnection()).getAll();
            System.out.println(all);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        projectInputLoop();
    }

    private void update() {
        Project project = new Project();

        while (true) {
            System.out.println("Home/Project/Update. Enter id:");
            try {
                int id = Integer.parseInt(scanner.nextLine());
                if (id <= 0) {
                    System.out.println("!!! enter id with a value greater than zero !!!");
                } else {
                    project.setId(id);
                    break;
                }
            } catch (Exception e) {
                System.out.println("!!! error. enter an integer value !!!");
            }
        }

        while (true) {
            System.out.println("Home/Project/Update. Enter name:");
            String name = scanner.nextLine();
            try {
                project.setName(name);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            System.out.println("Home/Project/Update. Enter companyId:");
            long companyId = Long.parseLong(scanner.nextLine());
            try {
                project.setCompanyId(companyId);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            System.out.println("Home/Project/Update. Enter customerId:");
            try {
                long customerId = Long.parseLong(scanner.nextLine());
                project.setCustomerId(customerId);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            System.out.println("Home/Project/Update. Enter creationDate:");
            try {
                LocalDate creationDate = LocalDate.parse(scanner.nextLine());
                project.setCreationDate(creationDate);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("date format = year-month-day. For example: 2022-06-07");
            }
        }

        try {
            new ProjectDaoService(storage.getConnection()).update(project);
            System.out.println("--project update, completed successfully--");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        projectInputLoop();
    }

    private void deleteById() {
        int id;

        while (true) {
            System.out.println("Home/Project/DeleteById. Enter id:");
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
            new ProjectDaoService(storage.getConnection()).deleteById(id);
            System.out.println("--project record successfully deleted--");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        projectInputLoop();
    }
}