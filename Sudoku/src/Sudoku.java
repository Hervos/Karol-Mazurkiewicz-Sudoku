import java.util.*;

public class Sudoku {
    public static final int SIZE = 9;
    private int[][] board;
    private int[][] solvedBoard;

    public Sudoku() {
        this.board = new int[SIZE][SIZE];
        this.solvedBoard = new int[SIZE][SIZE];
        fillBoard(board);
        copyBoard(this.board, this.solvedBoard);
        createPuzzle();
    }

    // Checks if a given value is correct
    public boolean isCorrect(int row, int col, int value) {
        return solvedBoard[row][col] == value;
    }

    // Fills the Sudoku board recursively
    private boolean fillBoard(int[][] board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= SIZE; num++) {
                        if (isValid(board, row, col, num)) {
                            board[row][col] = num;
                            if (fillBoard(board)) {
                                return true;
                            } else {
                                board[row][col] = 0;
                            }
                        }
                    }
                    return false; // no valid number
                }
            }
        }
        return true; // all cells are filled
    }

    // Checks if number is legal
    private boolean isValid(int[][] board, int row, int col, int num) {
        // check the column
        for (int r = 0; r < SIZE; r++) {
            if (board[r][col] == num) {
                return false;
            }
        }

        // check the row
        for (int c = 0; c < SIZE; c++) {
            if (board[row][c] == num) {
                return false;
            }
        }

        // check the box
        int boxRow = row - row % 3;
        int boxCol = col - col % 3;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[boxRow + r][boxCol + c] == num) {
                    return false;
                }
            }
        }

        return true; // All good
    }

    // Creates a Sudoku puzzle by randomly removing numbers from the fillBoard
    private void createPuzzle() {
        Random rand = new Random();

        for (int i = 0; i < SIZE * SIZE * 0.7; i++) {
            int row, col;
            do {
                row = rand.nextInt(SIZE);
                col = rand.nextInt(SIZE);
            } while (board[row][col] == 0);

            board[row][col] = 0;
        }
    }

    // Returns the current Sudoku board
    public int[][] getBoard() {
        return board;
    }

    // Returns the solved Sudoku board
    public int[][] getSolvedBoard() {
        return solvedBoard;
    }

    // Sets the solved Sudoku board
    public void setSolvedBoard(int[][] solvedBoard) {
        this.solvedBoard = solvedBoard;
    }

    // Copies the values from one board to another
    private void copyBoard(int[][] from, int[][] to) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                to[row][col] = from[row][col];
            }
        }
    }
}
