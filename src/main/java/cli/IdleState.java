package cli;

import java.sql.SQLException;

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
    public void company() throws SQLException {
        fsm.setState(new CompanyState(fsm));
    }

    @Override
    public void customer() throws SQLException {
        fsm.setState(new CustomerState(fsm));
    }

    @Override
    public void project() throws SQLException {
        fsm.setState(new ProjectState(fsm));
    }

    @Override
    public void developer() throws SQLException {
        fsm.setState(new DeveloperState(fsm));
    }

    @Override
    public void projectDeveloper() throws SQLException {
        fsm.setState(new ProjectDeveloperState(fsm));
    }

    @Override
    public void skill() throws SQLException {
        fsm.setState(new SkillState(fsm));
    }

    @Override
    public void developerSkill() throws SQLException {
        fsm.setState(new DeveloperSkillState(fsm));
    }
}