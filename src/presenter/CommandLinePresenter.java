package presenter;

import enums.Algorithm;
import view.CommandLineUI;

public interface CommandLinePresenter {
    void setCommandLineUI(CommandLineUI commandLineUI);
    void executeSearch(int n, Algorithm algorithm);
}
