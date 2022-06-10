package cli;

public class CustomerState extends CliState {
    public CustomerState(CliFSM fsm) {
        super(fsm);
    }

    @Override
    public void init() {
        //back
        //exit

        //create
        //getById
        //clear
        //getAll
        //update
        //deleteById
        System.out.println("Customer table. Введіть команду:");
    }
}