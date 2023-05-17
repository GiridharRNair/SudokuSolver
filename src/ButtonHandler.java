import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ButtonHandler implements ActionListener {

    SudokuSolverGUI sudokuSolverGUI;

    public ButtonHandler(SudokuSolverGUI sudokuSolverGUI) {
        this.sudokuSolverGUI = sudokuSolverGUI;
    }

    /**
     * Action Performed method that handles actions when the Solve or Clear button is pressed.
     *
     * @param e The ActionEvent object.
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == SudokuSolverTester.solveButton)
        {
            SudokuSolverTester.solveButton.setVisible(false);
            SudokuSolverTester.clearButton.setVisible(true);
            int[][] board = new int[9][9];
            boolean invalidInput = false;
            try {
                // Put user input into a board array
                board = sudokuSolverGUI.getBoardFromTextFields();
            } catch (Exception NumberFormatException) {
                invalidInput = true;
                //playErrorSound();
            }
            SudokuSolver puzzle = new SudokuSolver(board); // Create new SudokuSolver object
            if (puzzle.solve() && !invalidInput) // If solution exists and inputs are valid
            {
                SudokuSolverTester.status.setForeground(new Color(0, 102, 0));
                SudokuSolverTester.status.setText("Successful To Solve");
                try {
                    // Set textfields with solved board
                    sudokuSolverGUI.setBoardToTextFields(puzzle.getBoard());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            } else
            {
                SudokuSolverTester.status.setForeground(Color.red);
                SudokuSolverTester.status.setText("Unsuccessful To Solve");
                try {
                    new SoundEffect().playErrorSound();
                } catch (UnsupportedAudioFileException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        if (e.getSource() == SudokuSolverTester.clearButton) {
            SudokuSolverTester.clearButton.setVisible(false);
            SudokuSolverTester.solveButton.setVisible(true);
            sudokuSolverGUI.clearGrid();
            SudokuSolverTester.status.setForeground(Color.black);
            SudokuSolverTester.status.setText("Input Numbers");
        }
    }
}