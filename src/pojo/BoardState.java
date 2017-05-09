package pojo;

import java.util.Random;

public class BoardState {

    private static final Random random = new Random();

    private int size;
    private int[][] board;
    private int heuristicValue;

    public BoardState(int size) {
        this.size = size;
        init();
    }

    private void init() {
        board = new int[size][size];
        for(int col = 0; col < size; col++) {
            // get random position for our row
            int row = random.nextInt(size);
            board[row][col] = 1;
        }
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

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        String boardStr = "";
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                boardStr += board[i][j] + "\t";
            }
            boardStr += "\n";
        }
        return boardStr;
    }

}
