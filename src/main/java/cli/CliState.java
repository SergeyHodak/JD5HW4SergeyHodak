package cli;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CliState {
    protected final CliFSM fsm;

    public void init() {}
    public void company() {}
    public void customer() {}
    public void project() {}
    public void developer() {}
    public void projectDeveloper() {}
    public void skill() {}
    public void developerSkill() {}
    public void unknownCommand(String cmd) {}
    public void idleState() {}
}