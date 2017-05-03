package model;

import java.util.List;

public class SteepestHillClimbingSearch implements LocalSearchAlgorithm {

    public SteepestHillClimbingSearch() {

    }

    /**
     * Returns true if the search has found a complete answer, false otherwise.
     * @param initialState
     * @return
     */
    @Override
    public boolean performSearch(BoardState initialState) {
        // get our heuristic value for the state (number of attacking queens)
        int heuristicValue = getHeuristicValue(initialState);
        // set the heuristic to our initial state
        initialState.setHeuristicValue(heuristicValue);
        // check if our initial state is already solved
        if(isGoalState(initialState)) return true;
        // otherwise, run loop till goal is found and set current to the initial state
        BoardState currentState = initialState;
        while(true) {
            // get all successors to current
            List<BoardState> successors = getSuccessors(currentState);
            // determine the best successors out of the ones generated
            BoardState highestValuedSuccessor = getHighestValuedSuccessor(successors);
            // determine the heuristic value for our highest valued successor
            int successorHeuristicValue = getHeuristicValue(highestValuedSuccessor);
            // set the value to the object
            highestValuedSuccessor.setHeuristicValue(successorHeuristicValue);
            // check to see if the successor is a better result than current
            // if it is, then we can set current to our successor, otherwise return
            if(successorHeuristicValue <= currentState.getHeuristicValue()) return isGoalState(currentState);
            else currentState = highestValuedSuccessor;
        }
    }

    private List<BoardState> getSuccessors(BoardState boardState) {
        return null;
    }

    private BoardState getHighestValuedSuccessor(List<BoardState> successors) {
        return null;
    }

    /**
     * Determines how many pairs of queens are attacking one another.
     * @param boardState
     * @return
     */
    private int getHeuristicValue(BoardState boardState) {
        return 0;
    }

    // TODO:
    private boolean isGoalState(BoardState boardState) {
        return true;
    }

}
