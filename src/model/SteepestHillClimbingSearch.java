package model;

import java.util.*;

public class SteepestHillClimbingSearch implements LocalSearchAlgorithm {

    private static final int INITIAL_SUCCESSOR_QUEUE_CAPACITY = 10;
    private BoardStateComparator comparator;

    public SteepestHillClimbingSearch() {
        comparator = new BoardStateComparator();
    }

    /**
     * Returns true if the search has found a complete answer, false otherwise.
     * @param initialState
     * @return
     */
    @Override
    public boolean performSearch(BoardState initialState) {
        // get our heuristic value for the state (number of attacking queens)
        int heuristicValue = determineHeuristicValue(initialState);
        // set the heuristic to our initial state
        initialState.setHeuristicValue(heuristicValue);
        // check if our initial state is already solved
        if(initialState.getHeuristicValue() == 0) return true;
        // otherwise, run loop till goal is found and set current to the initial state
        BoardState currentState = initialState;
        while(true) {

            System.out.println(currentState);

            // get all successors to current
            Queue<BoardState> successors = getSuccessors(currentState);
            // get the front successor, we know it is the best due to the comparator
            BoardState highestValuedSuccessor = successors.remove();
            // check to see if the successor is a better result than current
            // if it is, then we can set current to our successor, otherwise return
            if(highestValuedSuccessor.getHeuristicValue() >= currentState.getHeuristicValue()) return currentState.getHeuristicValue() == 0 ? true : false;
            else currentState = highestValuedSuccessor;
        }
    }

    private Queue<BoardState> getSuccessors(BoardState boardState) {
        Queue<BoardState> successors = new PriorityQueue<>(INITIAL_SUCCESSOR_QUEUE_CAPACITY, comparator);
        for(int i = 0; i < boardState.getBoard().length; i++) {
            for(int j = 0; j < boardState.getBoard()[i].length; j++) {
                if(boardState.getBoard()[i][j] == 1) {
                    getSuccessorsMovingUp(boardState, successors, i, j);
                    getSuccessorsMovingDown(boardState, successors, i, j);
                }
            }
        }
        return successors;
    }

    private void getSuccessorsMovingUp(BoardState boardState, Queue<BoardState> successors, int xPos, int yPos) {
        BoardState successorState = new BoardState(boardState.getSize());
        int[][] board = deepCopyBoard(boardState);
        // add successor that moves up
        if(xPos - 1 > -1) {
            // set the new position to be 1
            board[xPos - 1][yPos] = 1;
            // set old position to be 0
            board[xPos][yPos] = 0;
            successorState.setBoard(board);
            successorState.setHeuristicValue(determineHeuristicValue(successorState));
            successors.add(successorState);
            getSuccessorsMovingUp(successorState, successors, xPos - 1, yPos);
        } else return;
    }

    private void getSuccessorsMovingDown(BoardState boardState, Queue<BoardState> successors, int xPos, int yPos) {
        BoardState successorState = new BoardState(boardState.getSize());
        int[][] board = deepCopyBoard(boardState);
        // add successor that moves down
        if(xPos + 1 < boardState.getSize()) {
            // set the new position to be 1
            board[xPos + 1][yPos] = 1;
            // set old position to be 0
            board[xPos][yPos] = 0;
            successorState.setBoard(board);
            successorState.setHeuristicValue(determineHeuristicValue(successorState));
            successors.add(successorState);
            getSuccessorsMovingDown(successorState, successors, xPos + 1, yPos);
        } else return;
    }

    /**
     * Determines how many pairs of queens are attacking one another.
     * @param boardState
     * @return
     */
    public int determineHeuristicValue(BoardState boardState) {
        int pairs = 0;
        for(int i = 0; i < boardState.getBoard().length; i++) {
            for(int j = 0; j < boardState.getBoard()[i].length; j++) {
                if(boardState.getBoard()[i][j] == 1) {
                   pairs += checkLeft(boardState, pairs, i, j) +
                   checkRight(boardState, pairs, i, j) +
                   checkRightUpDiagonal(boardState, pairs, i, j) +
                   checkRightDownDiagonal(boardState, pairs, i, j) +
                   checkLeftUpDiagonal(boardState, pairs, i, j) +
                   checkLeftDownDiagonal(boardState, pairs, i, j);
                }
            }
        }
        return pairs;
    }

    private int checkLeft(BoardState boardState, int pairs, int i, int j) {
        if(j - 1 > -1) {
            if (boardState.getBoard()[i][j - 1] == 1) return checkLeft(boardState, ++pairs, i, j - 1);
            else return checkLeft(boardState, pairs, i, j - 1);
        }
        return pairs;
    }

    private int checkRight(BoardState boardState, int pairs, int i, int j) {
        if(j + 1 < boardState.getSize()) {
            if(boardState.getBoard()[i][j + 1] == 1) return checkRight(boardState, ++pairs, i, j + 1);
            else return checkRight(boardState, pairs, i, j + 1);
        }
        return pairs;
    }

    private int checkRightUpDiagonal(BoardState boardState, int pairs, int i, int j) {
        if(i - 1 > -1 && j + 1 < boardState.getSize()) {
            if(boardState.getBoard()[i - 1][j + 1] == 1) return checkRightUpDiagonal(boardState, ++pairs, i - 1, j + 1);
            else return checkRightUpDiagonal(boardState, pairs, i - 1, j + 1);
        }
        return pairs;
    }

    private int checkRightDownDiagonal(BoardState boardState, int pairs, int i, int j) {
        if(i + 1 < boardState.getSize() && j + 1 < boardState.getSize()) {
            if(boardState.getBoard()[i + 1][j + 1] == 1) return checkRightDownDiagonal(boardState, ++pairs, i + 1, j + 1);
            else return checkRightDownDiagonal(boardState, pairs, i + 1, j + 1);
        }
        return pairs;
    }

    private int checkLeftUpDiagonal(BoardState boardState, int pairs, int i, int j) {
        if(i - 1 > -1 && j - 1 > -1) {
            if(boardState.getBoard()[i - 1][j - 1] == 1) return checkLeftUpDiagonal(boardState, ++pairs, i - 1, j - 1);
            else return checkLeftUpDiagonal(boardState, pairs, i - 1, j - 1);
        }
        return pairs;
    }

    private int checkLeftDownDiagonal(BoardState boardState, int pairs, int i, int j) {
        if(i + 1 < boardState.getSize() && j - 1 > -1) {
            if(boardState.getBoard()[i + 1][j - 1] == 1) return checkLeftDownDiagonal(boardState, ++pairs, i + 1, j - 1);
            else return checkLeftDownDiagonal(boardState, pairs, i + 1, j - 1);
        }
        return pairs;
    }

    /**
     * performs a deep copy of the 2 dimensional board
     */
    private int[][] deepCopyBoard(BoardState state) {
        return Arrays.stream(state.getBoard()).map(i -> i.clone()).toArray(int[][]::new);
    }

    /**
     * Custom comparator class to sort successors by their heuristic value
     */
    public class BoardStateComparator implements Comparator<BoardState> {
        @Override
        public int compare(BoardState o1, BoardState o2) {
            return o1.getHeuristicValue() - o2.getHeuristicValue();
        }
    }

}
