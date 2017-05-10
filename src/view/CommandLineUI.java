package view;

import enums.Algorithm;
import pojo.Stat;

public interface CommandLineUI {
    void printStatMessage(Stat stat);
    void printAverageStats(double percentage, int avgSearchCost, double avgRuntime);
    void printErrorMessage(int n, Algorithm algorithm);
}
