package cli;

import exceptions.AgeOutOfRange;
import exceptions.NumberOfCharactersExceedsTheLimit;
import storage.Storage;
import tables.customer.Customer;
import tables.customer.CustomerDaoService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CustomerState extends CliState {
    private final Scanner scanner;
    private final CliState state;
    private final Storage storage;

    public CustomerState(CliFSM fsm) {
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
        customerInputLoop();
    }

    private void customerInputLoop() {
        String command = "";

        boolean status = true;
        while (status) {
            System.out.println("Home/Customer. Enter command:");

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
    public void idleState() {
        new CliFSM(storage);
    }

    private void create() {
        Customer customer = new Customer();

        while (true) {
            System.out.println("Home/Customer/Create. Enter firstName:");
            String firstName =  scanner.nextLine();
            try {
                customer.setFirstName(firstName);
                break;
            } catch (NumberOfCharactersExceedsTheLimit e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            System.out.println("Home/Customer/Create. Enter secondName:");
            String secondName =  scanner.nextLine();
            try {
                customer.setSecondName(secondName);
                break;
            } catch (NumberOfCharactersExceedsTheLimit e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            System.out.println("Home/Customer/Create. Enter age:");

            try {
                int age = Integer.parseInt(scanner.nextLine());
                customer.setAge(age);
                break;
            } catch (AgeOutOfRange e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("!!! error. enter an integer value !!!");
            }
        }

        try {
            new CustomerDaoService(storage.getConnection()).create(customer);
            System.out.println("--new customer creation completed successfully--");
        } catch (SQLException e) {
            System.out.println("!!! new customer creation completed with following ERROR from database:");
            System.out.println(e.getMessage());
        }

        customerInputLoop();
    }

    private void getById() {
        int id;

        while (true) {
            System.out.println("Home/Customer/GetById. Enter id:");
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
            Customer byId = new CustomerDaoService(storage.getConnection()).getById(id);
            System.out.println(byId);
        } catch (SQLException e) {
            System.out.println("!!! action completed with following error from database:");
            System.out.println(e.getMessage());
        }

        customerInputLoop();
    }

    private void getAll() {
        try {
            List<Customer> all = new CustomerDaoService(storage.getConnection()).getAll();
            System.out.println(all);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        customerInputLoop();
    }

    private void update() {
        Customer customer = new Customer();

        while (true) {
            System.out.println("Home/Customer/Update. Enter id:");
            try {
                int id = Integer.parseInt(scanner.nextLine());
                if (id <= 0) {
                    System.out.println("!!! enter id with a value greater than zero !!!");
                } else {
                    customer.setId(id);
                    break;
                }
            } catch (Exception e) {
                System.out.println("!!! error. enter an integer value !!!");
            }
        }

        while (true) {
            System.out.println("Home/Customer/Update. Enter firstName:");
            String firstName =  scanner.nextLine();
            try {
                customer.setFirstName(firstName);
                break;
            } catch (NumberOfCharactersExceedsTheLimit e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            System.out.println("Home/Customer/Update. Enter secondName:");
            String secondName =  scanner.nextLine();
            try {
                customer.setSecondName(secondName);
                break;
            } catch (NumberOfCharactersExceedsTheLimit e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            System.out.println("Home/Customer/Update. Enter age:");
            try {
                int age = Integer.parseInt(scanner.nextLine());
                customer.setAge(age);
                break;
            } catch (AgeOutOfRange e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("!!! error. enter an integer value !!!");
            }
        }

        try {
            new CustomerDaoService(storage.getConnection()).update(customer);
            System.out.println("--customer update, completed successfully--");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        customerInputLoop();
    }

    private void deleteById() {
        int id;

        while (true) {
            System.out.println("Home/Customer/DeleteById. Enter id:");
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
            new CustomerDaoService(storage.getConnection()).deleteById(id);
            System.out.println("--customer record successfully deleted--");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        customerInputLoop();
    }
}