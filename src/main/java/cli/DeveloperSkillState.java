package cli;

import exceptions.MustNotBeNull;
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
    public void init() {
        developerSkillInputLoop();
    }

    private void developerSkillInputLoop() {
        String command = "";

        boolean status = true;
        while (status) {
            System.out.println("Home/DeveloperSkill. Enter command:");

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
    public void idleState() {
        new CliFSM(storage);
    }

    private void create() {
        DeveloperSkill developerSkill = new DeveloperSkill();

        while (true) {
            System.out.println("Home/DeveloperSkill/Create. Enter developerId:");
            String developerId = scanner.nextLine();
            try {
                long id = Long.parseLong(developerId);
                developerSkill.setDeveloperId(id);
                break;
            } catch (MustNotBeNull e) {
                System.out.println(e.getMessage());
            } catch (Exception ex) {
                System.out.println("!!! error. enter an integer value !!!");
            }
        }

        while (true) {
            System.out.println("Home/DeveloperSkill/Create. Enter skillId:");
            String skillId = scanner.nextLine();
            try {
                long id = Long.parseLong(skillId);
                developerSkill.setSkillId(id);
                break;
            } catch (MustNotBeNull e) {
                System.out.println(e.getMessage());
            } catch (Exception ex) {
                System.out.println("!!! error. enter an integer value !!!");
            }
        }

        try {
            new DeveloperSkillDaoService(storage.getConnection()).create(developerSkill);
            System.out.println("--new developerSkill creation completed successfully--");
        } catch (SQLException e) {
            System.out.println("!!! new developerSkill creation completed with following ERROR from database:");
            System.out.println(e.getMessage());
        }

        developerSkillInputLoop();
    }

    private void exist() {
        DeveloperSkill developerSkill = new DeveloperSkill();

        while (true) {
            System.out.println("Home/DeveloperSkill/Exist. Enter developerId:");
            try {
                long developerId = Long.parseLong(scanner.nextLine());
                developerSkill.setDeveloperId(developerId);
                break;
            } catch (MustNotBeNull e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("!!! error. enter an integer value !!!");
            }
        }

        while (true) {
            System.out.println("Home/DeveloperSkill/Exist. Enter skillId:");
            try {
                long skillId = Long.parseLong(scanner.nextLine());
                developerSkill.setSkillId(skillId);
                break;
            } catch (MustNotBeNull e) {
                System.out.println(e.getMessage());
            } catch (Exception ex) {
                System.out.println("!!! error. enter an integer value !!!");
            }
        }

        try {
            boolean result = new DeveloperSkillDaoService(storage.getConnection()).exist(
                    developerSkill.getDeveloperId(),
                    developerSkill.getSkillId()
            );
            System.out.println(result);
        } catch (SQLException e) {
            System.out.println("!!! action completed with following error from database:");
            System.out.println(e.getMessage());
        }

        developerSkillInputLoop();
    }

    private void getAll() {
        try {
            List<DeveloperSkill> all = new DeveloperSkillDaoService(storage.getConnection()).getAll();
            System.out.println(all);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        developerSkillInputLoop();
    }

    private void getAllByDeveloperId() {
        DeveloperSkill developerSkill = new DeveloperSkill();

        while (true) {
            System.out.println("Home/DeveloperSkill/GetAllByDeveloperId. Enter developerId:");
            try {
                long developerId = Long.parseLong(scanner.nextLine());
                developerSkill.setDeveloperId(developerId);
                break;
            } catch (MustNotBeNull e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("!!! error. enter an integer value !!!");
            }
        }

        try {
            List<DeveloperSkill> result = new DeveloperSkillDaoService(storage.getConnection()).getAllByDeveloperId(
                    developerSkill.getDeveloperId());
            System.out.println(result);
        } catch (SQLException e) {
            System.out.println("!!! action completed with following error from database:");
            System.out.println(e.getMessage());
        }

        developerSkillInputLoop();
    }

    private void getAllBySkillId() {
        DeveloperSkill developerSkill = new DeveloperSkill();

        while (true) {
            System.out.println("Home/DeveloperSkill/GetAllBySkillId. Enter skillId:");
            try {
                long skillId = Long.parseLong(scanner.nextLine());
                developerSkill.setSkillId(skillId);
                break;
            } catch (MustNotBeNull e) {
                System.out.println(e.getMessage());
            } catch (Exception ex) {
                System.out.println("!!! error. enter an integer value !!!");
            }
        }

        try {
            List<DeveloperSkill> result = new DeveloperSkillDaoService(storage.getConnection()).getAllBySkillId(
                    developerSkill.getSkillId()
            );
            System.out.println(result);
        } catch (SQLException e) {
            System.out.println("!!! action completed with following error from database:");
            System.out.println(e.getMessage());
        }

        developerSkillInputLoop();
    }

    private void update() {
        DeveloperSkill old = new DeveloperSkill();

        while (true) {
            System.out.println("Home/DeveloperSkill/Update. Enter oldDeveloperId:");
            try {
                long developerId = Long.parseLong(scanner.nextLine());
                old.setDeveloperId(developerId);
                break;
            } catch (MustNotBeNull e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("!!! error. enter an integer value !!!");
            }
        }

        while (true) {
            System.out.println("Home/DeveloperSkill/Update. Enter oldSkillId:");
            try {
                long skillId = Long.parseLong(scanner.nextLine());
                old.setSkillId(skillId);
                break;
            } catch (MustNotBeNull e) {
                System.out.println(e.getMessage());
            } catch (Exception ex) {
                System.out.println("!!! error. enter an integer value !!!");
            }
        }

        DeveloperSkill update = new DeveloperSkill();

        while (true) {
            System.out.println("Home/DeveloperSkill/Update. Enter DeveloperId:");
            try {
                long developerId = Long.parseLong(scanner.nextLine());
                update.setDeveloperId(developerId);
                break;
            } catch (MustNotBeNull e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("!!! error. enter an integer value !!!");
            }
        }

        while (true) {
            System.out.println("Home/DeveloperSkill/Update. Enter SkillId:");
            try {
                long skillId = Long.parseLong(scanner.nextLine());
                update.setSkillId(skillId);
                break;
            } catch (MustNotBeNull e) {
                System.out.println(e.getMessage());
            } catch (Exception ex) {
                System.out.println("!!! error. enter an integer value !!!");
            }
        }

        try {
            new DeveloperSkillDaoService(storage.getConnection()).update(
                    old.getDeveloperId(),
                    old.getSkillId(),
                    update
            );
            System.out.println("--update completed successfully--");
        } catch (SQLException e) {
            System.out.println("!!! action completed with following error from database:");
            System.out.println(e.getMessage());
        }

        developerSkillInputLoop();
    }

    private void delete() {
        DeveloperSkill developerSkill = new DeveloperSkill();

        while (true) {
            System.out.println("Home/DeveloperSkill/Delete. Enter DeveloperId:");
            try {
                long developerId = Long.parseLong(scanner.nextLine());
                developerSkill.setDeveloperId(developerId);
                break;
            } catch (MustNotBeNull e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("!!! error. enter an integer value !!!");
            }
        }

        while (true) {
            System.out.println("Home/DeveloperSkill/Delete. Enter SkillId:");
            try {
                long skillId = Long.parseLong(scanner.nextLine());
                developerSkill.setSkillId(skillId);
                break;
            } catch (MustNotBeNull e) {
                System.out.println(e.getMessage());
            } catch (Exception ex) {
                System.out.println("!!! error. enter an integer value !!!");
            }
        }

        try {
            new DeveloperSkillDaoService(storage.getConnection()).delete(developerSkill);
            System.out.println("--developerSkill record successfully deleted--");
        } catch (SQLException e) {
            System.out.println("!!! action completed with following error from database:");
            System.out.println(e.getMessage());
        }

        developerSkillInputLoop();
    }
}