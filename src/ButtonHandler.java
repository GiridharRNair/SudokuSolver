import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        }
        if (e.getSource() == SudokuSolverTester.clearButton) {
            sudokuSolverGUI.clearGrid();
            SudokuSolverTester.status.setForeground(Color.black);
            SudokuSolverTester.status.setText("Input Numbers");
        }
    }
}