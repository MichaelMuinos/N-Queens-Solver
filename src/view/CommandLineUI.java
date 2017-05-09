package view;

import enums.Algorithm;
import pojo.Stat;

public interface CommandLineUI {
    void printStatMessage(Stat stat);
    void printErrorMessage(int n, Algorithm algorithm);
}
