package model;

import javafx.util.Pair;
import pojo.BoardState;
import pojo.GeneticBoardState;
import pojo.Stat;

import java.util.*;

public class GeneticAlgorithmSearch implements LocalSearchAlgorithm {

    private static final int INITIAL_POPULATION_SIZE = 100;
    private static final int MAX_ITERATIONS = 10000;
    private static final Random random = new Random();

    private GeneticAlgorithmSearch.GeneticBoardStateComparator comparator;
    private int currentCount = 0;

    public GeneticAlgorithmSearch() {
        comparator = new GeneticBoardStateComparator();
    }

    @Override
    public Stat performSearch(BoardState initialState) {
        // start time
        long start = System.currentTimeMillis();
        // iterations count
        int iterations = 0;
        // search cost variable
        int searchCost = 0;
        // total heuristic variable to calculate probabilities
        double totalHeuristic = 0;
        // frontier to hold states
        Queue<GeneticBoardState> population = new PriorityQueue<>(INITIAL_POPULATION_SIZE, comparator);
        // populate the initial population of board states
        for(int i = 0; i < INITIAL_POPULATION_SIZE; i++) {
            GeneticBoardState boardState = new GeneticBoardState(initialState.getSize());
            // set heuristic
            boardState.setHeuristicValue(determineHeuristicValue(boardState));
            // add to our total
            totalHeuristic += boardState.getHeuristicValue();
            population.add(boardState);
        }
        // start loop to find solution
        while(population.peek().getHeuristicValue() != initialState.getSize()) {
            // get current population size before changing it
            int currentPopulationCount = population.size();
            // loop through population and set range breakdown
            for(GeneticBoardState boardState : population) boardState.setRange((int) Math.ceil((boardState.getHeuristicValue() / totalHeuristic) * 100));
            // get the appropriate ranges to allow for constant time selection
            currentCount = 0;
            Map<Integer,GeneticBoardState> ranges = getRanges(population);
            // reset our total heuristic
            totalHeuristic = 0.0;
            for(int i = 0; i < currentPopulationCount; i++) {
                GeneticBoardState parentOne = ranges.get(random.nextInt(currentCount) + 1);
                GeneticBoardState parentTwo = ranges.get(random.nextInt(currentCount) + 1);
                Pair<GeneticBoardState,GeneticBoardState> offspring = createOffspring(parentOne, parentTwo);
                GeneticBoardState betterOffspring = offspring.getKey().getHeuristicValue() > offspring.getValue().getHeuristicValue() ? offspring.getKey() : offspring.getValue();
                // add to our total heuristic
                totalHeuristic += betterOffspring.getHeuristicValue();
                // add to our search cost
                ++searchCost;
                // add betterOffspring offspring to new population
                population.add(betterOffspring);
            }
            if(++iterations == MAX_ITERATIONS) return new Stat(population.peek(), population.peek().getSize(), false, searchCost, (System.currentTimeMillis() - start) / 1000.0);
        }
        return new Stat(population.peek(), population.peek().getSize(), true, searchCost, (System.currentTimeMillis() - start) / 1000.0);
    }

    private Map<Integer,GeneticBoardState> getRanges(Queue<GeneticBoardState> population) {
        Map<Integer,GeneticBoardState> ranges = new HashMap<>();
        int populationSize = population.size();
        for(int i = 0; i < populationSize; i++) {
            GeneticBoardState boardState = population.remove();
            // start adding all board states to their appropriate range
            for(int j = currentCount; j < (currentCount + boardState.getRange()); j++) ranges.put(j + 1, boardState);
            currentCount += boardState.getRange();
        }
        return ranges;
    }

