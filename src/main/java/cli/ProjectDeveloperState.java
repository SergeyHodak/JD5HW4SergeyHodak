package cli;

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

    private final String path = "Home/ProjectDeveloper";
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
    public void init() throws SQLException {
        projectDeveloperInputLoop();
    }

    private void projectDeveloperInputLoop() throws SQLException {
        String command = "";
        boolean status = true;
        String navigation = path + ". Enter command:";
        while (status) {
            System.out.println(navigation);
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
    public void idleState() throws SQLException {
        new CliFSM(storage);
    }

    private void create() throws SQLException {
        ProjectDeveloper projectDeveloper = new ProjectDeveloper();
        setProjectId(projectDeveloper, create, "projectId");
        setDeveloperId(projectDeveloper, create, "developerId");

        try {
            new ProjectDeveloperDaoService(storage.getConnection()).create(projectDeveloper);
            System.out.println(true);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        projectDeveloperInputLoop();
    }

    private void exist() throws SQLException {
        ProjectDeveloper projectDeveloper = new ProjectDeveloper();
        setProjectId(projectDeveloper, exist, "projectId");
        setDeveloperId(projectDeveloper, exist, "developerId");

        try {
            boolean result = new ProjectDeveloperDaoService(storage.getConnection()).exist(
                    projectDeveloper.getProjectId(),
                    projectDeveloper.getDeveloperId()
            );
            System.out.println(result);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        projectDeveloperInputLoop();
    }

    private void getAll() throws SQLException {
        try {
            List<ProjectDeveloper> all = new ProjectDeveloperDaoService(storage.getConnection()).getAll();
            System.out.println(all);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        projectDeveloperInputLoop();
    }

    private void getAllByProjectId() throws SQLException {
        ProjectDeveloper projectDeveloper = new ProjectDeveloper();
        setProjectId(projectDeveloper, getAllByProjectId, "projectId");

        try {
            List<ProjectDeveloper> result = new ProjectDeveloperDaoService(storage.getConnection()).getAllByProjectId(
                    projectDeveloper.getProjectId());
            System.out.println(result);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        projectDeveloperInputLoop();
    }

    private void getAllByDeveloperId() throws SQLException {
        ProjectDeveloper projectDeveloper = new ProjectDeveloper();
        setDeveloperId(projectDeveloper, getAllByDeveloperId, "developerId");

        try {
            List<ProjectDeveloper> result = new ProjectDeveloperDaoService(storage.getConnection()).getAllByDeveloperId(
                    projectDeveloper.getDeveloperId()
            );
            System.out.println(result);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        projectDeveloperInputLoop();
    }

    private void update() throws SQLException {
        ProjectDeveloper old = new ProjectDeveloper();
        setProjectId(old, update, "oldProjectId");
        setDeveloperId(old, update, "oldDeveloperId");

        ProjectDeveloper updates = new ProjectDeveloper();
        setProjectId(updates, update, "projectId");
        setDeveloperId(updates, update, "developerId");

        try {
            new ProjectDeveloperDaoService(storage.getConnection()).update(
                    old.getProjectId(),
                    old.getDeveloperId(),
                    updates
            );
            System.out.println(true);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        projectDeveloperInputLoop();
    }

    private void delete() throws SQLException {
        ProjectDeveloper projectDeveloper = new ProjectDeveloper();
        setProjectId(projectDeveloper, delete, "projectId");
        setDeveloperId(projectDeveloper, delete, "developerId");

        try {
            new ProjectDeveloperDaoService(storage.getConnection()).delete(projectDeveloper);
            System.out.println(true);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        projectDeveloperInputLoop();
    }

    private void setProjectId(ProjectDeveloper projectDeveloper, String nameCmd, String whatWeEnter) {
        String navigation = path + "/" + nameCmd + ". Enter " + whatWeEnter +":";
        while (true) {
            System.out.println(navigation);
            try {
                long projectId = Long.parseLong(scanner.nextLine());
                projectDeveloper.setProjectId(projectId);
                break;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void setDeveloperId(ProjectDeveloper projectDeveloper, String nameCmd, String whatWeEnter) {
        String navigation = path + "/" + nameCmd + ". Enter " + whatWeEnter +":";
        while (true) {
            System.out.println(navigation);
            try {
                long developerId = Long.parseLong(scanner.nextLine());
                projectDeveloper.setDeveloperId(developerId);
                break;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}