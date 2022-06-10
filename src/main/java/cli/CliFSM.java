package cli;

import lombok.Getter;
import storage.Storage;

import java.util.List;
import java.util.Scanner;

public class CliFSM {
    @Getter
    private CliState state;
    @Getter
    private final Scanner scanner;
    @Getter
    private final Storage storage;

    private final String exit = "exit";
    private final String show = "show";
    private final String company = "company";
    private final String customer = "customer";
    private final String project = "project";
    private final String developer = "developer";
    private final String projectDeveloper = "projectDeveloper";
    private final String skill = "skill";
    private final String developerSkill = "developerSkill";

    List<String> availableCmd = List.of(
            exit,
            show,
            company,
            customer,
            project,
            developer,
            projectDeveloper,
            skill,
            developerSkill
    );

    public CliFSM(Storage storage) {
        this.storage = storage;
        state = new IdleState(this);
        scanner = new Scanner(System.in);
        startInputLoop();
    }

    public void startInputLoop() {
        String command = "";

        while (true) {
            System.out.println("Home. Enter command:");

            command = scanner.nextLine();

            if (availableCmd.contains(command)) {
                if ("show".equals(command)) {
                    System.out.println(availableCmd);
                } else if ("exit".equals(command)) {
                    System.exit(0);
                } else {
                    break;
                }
            } else {
                unknownCommand(command);
            }
        }

        switch (command) {
            case company: {
                company();
                break;
            }
            case customer: {
                customer();
                break;
            }
            case project: {
                project();
                break;
            }
            case developer: {
                developer();
                break;
            }
            case projectDeveloper: {
                projectDeveloper();
                break;
            }
            case skill: {
                skill();
                break;
            }
            case developerSkill: {
                developerSkill();
                break;
            }
        }
    }

    public void company() {
        state.company();
    }

    public void customer() {
        state.customer();
    }

    public void project() {
        state.project();
    }

    public void developer() {
        state.developer();
    }

    public void projectDeveloper() {
        state.projectDeveloper();
    }

    public void skill() {
        state.skill();
    }

    public void developerSkill() {
        state.developerSkill();
    }

    public void unknownCommand(String cmd) {
        state.unknownCommand(cmd);
    }

    public void setState(CliState state) {
        this.state = state;
        state.init();
    }
}