    private Pair<GeneticBoardState,GeneticBoardState> createOffspring(GeneticBoardState parentOne, GeneticBoardState parentTwo) {
        int size = parentOne.getSize();
        int val = (int) Math.ceil(size / 3.0);
        // crossover occurs here
        int[][] offspringBoardOne = new int[size][size];
        int[][] offspringBoardTwo = new int[size][size];
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if(j < val) {
                    offspringBoardOne[i][j] = parentOne.getBoard()[i][j];
                    offspringBoardTwo[i][j] = parentTwo.getBoard()[i][j];
                } else {
                    offspringBoardOne[i][j] = parentTwo.getBoard()[i][j];
                    offspringBoardTwo[i][j] = parentOne.getBoard()[i][j];
                }
            }
        }
        // mutation
        performMutation(offspringBoardOne);
        performMutation(offspringBoardTwo);
        // create the board state objects
        // 1st offspring
        GeneticBoardState offspringOne = new GeneticBoardState(size);
        offspringOne.setBoard(offspringBoardOne);
        offspringOne.setHeuristicValue(determineHeuristicValue(offspringOne));
        // 2nd offspring
        GeneticBoardState offspringTwo = new GeneticBoardState(size);
        offspringTwo.setBoard(offspringBoardTwo);
        offspringTwo.setHeuristicValue(determineHeuristicValue(offspringTwo));
        // return pair of offspring
        return new Pair<>(offspringOne,offspringTwo);
    }

    private void performMutation(int[][] board) {
        int col = random.nextInt(board.length);
        for(int i = 0; i < board.length; i++) {
            if(board[i][col] == 1) board[i][col] = 0;
        }
        int row = random.nextInt(board.length);
        board[row][col] = 1;
    }

    /**
     * Determines how many non-attacking queens there are
     * @param boardState
     * @return
     */
    private int determineHeuristicValue(BoardState boardState) {
        int nonAttackingQueens = 0;
        for(int i = 0; i < boardState.getBoard().length; i++) {
            for (int j = 0; j < boardState.getBoard()[i].length; j++) {
                if (boardState.getBoard()[i][j] == 1 && checkDirections(boardState, i, j))
                    ++nonAttackingQueens;
            }
        }
        return nonAttackingQueens;
    }

    private boolean checkDirections(BoardState boardState, int i, int j) {
        return checkLeft(boardState, i, j) && checkRight(boardState, i, j) && checkRightUpDiagonal(boardState, i, j)
                && checkRightDownDiagonal(boardState, i, j) && checkLeftUpDiagonal(boardState, i, j)
                && checkLeftDownDiagonal(boardState, i, j);
    }

    private boolean checkLeft(BoardState boardState, int i, int j) {
       if(j - 1 > -1) return boardState.getBoard()[i][j - 1] == 1 ? false : checkLeft(boardState, i, j - 1);
       else return true;
    }

    private boolean checkRight(BoardState boardState, int i, int j) {
        if(j + 1 < boardState.getSize()) return boardState.getBoard()[i][j + 1] == 1 ? false : checkRight(boardState, i, j + 1);
        else return true;
    }

    private boolean checkRightUpDiagonal(BoardState boardState, int i, int j) {
        if(i - 1 > -1 && j + 1 < boardState.getSize()) return boardState.getBoard()[i - 1][j + 1] == 1 ? false : checkRightUpDiagonal(boardState, i - 1, j + 1);
        return true;
    }

    private boolean checkRightDownDiagonal(BoardState boardState, int i, int j) {
        if(i + 1 < boardState.getSize() && j + 1 < boardState.getSize()) return boardState.getBoard()[i + 1][j + 1] == 1 ? false : checkRightDownDiagonal(boardState, i + 1, j + 1);
        return true;
    }

    private boolean checkLeftUpDiagonal(BoardState boardState, int i, int j) {
        if(i - 1 > -1 && j - 1 > -1) return boardState.getBoard()[i - 1][j - 1] == 1 ? false : checkLeftUpDiagonal(boardState, i - 1, j - 1);
        return true;
    }

    private boolean checkLeftDownDiagonal(BoardState boardState, int i, int j) {
        if(i + 1 < boardState.getSize() && j - 1 > -1) return boardState.getBoard()[i + 1][j - 1] == 1 ? false : checkLeftDownDiagonal(boardState, i + 1, j - 1);
        return true;
    }

    /**
     * Custom comparator class to sort successors by their heuristic value
     */
    public class GeneticBoardStateComparator implements Comparator<GeneticBoardState> {
        @Override
        public int compare(GeneticBoardState o1, GeneticBoardState o2) {
            return o2.getHeuristicValue() - o1.getHeuristicValue();
        }
    }

}
