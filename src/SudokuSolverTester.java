/**
 * This class tests the SudokuSolverGUI class.
 * Name: Giridhar Nair
 */

import com.sun.tools.javac.Main;
import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

// Class that tests the SudokuSolverGUI class
public class SudokuSolverTester {

    static MyJSlider jSlider;
    static JButton solveButton;
    static JButton clearStopButton;
    static JButton genRandPuzzle;
    static JLabel status;


    public static void main(String[] args) {
        try {
            // Set the look and feel of the user interface
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            // Print the stack trace of the exception if the look and feel could not be set
            e.printStackTrace();
        }

        SudokuSolverGUI sudokuSolverGUI  = new SudokuSolverGUI();

        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarculaLaf");
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel userInputPanel = new JPanel();
        userInputPanel.setLayout(new BoxLayout(userInputPanel, BoxLayout.Y_AXIS));
        userInputPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userInputPanel.setAlignmentY(Component.CENTER_ALIGNMENT);

        JFrame frame = new JFrame("Sudoku Solver");

        // Set the icon image for the frame
        final Image image = Toolkit.getDefaultToolkit().getImage(Main.class.getClassLoader().getResource("SudokuSolverIcon.jpeg"));
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            Taskbar.getTaskbar().setIconImage(image);
        }

        status = new JLabel("<html><center>Set Iteration Delay</html></center>");
        status.setAlignmentX(Component.CENTER_ALIGNMENT);

        jSlider = new MyJSlider(0, 100);
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(0, new JLabel("Slow"));
        labelTable.put(100, new JLabel("Fast"));
        jSlider.setLabelTable( labelTable );
        jSlider.setPaintLabels(true);

        solveButton = new JButton("<html><center>Solve</center></html>");
        solveButton.addActionListener(new ButtonHandler(sudokuSolverGUI));
        solveButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        clearStopButton = new JButton("<html><center>Clear</center></html>");
        clearStopButton.addActionListener(new ButtonHandler(sudokuSolverGUI));
        clearStopButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        genRandPuzzle = new JButton("<html><center>Generate Random Puzzle</center></html>");
        genRandPuzzle.addActionListener(new ButtonHandler(sudokuSolverGUI));
        genRandPuzzle.setAlignmentX(Component.CENTER_ALIGNMENT);

        frame.setIconImage(image);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.setResizable(false);
        frame.add(sudokuSolverGUI.sudokuGrid);

        userInputPanel.add(status);
        userInputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        userInputPanel.add(jSlider);
        userInputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        userInputPanel.add(solveButton);
        userInputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        userInputPanel.add(clearStopButton);
        userInputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        userInputPanel.add(genRandPuzzle);

        frame.add(userInputPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}