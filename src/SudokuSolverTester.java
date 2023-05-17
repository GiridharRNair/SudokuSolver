/**
 * This class tests the Sudoku solver.
 * Name: Giridhar Nair
 * Comments produced by https://syntaxwarrior30.github.io/Documentation-Wizard/
 */

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

// Class that tests the SudokuSolverGUI class
public class SudokuSolverTester {
    public static void main(String[] args) {
        try {
            // Set the look and feel of the user interface
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            // Print the stack trace of the exception if the look and feel could not be set
            e.printStackTrace();
        }
        // Create a new instance of the SudokuSolverGUI class
        @SuppressWarnings("unused")
		SudokuSolverGUI solver = new SudokuSolverGUI();
    }
}