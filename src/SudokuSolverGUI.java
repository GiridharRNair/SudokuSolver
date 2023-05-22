import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class SudokuSolverGUI extends JFrame {

    public final IntegerInputField[][] textField; // TextFields to hold user input of the Sudoku puzzle
    public GridPanel sudokuGrid; // Custom grid panel for Sudoku puzzle
    public boolean isDone; // Boolean value to store if done or not
    public boolean terminate; // Boolean value to store if user wants to terminate solve
    public Thread solveThread;

    public SudokuSolverGUI() {
        isDone = true;

        textField = new IntegerInputField[9][9];
        sudokuGrid = new GridPanel(new GridLayout(9, 9, 0, 0));

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                textField[r][c] = new IntegerInputField();

                int finalR = r;
                int finalC = c;
                textField[r][c].addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        if(textField[finalR][finalC].isEditable()) {
                            changeForegroundBlack();
                        }
                    }

                    @Override
                    public void focusLost(FocusEvent e) {

                    }
                });

                textField[r][c].setPreferredSize(new Dimension(40, 40));
                this.setFont(new Font("Arial", Font.PLAIN, 100));
                textField[r][c].setHorizontalAlignment(JTextField.CENTER);
                sudokuGrid.add(textField[r][c]);
            }
        }

        this.add(sudokuGrid);
    }

    public void clearGrid() {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                textField[x][y].setText("");
                textField[x][y].setToolTipText("");
                textField[x][y].setForeground(Color.black);
                textField[x][y].setEditable(true);
            }
        }
    }

    public void solve() {
        terminate = false;

        if (Objects.equals(this.isValidSudoku(), "")) {
            this.disableBoard();
            SudokuSolverTester.clearStopButton.setText("<html><center>Stop</html></center>");
            SudokuSolverTester.jSlider.setEnabled(false);
            SudokuSolverTester.solveButton.setEnabled(false);
            SudokuSolverTester.genRandPuzzle.setEnabled(false);
            solveThread = new Thread(this::solveHelper);
            solveThread.start();
        } else {
            try {
                new SoundEffect().playErrorSound();
            } catch (UnsupportedAudioFileException | IOException e) {
                throw new RuntimeException(e);
            }

            JOptionPane.showMessageDialog(SudokuSolverTester.frame,
                    this.isValidSudoku(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    public void terminateSolve() {
        terminate = true;
        if (solveThread != null) {
            solveThread.interrupt();
            SudokuSolverTester.jSlider.setEnabled(true);
            SudokuSolverTester.solveButton.setEnabled(true);
            SudokuSolverTester.genRandPuzzle.setEnabled(true);

            for (IntegerInputField[] integerInputFields : textField) {
                for (IntegerInputField integerInputField : integerInputFields) {
                    integerInputField.setToolTipText("");
                }
            }
        }
    }

    private boolean solveHelper() {
        int row = -1;
        int col = -1;
        boolean isFilled = true;

        // Find the next empty cell
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (textField[r][c].getText().isEmpty()) {
                    row = r;
                    col = c;
                    isFilled = false;
                    break;
                }
            }
            if (!isFilled) {
                break;
            }
        }

        // If all cells are filled, the Sudoku puzzle is solved
        if (isFilled) {
            SudokuSolverTester.clearStopButton.setText("<html><center>Clear</html></center>");

            try {
                new SoundEffect().playSuccessSound();
            } catch (UnsupportedAudioFileException | IOException e) {
                throw new RuntimeException(e);
            }

            SudokuSolverTester.jSlider.setEnabled(true);
            SudokuSolverTester.solveButton.setEnabled(true);
            SudokuSolverTester.genRandPuzzle.setEnabled(true);
            JOptionPane.showMessageDialog(SudokuSolverTester.frame,
                    "Congratulations! Sudoku Solved",
                    "Success",
                    JOptionPane.PLAIN_MESSAGE);
            return true;
        }

        for (int num = 1; num <= 9; num++) {
            if (terminate) {
                // Thread was interrupted, terminate solving
                return false;
            } else {
                sleep();
            }

            if (isValid(row, col, num)) {
                textField[row][col].setForeground(Color.blue);
                textField[row][col].setText(String.valueOf(num));

                if (solveHelper()) {
                    return true;
                }

                // Backtrack if the solution is not found
                textField[row][col].setText("");
            }
        }
        return false;
    }


    public boolean isValid(int row, int col, int num) {
        // Check row constraints
        for (int i = 0; i < textField.length; i++) {
            if (i != col && textField[row][i].getText().equals(String.valueOf(num))) {
                return false;
            }
        }

        // Check column constraints
        for (int i = 0; i < textField.length; i++) {
            if (i != row && textField[i][col].getText().equals(String.valueOf(num))) {
                return false;
            }
        }

        // Check 3x3 box constraint
        int boxRow = row - row % 3;
        int boxCol = col - col % 3;
        for (int i = boxRow; i < boxRow + 3; i++) {
            for (int j = boxCol; j < boxCol + 3; j++) {
                if (textField[i][j].getText().equals(String.valueOf(num))) {
                    return false;
                }
            }
        }

        return true;
    }

    private void sleep() {
        try {
            long sleepTime = SudokuSolverTester.jSlider.getMaximum() - ((long) SudokuSolverTester.jSlider.getValue() * (SudokuSolverTester.jSlider.getMaximum() - SudokuSolverTester.jSlider.getMinimum()) / 100);
            while (sleepTime > 0) {
                long startTime = System.currentTimeMillis();
                TimeUnit.MILLISECONDS.sleep(sleepTime);
                long elapsedTime = System.currentTimeMillis() - startTime;
                sleepTime -= elapsedTime;
            }
        } catch (InterruptedException e) {
            // Restore the interrupted status
            Thread.currentThread().interrupt();
        }
    }

    public void disableBoard() {
        for (JTextField[] jTextFields : textField) {
            for (JTextField jTextField : jTextFields) {
                jTextField.setEditable(false);
                jTextField.setToolTipText("Cannot Edit During Solve");
            }
        }
    }

    public void generateBoard() {
        this.clearGrid();
        SudokuGenerator randomBoard = new SudokuGenerator(9, (int)(Math.random() * 76) + 5);
        randomBoard.generate();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if(randomBoard.grid[r][c] != 0) {
                    textField[r][c].setText(String.valueOf(randomBoard.grid[r][c]));
                } else {
                    textField[r][c].setText("");
                }
            }
        }
    }

    public String isValidSudoku() {
        // Convert JTextFields to int[][] board
        int[][] board = new int[textField.length][textField.length];
        for (int r = 0; r < textField.length; r++) {
            for (int c = 0; c < textField[r].length; c++) {
                if (!Objects.equals(textField[r][c].getText(), "")) {
                    board[r][c] = Integer.parseInt(textField[r][c].getText());
                } else {
                    board[r][c] = 0;
                }
            }
        }

        // Check rows
        for (int row = 0; row < 9; row++) {
            if (!isValidRow(board, row)) {
                return "There are duplicate numbers on the same row";
            }
        }

        // Check columns
        for (int col = 0; col < 9; col++) {
            if (!isValidColumn(board, col)) {
                return "There are duplicate numbers on the same column";
            }
        }

        // Check squares
        for (int row = 0; row < 9; row += 3) {
            for (int col = 0; col < 9; col += 3) {
                if (!isValidSquare(board, row, col)) {
                    return "There are duplicate numbers in the same subgrid";
                }
            }
        }

        return "";
    }

    private boolean isValidRow(int[][] board, int row) {
        boolean[] used = new boolean[9];
        for (int col = 0; col < 9; col++) {
            int value = board[row][col];
            if (value != 0) {
                if (used[value - 1]) {
                    errorNumbersRow(row, value);
                    return false; // Duplicate value found
                }
                used[value - 1] = true;
            }
        }
        return true;
    }

    private boolean isValidColumn(int[][] board, int col) {
        boolean[] used = new boolean[9];
        for (int row = 0; row < 9; row++) {
            int value = board[row][col];
            if (value != 0) {
                if (used[value - 1]) {
                    errorNumbersCol(col, value);
                    return false; // Duplicate value found
                }
                used[value - 1] = true;
            }
        }
        return true;
    }

    private boolean isValidSquare(int[][] board, int startRow, int startCol) {
        boolean[] used = new boolean[9];
        for (int row = startRow; row < (startRow + 3); row++) {
            for (int col = startCol; col < (startCol + 3); col++) {
                int value = board[row][col];
                if (value != 0) {
                    if (used[value - 1]) {
                        errorNumbersSubGrid(board, value, startRow, startCol);
                        return false; // Duplicate value found
                    }
                    used[value - 1] = true;
                }
            }
        }
        return true;
    }

    private void errorNumbersRow(int row, int value) {
        for(int col = 0; col < textField.length; col++) {
            if(!Objects.equals(textField[row][col].getText(), "")) {
                if (Integer.parseInt(textField[row][col].getText()) == value) {
                    textField[row][col].setForeground(Color.RED);
                    textField[row][col].setText(Integer.toString(value));
                }
            }
        }
    }

    private void errorNumbersCol(int col, int value) {
        for(int row = 0; row < textField.length; row++) {
            if(!Objects.equals(textField[row][col].getText(), "")) {
                if (Integer.parseInt(textField[row][col].getText()) == value) {
                    textField[row][col].setForeground(Color.RED);
                    textField[row][col].setText(Integer.toString(value));
                }
            }
        }
    }

    private void errorNumbersSubGrid(int[][] board, int value, int startRow, int startCol) {
        for (int row = startRow; row < (startRow + 3); row++) {
            for (int col = startCol; col < (startCol + 3); col++) {
                if (board[row][col] != 0) {
                    int currentValue = board[row][col];
                    if (currentValue == value) {
                        board[row][col] = value;
                        textField[row][col].setForeground(Color.RED);
                    }
                }
            }
        }
    }



    public void changeForegroundBlack() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                textField[r][c].setForeground(Color.BLACK);
            }
        }
    }
}
