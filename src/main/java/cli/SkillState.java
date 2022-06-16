package cli;

import storage.Storage;
import tables.skill.Skill;
import tables.skill.SkillDaoService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class SkillState extends CliState {
    private final Scanner scanner;
    private final CliState state;
    private final Storage storage;

    public SkillState(CliFSM fsm) {
        super(fsm);
        scanner = fsm.getScanner();
        state = fsm.getState();
        storage = fsm.getStorage();
    }

    private final String path = "Home/Skill";
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
    public void init() throws SQLException {
        skillInputLoop();
    }

    private void skillInputLoop() throws SQLException {
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
        Skill skill = new Skill();
        setDepartment(skill, create);
        setSkillLevel(skill, create);

        try {
            new SkillDaoService(storage.getConnection()).create(skill);
            System.out.println(true);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        skillInputLoop();
    }

    private void getById() throws SQLException {
        long id = setId(new Skill(), getById).getId();
        try {
            Skill byId = new SkillDaoService(storage.getConnection()).getById(id);
            System.out.println(byId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        skillInputLoop();
    }

    private void getAll() throws SQLException {
        try {
            List<Skill> all = new SkillDaoService(storage.getConnection()).getAll();
            System.out.println(all);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        skillInputLoop();
    }

    private void update() throws SQLException {
        Skill skill = new Skill();
        setId(skill, update);
        setDepartment(skill, update);
        setSkillLevel(skill, update);

        try {
            new SkillDaoService(storage.getConnection()).update(skill);
            System.out.println(true);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        skillInputLoop();
    }

    private void deleteById() throws SQLException {
        long id = setId(new Skill(), deleteById).getId();
        try {
            new SkillDaoService(storage.getConnection()).deleteById(id);
            System.out.println("--skill record successfully deleted--");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        skillInputLoop();
    }

    private Skill setId(Skill skill, String nameCmd) {
        String navigation = path + "/" + nameCmd + ". Enter Id:";
        while (true) {
            System.out.println(navigation);
            try {
                long id = Long.parseLong(scanner.nextLine());
                skill.setId(id);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return skill;
    }

    private void setDepartment(Skill skill, String nameCmd) {
        String navigation = path + "/" + nameCmd + ". Enter Department:";
        while (true) {
            System.out.println(navigation);
            String department = scanner.nextLine();
            try {
                skill.setDepartment(department);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void setSkillLevel(Skill skill, String nameCmd) {
        String navigation = path + "/" + nameCmd + ". Enter SkillLevel:";
        while (true) {
            System.out.println(navigation);
            String skillLevel = scanner.nextLine();
            try {
                skill.setSkillLevel(skillLevel);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}