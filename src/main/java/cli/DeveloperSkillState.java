package cli;

import storage.Storage;
import tables.developer_skill.DeveloperSkill;
import tables.developer_skill.DeveloperSkillDaoService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class DeveloperSkillState extends CliState {
    private final Scanner scanner;
    private final CliState state;
    private final Storage storage;

    public DeveloperSkillState(CliFSM fsm) {
        super(fsm);
        scanner = fsm.getScanner();
        state = fsm.getState();
        storage = fsm.getStorage();
    }

    private final String path = "Home/DeveloperSkill";
    private final String exit = "exit";
    private final String show = "show";
    private final String back = "back";
    private final String create = "create";
    private final String exist = "exist";
    private final String getAll = "getAll";
    private final String getAllByDeveloperId = "getAllByDeveloperId";
    private final String getAllBySkillId = "getAllBySkillId";
    private final String update = "update";
    private final String delete = "delete";

    List<String> availableCmd = List.of(
            exit,
            show,
            back,
            create,
            exist,
            getAll,
            getAllByDeveloperId,
            getAllBySkillId,
            update,
            delete
    );

    @Override
    public void init() throws SQLException {
        developerSkillInputLoop();
    }

    private void developerSkillInputLoop() throws SQLException {
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
            case getAllByDeveloperId: {
                getAllByDeveloperId();
                break;
            }
            case getAllBySkillId: {
                getAllBySkillId();
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
        DeveloperSkill developerSkill = new DeveloperSkill();
        setDeveloperId(developerSkill, create, "DeveloperId");
        setSkillId(developerSkill, create, "skillId");

        try {
            new DeveloperSkillDaoService(storage.getConnection()).create(developerSkill);
            System.out.println(true);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        developerSkillInputLoop();
    }

    private void exist() throws SQLException {
        DeveloperSkill developerSkill = new DeveloperSkill();
        setDeveloperId(developerSkill, exist, "DeveloperId");
        setSkillId(developerSkill, exist, "skillId");

        try {
            boolean result = new DeveloperSkillDaoService(storage.getConnection()).exist(
                    developerSkill.getDeveloperId(),
                    developerSkill.getSkillId()
            );
            System.out.println(result);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        developerSkillInputLoop();
    }

    private void getAll() throws SQLException {
        try {
            List<DeveloperSkill> all = new DeveloperSkillDaoService(storage.getConnection()).getAll();
            System.out.println(all);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        developerSkillInputLoop();
    }

    private void getAllByDeveloperId() throws SQLException {
        DeveloperSkill developerSkill = new DeveloperSkill();
        setDeveloperId(developerSkill, getAllByDeveloperId, "DeveloperId");

        try {
            List<DeveloperSkill> result = new DeveloperSkillDaoService(storage.getConnection()).getAllByDeveloperId(
                    developerSkill.getDeveloperId());
            System.out.println(result);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        developerSkillInputLoop();
    }

    private void getAllBySkillId() throws SQLException {
        DeveloperSkill developerSkill = new DeveloperSkill();
        setSkillId(developerSkill, getAllBySkillId, "skillId");

        try {
            List<DeveloperSkill> result = new DeveloperSkillDaoService(storage.getConnection()).getAllBySkillId(
                    developerSkill.getSkillId()
            );
            System.out.println(result);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        developerSkillInputLoop();
    }

    private void update() throws SQLException {
        DeveloperSkill old = new DeveloperSkill();
        setDeveloperId(old, update, "oldDeveloperId");
        setSkillId(old, update, "oldSkillId");

        DeveloperSkill updates = new DeveloperSkill();
        setDeveloperId(updates, update, "DeveloperId");
        setSkillId(updates, update, "SkillId");

        try {
            new DeveloperSkillDaoService(storage.getConnection()).update(
                    old.getDeveloperId(),
                    old.getSkillId(),
                    updates
            );
            System.out.println(true);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        developerSkillInputLoop();
    }

    private void delete() throws SQLException {
        DeveloperSkill developerSkill = new DeveloperSkill();
        setDeveloperId(developerSkill, delete, "DeveloperId");
        setSkillId(developerSkill, delete, "SkillId");

        try {
            new DeveloperSkillDaoService(storage.getConnection()).delete(developerSkill);
            System.out.println(true);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        developerSkillInputLoop();
    }

    private void setDeveloperId(DeveloperSkill developerSkill, String nameCmd, String whatWeEnter) {
        String navigation = path + "/" + nameCmd + ". Enter " + whatWeEnter +":";
        while (true) {
            System.out.println(navigation);
            try {
                long developerId = Long.parseLong(scanner.nextLine());
                developerSkill.setDeveloperId(developerId);
                break;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void setSkillId(DeveloperSkill developerSkill, String nameCmd, String whatWeEnter) {
        String navigation = path + "/" + nameCmd + ". Enter " + whatWeEnter +":";
        while (true) {
            System.out.println(navigation);
            try {
                long SkillId = Long.parseLong(scanner.nextLine());
                developerSkill.setSkillId(SkillId);
                break;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}