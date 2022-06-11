package cli;

import exceptions.MustNotBeNull;
import storage.Storage;
import tables.project_developer.ProjectDeveloper;
import tables.project_developer.ProjectDeveloperDaoService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ProjectDeveloperState extends CliState {
    private final Scanner scanner;
    private final CliState state;
    private final Storage storage;

    public ProjectDeveloperState(CliFSM fsm) {
        super(fsm);
        scanner = fsm.getScanner();
        state = fsm.getState();
        storage = fsm.getStorage();
    }

    private final String exit = "exit";
    private final String show = "show";
    private final String back = "back";
    private final String create = "create";
    private final String exist = "exist";
    private final String getAll = "getAll";
    private final String getAllByProjectId = "getAllByProjectId";
    private final String getAllByDeveloperId = "getAllByDeveloperId";
    private final String update = "update";
    private final String delete = "delete";

    List<String> availableCmd = List.of(
            exit,
            show,
            back,
            create,
            exist,
            getAll,
            getAllByProjectId,
            getAllByDeveloperId,
            update,
            delete
    );

    @Override
    public void init() {
        projectDeveloperInputLoop();
    }

    private void projectDeveloperInputLoop() {
        String command = "";

        boolean status = true;
        while (status) {
            System.out.println("Home/ProjectDeveloper. Enter command:");

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
            case exist: {
                exist();
                break;
            }
            case getAll: {
                getAll();
                break;
            }
            case getAllByProjectId: {
                getAllByProjectId();
                break;
            }
            case getAllByDeveloperId: {
                getAllByDeveloperId();
                break;
            }
            case update: {
                update();
                break;
            }
            case delete: {
                delete();
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
        ProjectDeveloper projectDeveloper = new ProjectDeveloper();

        while (true) {
            System.out.println("Home/ProjectDeveloper/Create. Enter projectId:");
            String developerId = scanner.nextLine();
            try {
                long id = Long.parseLong(developerId);
                projectDeveloper.setProjectId(id);
                break;
            } catch (MustNotBeNull e) {
                System.out.println(e.getMessage());
            } catch (Exception ex) {
                System.out.println("!!! error. enter an integer value !!!");
            }
        }

        while (true) {
            System.out.println("Home/ProjectDeveloper/Create. Enter developerId:");
            String skillId = scanner.nextLine();
            try {
                long id = Long.parseLong(skillId);
                projectDeveloper.setDeveloperId(id);
                break;
            } catch (MustNotBeNull e) {
                System.out.println(e.getMessage());
            } catch (Exception ex) {
                System.out.println("!!! error. enter an integer value !!!");
            }
        }

        try {
            new ProjectDeveloperDaoService(storage.getConnection()).create(projectDeveloper);
            System.out.println("--new projectDeveloper creation completed successfully--");
        } catch (SQLException e) {
            System.out.println("!!! new projectDeveloper creation completed with following ERROR from database:");
            System.out.println(e.getMessage());
        }

        projectDeveloperInputLoop();
    }

    private void exist() {
        ProjectDeveloper projectDeveloper = new ProjectDeveloper();

        while (true) {
            System.out.println("Home/ProjectDeveloper/Exist. Enter projectId:");
            try {
                long projectId = Long.parseLong(scanner.nextLine());
                projectDeveloper.setProjectId(projectId);
                break;
            } catch (MustNotBeNull e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("!!! error. enter an integer value !!!");
            }
        }

        while (true) {
            System.out.println("Home/ProjectDeveloper/Exist. Enter developerId:");
            try {
                long developerId = Long.parseLong(scanner.nextLine());
                projectDeveloper.setDeveloperId(developerId);
                break;
            } catch (MustNotBeNull e) {
                System.out.println(e.getMessage());
            } catch (Exception ex) {
                System.out.println("!!! error. enter an integer value !!!");
            }
        }

        try {
            boolean result = new ProjectDeveloperDaoService(storage.getConnection()).exist(
                    projectDeveloper.getProjectId(),
                    projectDeveloper.getDeveloperId()
            );
            System.out.println(result);
        } catch (SQLException e) {
            System.out.println("!!! action completed with following error from database:");
            System.out.println(e.getMessage());
        }

        projectDeveloperInputLoop();
    }

    private void getAll() {
        try {
            List<ProjectDeveloper> all = new ProjectDeveloperDaoService(storage.getConnection()).getAll();
            System.out.println(all);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        projectDeveloperInputLoop();
    }

    private void getAllByProjectId() {
        ProjectDeveloper projectDeveloper = new ProjectDeveloper();

        while (true) {
            System.out.println("Home/ProjectDeveloper/getAllByProjectId. Enter projectId:");
            try {
                long projectId = Long.parseLong(scanner.nextLine());
                projectDeveloper.setProjectId(projectId);
                break;
            } catch (MustNotBeNull e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("!!! error. enter an integer value !!!");
            }
        }

        try {
            List<ProjectDeveloper> result = new ProjectDeveloperDaoService(storage.getConnection()).getAllByProjectId(
                    projectDeveloper.getProjectId());
            System.out.println(result);
        } catch (SQLException e) {
            System.out.println("!!! action completed with following error from database:");
            System.out.println(e.getMessage());
        }

        projectDeveloperInputLoop();
    }

    private void getAllByDeveloperId() {
        ProjectDeveloper projectDeveloper = new ProjectDeveloper();

        while (true) {
            System.out.println("Home/ProjectDeveloper/GetAllByDeveloperId. Enter developerId:");
            try {
                long developerId = Long.parseLong(scanner.nextLine());
                projectDeveloper.setDeveloperId(developerId);
                break;
            } catch (MustNotBeNull e) {
                System.out.println(e.getMessage());
            } catch (Exception ex) {
                System.out.println("!!! error. enter an integer value !!!");
            }
        }

        try {
            List<ProjectDeveloper> result = new ProjectDeveloperDaoService(storage.getConnection()).getAllByDeveloperId(
                    projectDeveloper.getDeveloperId()
            );
            System.out.println(result);
        } catch (SQLException e) {
            System.out.println("!!! action completed with following error from database:");
            System.out.println(e.getMessage());
        }

        projectDeveloperInputLoop();
    }

    private void update() {
        ProjectDeveloper old = new ProjectDeveloper();

        while (true) {
            System.out.println("Home/ProjectDeveloper/Update. Enter oldProjectId:");
            try {
                long projectId = Long.parseLong(scanner.nextLine());
                old.setProjectId(projectId);
                break;
            } catch (MustNotBeNull e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("!!! error. enter an integer value !!!");
            }
        }

        while (true) {
            System.out.println("Home/ProjectDeveloper/Update. Enter oldDeveloperId:");
            try {
                long developerId = Long.parseLong(scanner.nextLine());
                old.setDeveloperId(developerId);
                break;
            } catch (MustNotBeNull e) {
                System.out.println(e.getMessage());
            } catch (Exception ex) {
                System.out.println("!!! error. enter an integer value !!!");
            }
        }

        ProjectDeveloper update = new ProjectDeveloper();

        while (true) {
            System.out.println("Home/ProjectDeveloper/Update. Enter projectId:");
            try {
                long projectId = Long.parseLong(scanner.nextLine());
                update.setProjectId(projectId);
                break;
            } catch (MustNotBeNull e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("!!! error. enter an integer value !!!");
            }
        }

        while (true) {
            System.out.println("Home/ProjectDeveloper/Update. Enter developerId:");
            try {
                long developerId = Long.parseLong(scanner.nextLine());
                update.setDeveloperId(developerId);
                break;
            } catch (MustNotBeNull e) {
                System.out.println(e.getMessage());
            } catch (Exception ex) {
                System.out.println("!!! error. enter an integer value !!!");
            }
        }

        try {
            new ProjectDeveloperDaoService(storage.getConnection()).update(
                    old.getProjectId(),
                    old.getDeveloperId(),
                    update
            );
            System.out.println("--update completed successfully--");
        } catch (SQLException e) {
            System.out.println("!!! action completed with following error from database:");
            System.out.println(e.getMessage());
        }

        projectDeveloperInputLoop();
    }

    private void delete() {
        ProjectDeveloper projectDeveloper = new ProjectDeveloper();

        while (true) {
            System.out.println("Home/ProjectDeveloper/Delete. Enter projectId:");
            try {
                long projectId = Long.parseLong(scanner.nextLine());
                projectDeveloper.setProjectId(projectId);
                break;
            } catch (MustNotBeNull e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("!!! error. enter an integer value !!!");
            }
        }

        while (true) {
            System.out.println("Home/ProjectDeveloper/Delete. Enter developerId:");
            try {
                long developerId = Long.parseLong(scanner.nextLine());
                projectDeveloper.setDeveloperId(developerId);
                break;
            } catch (MustNotBeNull e) {
                System.out.println(e.getMessage());
            } catch (Exception ex) {
                System.out.println("!!! error. enter an integer value !!!");
            }
        }

        try {
            new ProjectDeveloperDaoService(storage.getConnection()).delete(projectDeveloper);
            System.out.println("--projectDeveloper record successfully deleted--");
        } catch (SQLException e) {
            System.out.println("!!! action completed with following error from database:");
            System.out.println(e.getMessage());
        }

        projectDeveloperInputLoop();
    }
}