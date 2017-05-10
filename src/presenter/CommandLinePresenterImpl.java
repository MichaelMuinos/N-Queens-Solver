package presenter;

import enums.Algorithm;
import pojo.BoardState;
import model.GeneticAlgorithmSearch;
import pojo.Stat;
import model.SteepestHillClimbingSearch;
import view.CommandLineUI;

public class CommandLinePresenterImpl implements CommandLinePresenter {

    private static final int MIN_N = 4;

    private CommandLineUI commandLineUI;
    private SteepestHillClimbingSearch steepestHillClimbingSearch;
    private GeneticAlgorithmSearch geneticAlgorithmSearch;

    public CommandLinePresenterImpl(SteepestHillClimbingSearch steepestHillClimbingSearch, GeneticAlgorithmSearch geneticAlgorithmSearch) {
        this.steepestHillClimbingSearch = steepestHillClimbingSearch;
        this.geneticAlgorithmSearch = geneticAlgorithmSearch;
    }

    @Override
    public void setCommandLineUI(CommandLineUI commandLineUI) {
        this.commandLineUI = commandLineUI;
    }

    @Override
    public void executeSearch(int n, Algorithm algorithm) {
        // variables for showing average stats
        int completed = 0;
        int totalSearchCost = 0;
        double totalRuntime = 0;
        // start searches on various sizes
        for(int i = MIN_N; i <= n; i++) {
            BoardState boardState = new BoardState(i);
            Stat stat = algorithm == Algorithm.STEEPEST_HILL_CLIMBING ? steepestHillClimbingSearch.performSearch(boardState) : geneticAlgorithmSearch.performSearch(boardState);
            if(stat.isSolved()) {
                commandLineUI.printStatMessage(stat);
                ++completed;
                totalSearchCost += stat.getSearchCost();
                totalRuntime += stat.getRuntime();
            } else commandLineUI.printErrorMessage(i, algorithm);
        }
        commandLineUI.printAverageStats((double) completed / ((n - MIN_N) + 1) * 100, totalSearchCost / completed, totalRuntime / completed);
    }

}
