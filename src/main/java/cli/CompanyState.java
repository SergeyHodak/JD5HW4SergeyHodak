package cli;

import exceptions.NumberOfCharactersExceedsTheLimit;
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
        while (status) {
            System.out.println("Home/Company. Enter command:");

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

        while (true) {
            System.out.println("Home/Company/Create. Enter name:");
            String name =  scanner.nextLine();
            try {
                company.setName(name);
                break;
            } catch (NumberOfCharactersExceedsTheLimit e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            System.out.println("Home/Company/Create. Enter description:");
            String description =  scanner.nextLine();
            try {
                company.setDescription(description);
                break;
            } catch (NumberOfCharactersExceedsTheLimit e) {
                System.out.println(e.getMessage());
            }
        }

        try {
            new CompanyDaoService(storage.getConnection()).create(company);
            System.out.println("--new company creation completed successfully--");
        } catch (SQLException e) {
            System.out.println("!!! new company creation completed with following ERROR from database:");
            System.out.println(e.getMessage());
        }

        companyInputLoop();
    }

    private void getById() throws SQLException {
        int id;

        while (true) {
            System.out.println("Home/Company/GetById. Enter id:");
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
            Company byId = new CompanyDaoService(storage.getConnection()).getById(id);
            System.out.println(byId);
        } catch (SQLException e) {
            System.out.println("!!! action completed with following error from database:");
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

        while (true) {
            System.out.println("Home/Company/Update. Enter id:");
            try {
                int id = Integer.parseInt(scanner.nextLine());
                if (id <= 0) {
                    System.out.println("!!! enter id with a value greater than zero !!!");
                } else {
                    company.setId(id);
                    break;
                }
            } catch (Exception e) {
                System.out.println("!!! error. enter an integer value !!!");
            }
        }

        while (true) {
            System.out.println("Home/Company/Update. Enter name:");
            String name =  scanner.nextLine();
            try {
                company.setName(name);
                break;
            } catch (NumberOfCharactersExceedsTheLimit e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            System.out.println("Home/Company/Update. Enter description:");
            String description =  scanner.nextLine();
            try {
                company.setDescription(description);
                break;
            } catch (NumberOfCharactersExceedsTheLimit e) {
                System.out.println(e.getMessage());
            }
        }

        try {
            new CompanyDaoService(storage.getConnection()).update(company);
            System.out.println("--company update, completed successfully--");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        companyInputLoop();
    }

    private void deleteById() throws SQLException {
        int id;

        while (true) {
            System.out.println("Home/Company/DeleteById. Enter id:");
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
            new CompanyDaoService(storage.getConnection()).deleteById(id);
            System.out.println("--company record successfully deleted--");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        companyInputLoop();
    }
}