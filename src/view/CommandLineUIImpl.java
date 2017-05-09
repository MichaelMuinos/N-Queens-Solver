package view;

import enums.Algorithm;
import pojo.Stat;
import presenter.CommandLinePresenter;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CommandLineUIImpl implements CommandLineUI {

    private static final Scanner scanner = new Scanner(System.in);
    private static final int STEEPEST_HILL_CLIMBING_CHOICE = 1;
    private static final int GENETIC_ALGORITHM_CHOICE = 2;
    private static final int EXIT_CHOICE = 3;

    private CommandLinePresenter commandLinePresenter;

    public CommandLineUIImpl(CommandLinePresenter commandLinePresenter) {
        this.commandLinePresenter = commandLinePresenter;
    }

    public void executeCommandLine() {
        boolean stop = false;
        while(!stop) {
            System.out.println("\nChoose a search to use:");
            System.out.println("1. Steepest Hill Climbing Search");
            System.out.println("2. Genetic Algorithm Search");
            System.out.println("3. Exit");
            int choice = getUserChoice();
            switch (choice) {
                case STEEPEST_HILL_CLIMBING_CHOICE:
                    commandLinePresenter.executeSearch(getNumber(), Algorithm.STEEPEST_HILL_CLIMBING);
                    break;
                case GENETIC_ALGORITHM_CHOICE:
                    commandLinePresenter.executeSearch(getNumber(), Algorithm.GENETIC_ALGORITHM);
                    break;
                case EXIT_CHOICE:
                    stop = true;
                    System.out.println("Goodbye!");
                    break;
                default:
                    break;
            }
        }
    }

    private int getNumber() throws InputMismatchException {
        System.out.println("Enter a number N for the algorithm to go up to: ");
        System.out.println("Note: It will always start at N = 4.");
        return scanner.nextInt();
    }

    private int getUserChoice() throws InputMismatchException {
        int choice = 0;
        boolean validChoice = false;
        while(!validChoice) {
            choice = scanner.nextInt();
            if(choice == STEEPEST_HILL_CLIMBING_CHOICE || choice == GENETIC_ALGORITHM_CHOICE || choice == EXIT_CHOICE)
                validChoice = true;
            else
                System.out.println("Incorrect input. Try again!");
        }
        return choice;
    }

    @Override
    public void printStatMessage(Stat stat) {
        System.out.println(stat);
    }

    @Override
    public void printErrorMessage(int n, Algorithm algorithm) {
        System.out.println("\nSize: " + n + "\nError: " + algorithm + " could not solve the problem at size equal to " + n + ".");
    }

}
