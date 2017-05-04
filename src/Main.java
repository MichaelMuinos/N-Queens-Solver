import model.BoardState;
import model.SteepestHillClimbingSearch;
import presenter.CommandLinePresenterImpl;
import view.CommandLineUIImpl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CommandLinePresenterImpl presenter = new CommandLinePresenterImpl();
        CommandLineUIImpl commandLine = new CommandLineUIImpl(presenter, new Scanner(System.in));
        commandLine.executeCommandLine();
    }
}
