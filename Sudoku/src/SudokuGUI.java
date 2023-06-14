import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class SudokuGUI extends JFrame {
    private Sudoku sudoku;
    private JTextField[][] cell = new JTextField[Sudoku.SIZE][Sudoku.SIZE];

    public SudokuGUI(Sudoku sudoku) {
        this.sudoku = sudoku;
        initUI();
        int[][] board = sudoku.getBoard();
        // Creates text cells
        for (int row = 0; row < Sudoku.SIZE; row++) {
            for (int col = 0; col < Sudoku.SIZE; col++) {
                if (board[row][col] != 0) {
                    cell[row][col].setText(String.valueOf(board[row][col]));
                    cell[row][col].setEditable(false);
                    cell[row][col].setBackground(Color.LIGHT_GRAY);
                }
            }
        }
    }

    private void initUI() {
        setTitle("Sudoku Solver");
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Exits program on close

        JPanel mainPanel = new JPanel(); // Creates the main panel
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // Use vertical box layout

        // GUI looks
        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        boardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        boardPanel.setPreferredSize(new Dimension(screenSize.width, screenSize.width));

        JPanel[][] panels = new JPanel[3][3]; // Creates array to hold the block panels

        // Creates the block panels, set their borders and add them to the board panel
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                panels[i][j] = new JPanel(new GridLayout(3, 3));
                panels[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                boardPanel.add(panels[i][j]);
            }
        }

        // Creates the text cell for each cell of the Sudoku grid
        for (int row = 0; row < Sudoku.SIZE; row++) {
            for (int col = 0; col < Sudoku.SIZE; col++) {
                cell[row][col] = new JTextField();
                cell[row][col].setHorizontalAlignment(JTextField.CENTER);
                cell[row][col].setDocument(new LimitDocument(1)); // Limit text to 1 character
                cell[row][col].addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        char c = e.getKeyChar();
                        if (!(c >= '1' && c <= '9')) { // Permit only digits 1-9
                            e.consume(); // Ignore the event if key is not a digit
                        }
                    }
                });
                panels[row / 3][col / 3].add(cell[row][col]); // Add the text field to the corresponding block panel
            }
        }

        mainPanel.add(boardPanel); // Add the board panel to the main panel

        // Buttons

        JPanel buttonPanel = new JPanel(new FlowLayout()); // Creates panel for buttons
        JButton resetButton = new JButton("Reset"); // Creates reset button
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sudoku = new Sudoku(); // Creates a new Sudoku instance
                updateBoard(); // Update the displayed board
            }
        });
        JButton fillButton = new JButton("Solve"); // Creates solve button
        fillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fillBoard(); // Fill the board with the solved values
                checkBoard(); // Check the board for correctness
            }
        });
        JButton solveButton = new JButton("Check"); // Creates check button
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkBoard(); // Check the board for correctness
            }
        });
        buttonPanel.add(solveButton); // Add the check button to the button panel
        buttonPanel.add(fillButton); // Add the solve button to the button panel
        buttonPanel.add(resetButton); // Add the reset button to the button panel
        mainPanel.add(buttonPanel); // Add the button panel to the main panel

        setContentPane(mainPanel); // Set the main panel as the content pane
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the window
        setLocationRelativeTo(null); // Center the window on the screen
    }

    private void checkBoard() {
        for (int row = 0; row < Sudoku.SIZE; row++) {
            for (int col = 0; col < Sudoku.SIZE; col++) {
                String text = cell[row][col].getText();
                if (!text.isEmpty()) {
                    int value = Integer.parseInt(text);
                    if (sudoku.isCorrect(row, col, value)) { // Check if the entered value is correct
                        if (!cell[row][col].getBackground().equals(Color.LIGHT_GRAY))
                            cell[row][col].setBackground(Color.GREEN); // Set background color to green for correct values
                    } else {
                        cell[row][col].setBackground(Color.RED); // Set background color to red for incorrect values
                    }
                }
            }
        }
    }

    private void updateBoard() {
        int[][] board = sudoku.getBoard();
        for (int row = 0; row < Sudoku.SIZE; row++) {
            for (int col = 0; col < Sudoku.SIZE; col++) {
                if (board[row][col] != 0) {
                    cell[row][col].setText(String.valueOf(board[row][col]));
                    cell[row][col].setEditable(false);
                    cell[row][col].setBackground(Color.LIGHT_GRAY); // Set background color for pre-filled cells
                } else {
                    cell[row][col].setText("");
                    cell[row][col].setEditable(true);
                    cell[row][col].setBackground(Color.WHITE); // Set background color for empty cells
                }
            }
        }
    }

    private void fillBoard() {
        int[][] solvedBoard = sudoku.getSolvedBoard();
        for (int row = 0; row < Sudoku.SIZE; row++) {
            for (int col = 0; col < Sudoku.SIZE; col++) {
                cell[row][col].setText(String.valueOf(solvedBoard[row][col])); // Fill the board with solved values
            }
        }
    }

    private class LimitDocument extends PlainDocument {
        private int limit;

        LimitDocument(int limit) {
            super();
            this.limit = limit;
        }

        @Override
        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            if (str == null)
                return;

            if ((getLength() + str.length()) <= limit) {
                super.insertString(offset, str, attr); // Limit the input length to the specified limit
            }
        }
    }
}
