package model;

public class BoardState {

    private int size;
    private int[][] board;
    private int heuristicValue;

    public BoardState(int size) {
        this.size = size;
        init();
    }

    private void init() {
        board = new int[size][size];
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int getHeuristicValue() {
        return heuristicValue;
    }

    public void setHeuristicValue(int heuristicValue) {
        this.heuristicValue = heuristicValue;
    }

}
