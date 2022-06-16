package cli;

import storage.Storage;
import tables.company.Company;
import tables.company.CompanyDaoService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CompanyState extends CliState {
    private final Scanner scanner;
    private final CliState state;
    private final Storage storage;

    public CompanyState(CliFSM fsm) {
        super(fsm);
        scanner = fsm.getScanner();
        state = fsm.getState();
        storage = fsm.getStorage();
    }

    private final String path = "Home/Company";
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
        companyInputLoop();
    }

    private void companyInputLoop() throws SQLException {
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
        Company company = new Company();
        setName(company, create);
        setDescription(company, create);

        try {
            new CompanyDaoService(storage.getConnection()).create(company);
            System.out.println(true);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        companyInputLoop();
    }

    private void getById() throws SQLException {
        long id = setId(new Company(), getById).getId();
        try {
            Company byId = new CompanyDaoService(storage.getConnection()).getById(id);
            System.out.println(byId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        companyInputLoop();
    }

    private void getAll() throws SQLException {
        try {
            List<Company> all = new CompanyDaoService(storage.getConnection()).getAll();
            System.out.println(all);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        companyInputLoop();
    }

    private void update() throws SQLException {
        Company company = new Company();
        setId(company, update);
        setName(company, update);
        setDescription(company, update);

        try {
            new CompanyDaoService(storage.getConnection()).update(company);
            System.out.println(true);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        companyInputLoop();
    }

    private void deleteById() throws SQLException {
        long id = setId(new Company(), update).getId();
        try {
            new CompanyDaoService(storage.getConnection()).deleteById(id);
            System.out.println(true);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        companyInputLoop();
    }

    private Company setId(Company company, String nameCmd) {
        String navigation = path + "/" + nameCmd + ". Enter Id:";
        while (true) {
            System.out.println(navigation);
            try {
                long id = Long.parseLong(scanner.nextLine());
                company.setId(id);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return company;
    }

    private void setName(Company company, String nameCmd) {
        String navigation = path + "/" + nameCmd + ". Enter Name:";
        while (true) {
            System.out.println(navigation);
            String name =  scanner.nextLine();
            try {
                company.setName(name);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void setDescription(Company company, String nameCmd) {
        String navigation = path + "/" + nameCmd + ". Enter Description:";
        while (true) {
            System.out.println(navigation);
            String description =  scanner.nextLine();
            try {
                company.setDescription(description);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}