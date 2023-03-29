public class SudokuBoard {
    private int[][] grid;

    public SudokuBoard() {
        grid = new int[9][9];
    }

    public void populate() {
        // Generate Sudoku puzzle
    }

    public void display() {
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0) {
                System.out.println("+-----------------------------+");
            }
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0) {
                    System.out.print("|");
                }
                if (grid[i][j] == 0) {
                    System.out.print("   ");
                } else {
                    System.out.printf(" %d ", grid[i][j]);
                }
            }
            System.out.println("|");
        }
        System.out.println("+-----------------------------+");
    }

    public boolean isValid(int row, int col, int value) {
        // Check if the value is already in the same row or column
        for (int i = 0; i < 9; i++) {
            if (grid[row][i] == value || grid[i][col] == value) {
                return false;
            }
        }

        // Check if the value is already in the same 3x3 sub-grid
        int subGridRow = (row / 3) * 3;
        int subGridCol = (col / 3) * 3;
        for (int i = subGridRow; i < subGridRow + 3; i++) {
            for (int j = subGridCol; j < subGridCol + 3; j++) {
                if (grid[i][j] == value) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isSolved() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void setValue(int row, int col, int value) {
        grid[row][col] = value;
    }

    public int getValue(int row, int col) {
        return grid[row][col];
    }
}
