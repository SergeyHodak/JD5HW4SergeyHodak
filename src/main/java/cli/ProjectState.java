package cli;

import storage.Storage;
import tables.developer.Developer;
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

    private final String path = "Home/Project";
    private final String exit = "exit";
    private final String show = "show";
    private final String back = "back";
    private final String create = "create";
    private final String getById = "getById";
    private final String getAll = "getAll";
    private final String update = "update";
    private final String deleteById = "deleteById";
    private final String getCostById = "getCostById";
    private final String updateCostById = "updateCostById";
    private final String getDevelopersByProjectId = "getDevelopersByProjectId";
    private final String getAllBySpecialFormat = "getAllBySpecialFormat";

    List<String> availableCmd = List.of(
            exit,
            show,
            back,
            create,
            getById,
            getAll,
            update,
            deleteById,
            getCostById,
            updateCostById,
            getDevelopersByProjectId,
            getAllBySpecialFormat
    );

    @Override
    public void init() throws SQLException {
        projectInputLoop();
    }

    private void projectInputLoop() throws SQLException {
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
            case getCostById: {
                getCostById();
                break;
            }
            case updateCostById: {
                updateCostById();
                break;
            }
            case getDevelopersByProjectId: {
                getDevelopersByProjectId();
                break;
            }
            case getAllBySpecialFormat: {
                getAllBySpecialFormat();
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
        Project project = new Project();
        setName(project, create);
        setCompanyId(project, create);
        setCustomerId(project, create);
        setCreationDate(project, create);

        try {
            new ProjectDaoService(storage.getConnection()).create(project);
            System.out.println(true);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        projectInputLoop();
    }

    private void getById() throws SQLException {
        long id = setId(new Project(), getById).getId();
        try {
            Project byId = new ProjectDaoService(storage.getConnection()).getById(id);
            System.out.println(byId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        projectInputLoop();
    }

    private void getAll() throws SQLException {
        try {
            List<Project> all = new ProjectDaoService(storage.getConnection()).getAll();
            System.out.println(all);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        projectInputLoop();
    }

    private void update() throws SQLException {
        Project project = new Project();
        setId(project, update);
        setName(project, update);
        setCompanyId(project, update);
        setCustomerId(project, update);
        setCreationDate(project, update);

        try {
            new ProjectDaoService(storage.getConnection()).update(project);
            System.out.println(true);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        projectInputLoop();
    }

    private void deleteById() throws SQLException {
        long id = setId(new Project(), deleteById).getId();
        try {
            new ProjectDaoService(storage.getConnection()).deleteById(id);
            System.out.println(true);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        projectInputLoop();
    }

    private void getCostById() throws SQLException {
        long id = setId(new Project(), getCostById).getId();
        try {
            double costById = new ProjectDaoService(storage.getConnection()).getCostById(id);
            System.out.println(costById);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        projectInputLoop();
    }

    private void updateCostById() throws SQLException {
        long id = setId(new Project(), updateCostById).getId();
        try {
            new ProjectDaoService(storage.getConnection()).updateCostById(id);
            System.out.println(true);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        projectInputLoop();
    }

    private void getDevelopersByProjectId() throws SQLException {
        long id = setId(new Project(), getDevelopersByProjectId).getId();
        try {
            List<Developer> developersByProjectId = new ProjectDaoService(storage.getConnection()).getDevelopersByProjectId(id);
            System.out.println(developersByProjectId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        projectInputLoop();
    }

    private void getAllBySpecialFormat() throws SQLException {
        List<ProjectDaoService.project> allBySpecialFormat = new ProjectDaoService(storage.getConnection())
                .getAllBySpecialFormat();
        System.out.println(allBySpecialFormat);

        projectInputLoop();
    }

    private Project setId(Project project, String nameCmd) {
        String navigation = path + "/" + nameCmd + ". Enter Id:";
        while (true) {
            System.out.println(navigation);
            try {
                long id = Long.parseLong(scanner.nextLine());
                project.setId(id);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return project;
    }

    private void setName(Project project, String nameCmd) {
        String navigation = path + "/" + nameCmd + ". Enter Name:";
        while (true) {
            System.out.println(navigation);
            String name = scanner.nextLine();
            try {
                project.setName(name);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void setCompanyId(Project project, String nameCmd) {
        String navigation = path + "/" + nameCmd + ". Enter CompanyId:";
        while (true) {
            System.out.println(navigation);
            try {
                long companyId = Long.parseLong(scanner.nextLine());
                project.setCompanyId(companyId);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void setCustomerId(Project project, String nameCmd) {
        String navigation = path + "/" + nameCmd + ". Enter CustomerId:";
        while (true) {
            System.out.println(navigation);
            try {
                long customerId = Long.parseLong(scanner.nextLine());
                project.setCustomerId(customerId);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void setCreationDate(Project project, String nameCmd) {
        String navigation = path + "/" + nameCmd + ". Enter CreationDate:";
        while (true) {
            System.out.println(navigation);
            try {
                LocalDate creationDate = LocalDate.parse(scanner.nextLine());
                project.setCreationDate(creationDate);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("date format = year-month-day. For example: 2022-06-07");
            }
        }
    }
}