package cli;

public class IdleState extends CliState {
    public IdleState(CliFSM fsm) {
        super(fsm);
    }

    @Override
    public void unknownCommand(String cmd) {
        System.out.println("Unknown command: " + cmd);
        System.out.println("To see the available commands type <show>.");
    }

    @Override
    public void company() {
        fsm.setState(new CompanyState(fsm));
    }

    @Override
    public void customer() {
        fsm.setState(new CustomerState(fsm));
    }

    @Override
    public void project() {
        fsm.setState(new ProjectState(fsm));
    }

    @Override
    public void developer() {
        fsm.setState(new DeveloperState(fsm));
    }

    @Override
    public void projectDeveloper() {
        fsm.setState(new ProjectDeveloperState(fsm));
    }

    @Override
    public void skill() {
        fsm.setState(new SkillState(fsm));
    }

    @Override
    public void developerSkill() {
        fsm.setState(new DeveloperSkillState(fsm));
    }
}