package cli;

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

    private final String path = "Home/Customer";
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
        customerInputLoop();
    }

    private void customerInputLoop() throws SQLException {
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
        Customer customer = new Customer();
        setFirstName(customer, create);
        setSecondName(customer, create);
        setAge(customer, create);

        try {
            new CustomerDaoService(storage.getConnection()).create(customer);
            System.out.println(true);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        customerInputLoop();
    }

    private void getById() throws SQLException {
        long id = setId(new Customer(), getById).getId();
        try {
            Customer byId = new CustomerDaoService(storage.getConnection()).getById(id);
            System.out.println(byId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        customerInputLoop();
    }

    private void getAll() throws SQLException {
        try {
            List<Customer> all = new CustomerDaoService(storage.getConnection()).getAll();
            System.out.println(all);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        customerInputLoop();
    }

    private void update() throws SQLException {
        Customer customer = new Customer();
        setId(customer, update);
        setFirstName(customer, update);
        setSecondName(customer, update);
        setAge(customer, update);

        try {
            new CustomerDaoService(storage.getConnection()).update(customer);
            System.out.println(true);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        customerInputLoop();
    }

    private void deleteById() throws SQLException {
        long id = setId(new Customer(), deleteById).getId();
        try {
            new CustomerDaoService(storage.getConnection()).deleteById(id);
            System.out.println(true);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        customerInputLoop();
    }

    public Customer setId(Customer customer, String nameCmd) {
        String navigation = path + "/" + nameCmd + ". Enter Id:";
        while (true) {
            System.out.println(navigation);
            try {
                long id = Long.parseLong(scanner.nextLine());
                customer.setId(id);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return customer;
    }

    private void setFirstName(Customer customer, String nameCmd) {
        String navigation = path + "/" + nameCmd + ". Enter FirstName:";
        while (true) {
            System.out.println(navigation);
            String firstName =  scanner.nextLine();
            try {
                customer.setFirstName(firstName);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void setSecondName(Customer customer, String nameCmd) {
        String navigation = path + "/" + nameCmd + ". Enter SecondName:";
        while (true) {
            System.out.println(navigation);
            String secondName =  scanner.nextLine();
            try {
                customer.setSecondName(secondName);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void setAge(Customer customer, String nameCmd) {
        String navigation = path + "/" + nameCmd + ". Enter Age:";
        while (true) {
            System.out.println(navigation);
            try {
                int age = Integer.parseInt(scanner.nextLine());
                customer.setAge(age);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}