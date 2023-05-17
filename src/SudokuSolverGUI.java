/**
 * SudokuSolverGUI represents a graphical user interface for a Sudoku Solver. It provides users with
 * a grid and corresponding "Solve" and "Clear" buttons. It uses the SudokuSolver class to solve the
 * Sudoku puzzle based upon user inputs.
 * Name: Giridhar Nair
 * Comments produced by https://syntaxwarrior30.github.io/Documentation-Wizard/
 */
import com.sun.tools.javac.Main;

import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class SudokuSolverGUI extends JFrame {

    // Buttons to solve and clear the Sudoku puzzle
    JTextField[][] textField; // TextFields to hold user input of the Sudoku puzzle
    GridPanel sudokuGrid; // Custom grid panel for Sudoku puzzle

    /**
     * Constructor for SudokuSolverGUI. Initializes the JFrame, sets its properties, 
     * initializes its components, and makes it visible.
     */
    SudokuSolverGUI() throws UnsupportedAudioFileException, IOException {

        // Create the grid of textfields
        textField = new JTextField[9][9];
        sudokuGrid = new GridPanel(new GridLayout(9, 9, 0, 0));
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                textField[x][y] = new JTextField();
                textField[x][y].setPreferredSize(new Dimension(25, 25));
                textField[x][y].setHorizontalAlignment(JTextField.CENTER);
                sudokuGrid.add(textField[x][y]);
            }
        }
    }

    /**
     * Clears the grid of user input and sets everything to default properties.
     */
    public void clearGrid() {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                textField[x][y].setText("");
                textField[x][y].setForeground(Color.black);
                textField[x][y].setEditable(true);
            }
        }
    }

    /**
     * Sets the textfields with the solved Sudoku inputs.
     * 
     * @param board The solved Sudoku board.
     */
    public void setBoardToTextFields(int[][] board) throws UnsupportedAudioFileException, IOException {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                textField[x][y].setText(Integer.toString(board[x][y]));
            }
        }
        new SoundEffect().playSuccessSound();
    }

    /**
     * Gets the user input from textfields and returns a board array.
     * 
     * @return User input in a board array.
     */
    public int[][] getBoardFromTextFields() {
        int[][] board = new int[9][9];
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                textField[x][y].setEditable(false);
                if (!textField[x][y].getText().equals("")) {
                    board[x][y] = Integer.parseInt(textField[x][y].getText());
                    textField[x][y].setForeground(Color.black);
                } else {
                    board[x][y] = 0;
                    textField[x][y].setForeground(Color.red);
                }
            }
        }
        return board;
    }
}