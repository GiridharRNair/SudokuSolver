import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

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
        if (e.getSource() == SudokuSolverTester.solveButton) {
            sudokuSolverGUI.disableBoard();
            sudokuSolverGUI.solve();
            SudokuSolverTester.clearStopButton.setText("<html><center>Stop</html></center>");
            SudokuSolverTester.jSlider.setEnabled(false);
            SudokuSolverTester.solveButton.setEnabled(false);
            SudokuSolverTester.genRandPuzzle.setEnabled(false);
        }
        if (e.getSource() == SudokuSolverTester.clearStopButton && Objects.equals(SudokuSolverTester.clearStopButton.getText(), "<html><center>Stop</html></center>")) {
            sudokuSolverGUI.terminateSolve();
            SudokuSolverTester.clearStopButton.setText("<html><center>Clear</html></center>");
        } else if (e.getSource() == SudokuSolverTester.clearStopButton) {
            sudokuSolverGUI.clearGrid();
        }
        if (e.getSource() == SudokuSolverTester.genRandPuzzle) {
            sudokuSolverGUI.generateBoard();
        }
    }
}