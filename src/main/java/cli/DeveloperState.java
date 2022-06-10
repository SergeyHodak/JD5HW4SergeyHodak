package cli;

public class DeveloperState extends CliState {
    public DeveloperState(CliFSM fsm) {
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
        System.out.println("Developer table. Введіть команду:");
    }
}