/**
 * This class tests the Sudoku solver.
 * Name: Giridhar Nair
 * Comments produced by https://syntaxwarrior30.github.io/Documentation-Wizard/
 */

import com.sun.tools.javac.Main;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

// Class that tests the SudokuSolverGUI class
public class SudokuSolverTester {

    static JFrame frame;
    static JButton solveButton;
    static JButton clearButton;
    static JLabel status;


    public static void main(String[] args) throws UnsupportedAudioFileException, IOException {
        try {
            // Set the look and feel of the user interface
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            // Print the stack trace of the exception if the look and feel could not be set
            e.printStackTrace();
        }

        SudokuSolverGUI sudokuSolverGUI  = new SudokuSolverGUI();


        frame = new JFrame("Sudoku Solver");

        // Set the icon image for the frame
        final Image image = Toolkit.getDefaultToolkit().getImage(Main.class.getClassLoader().getResource("SudokuSolverIcon.jpeg"));
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            Taskbar.getTaskbar().setIconImage(image);
        }

        solveButton = new JButton("Solve");
        clearButton = new JButton("Clear");
        status = new JLabel("Input Numbers");

        solveButton.addActionListener(new ButtonHandler(sudokuSolverGUI));
        clearButton.addActionListener(new ButtonHandler(sudokuSolverGUI));
        clearButton.setVisible(false);

        frame.setIconImage(image);
        frame.setIconImage(image);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(250, 305);
        frame.setLayout(new FlowLayout());
        frame.setResizable(false);

        frame.add(sudokuSolverGUI.sudokuGrid);
        frame.add(solveButton);
        frame.add(clearButton);
        frame.add(status);
        frame.setVisible(true);
    }
}