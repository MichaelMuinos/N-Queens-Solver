package pojo;

public class Stat {

    private BoardState finalBoardState;
    private int size;
    private boolean solved;
    private long runtime;

    public Stat(BoardState finalBoardState, int size, boolean solved, long runtime) {
        this.finalBoardState = finalBoardState;
        this.size = size;
        this.solved = solved;
        this.runtime = runtime;
    }

    public boolean isSolved() {
        return solved;
    }

    @Override
    public String toString() {
        return "\nSize: " + size + "\n" + finalBoardState.toString() + "Runtime: " + runtime;
    }

}
