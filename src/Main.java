import model.GeneticAlgorithmSearch;
import model.SteepestHillClimbingSearch;
import presenter.CommandLinePresenterImpl;
import view.CommandLineUIImpl;

public class Main {
    public static void main(String[] args) {
        // create presenter
        CommandLinePresenterImpl presenter = new CommandLinePresenterImpl(new SteepestHillClimbingSearch(), new GeneticAlgorithmSearch());
        // create ui
        CommandLineUIImpl commandLine = new CommandLineUIImpl(presenter);
        // set ui to our presenter so they can talk back and forth
        presenter.setCommandLineUI(commandLine);
        // start the command line
        commandLine.executeCommandLine();
    }
}
