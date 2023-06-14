public class Main {
    public static void main(String[] args) {

        Sudoku sudoku = new Sudoku();
        SudokuGUI gui = new SudokuGUI(sudoku);
        gui.setVisible(true);
    }
}
