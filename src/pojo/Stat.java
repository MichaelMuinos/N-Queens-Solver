package pojo;

public class Stat {

    private BoardState finalBoardState;
    private int size;
    private boolean solved;
    private int searchCost;
    private double runtime;

    public Stat(BoardState finalBoardState, int size, boolean solved, int searchCost, double runtime) {
        this.finalBoardState = finalBoardState;
        this.size = size;
        this.solved = solved;
        this.searchCost = searchCost;
        this.runtime = runtime;
    }

    public boolean isSolved() {
        return solved;
    }

    public int getSearchCost() {
        return searchCost;
    }

    public double getRuntime() {
        return runtime;
    }

    @Override
    public String toString() {
        return "\nSize: " + size + "\n" + finalBoardState.toString() + "Search Cost: " + searchCost + "\nRuntime: " + runtime + " seconds";
    }

}
