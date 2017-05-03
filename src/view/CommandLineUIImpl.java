package view;

import presenter.CommandLinePresenter;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CommandLineUIImpl implements CommandLineUI {

    private static final int RANDOM_CHOICE = 1;
    private static final int SPECIFIC_CHOICE = 2;
    private static final int FILE_INPUT_CHOICE = 3;
    private static final int EXIT_CHOICE = 4;

    private CommandLinePresenter commandLinePresenter;
    private Scanner scanner;

    public CommandLineUIImpl(CommandLinePresenter commandLinePresenter, Scanner scanner) {
        this.commandLinePresenter = commandLinePresenter;
        this.scanner = scanner;
    }

    private void executeCommandLine() {
        boolean stop = false;
        while(!stop) {
            showChoices();
            int choice = getUserChoice();
            switch (choice) {
                case RANDOM_CHOICE:
                    break;
                case SPECIFIC_CHOICE:
                    break;
                case FILE_INPUT_CHOICE:
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

    private int getUserChoice() throws InputMismatchException {
        int choice = 0;
        boolean validChoice = false;
        while(!validChoice) {
            choice = scanner.nextInt();
            if(choice == RANDOM_CHOICE || choice == SPECIFIC_CHOICE || choice == FILE_INPUT_CHOICE || choice == EXIT_CHOICE)
                validChoice = true;
            else
                System.out.println("Incorrect input. Try again!");
        }
        return choice;
    }

    private void showChoices() {
        System.out.println("\nChoose a type input:");
        System.out.println("1. Randomly generated.");
        System.out.println("2. Specific configuration.");
        System.out.println("3. File input (used to create table data)");
        System.out.println("4. Exit");
    }

}
