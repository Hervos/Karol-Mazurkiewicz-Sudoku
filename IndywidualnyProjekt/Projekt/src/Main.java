import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SudokuBoard board = new SudokuBoard();
        board.populate();

        Scanner scanner = new Scanner(System.in);
        boolean end = false;
        while (!board.isSolved() && !end) {
            board.display();
            System.out.print("Enter row, column, and value (e.g. 3 5 7), or type 'end' to exit: ");
            try {
                String input = scanner.next();
                if (input.equals("end")) {
                    end = true;
                    continue;
                }
                int row = Integer.parseInt(input) - 1;
                int col = scanner.nextInt() - 1;
                int value = scanner.nextInt();

                if (row >= 0 && row < 9 && col >= 0 && col < 9 && value >= 1 && value <= 9) {
                    if (board.isValid(row, col, value)) {
                        board.setValue(row, col, value);
                    } else {
                        System.out.println("Invalid input!");
                    }
                } else {
                    System.out.println("Invalid input!");
                    scanner.nextLine(); // consume the rest of the invalid input
                }
            } catch (NumberFormatException | InputMismatchException e) {
                System.out.println("Invalid input!");
                scanner.nextLine(); // consume the invalid input
            }
        }

        if (end) {
            System.out.println("You will get it next time.");
        } else {
            System.out.println("Congratulations, you solved the puzzle!");
            board.display();
        }
    }
}
