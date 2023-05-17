/**
 * SudokuSolverGUI represents a graphical user interface for a Sudoku Solver. It provides users with
 * a grid and corresponding "Solve" and "Clear" buttons. It uses the SudokuSolver class to solve the
 * Sudoku puzzle based upon user inputs.
 * Name: Giridhar Nair
 * Comments produced by https://syntaxwarrior30.github.io/Documentation-Wizard/
 */
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class SudokuSolverGUI extends JFrame implements ActionListener {
    JFrame frame; // JFrame to hold the entire SudokuSolverGUI panel
    JButton solveButton, clearButton; // Buttons to solve and clear the Sudoku puzzle
    JTextField[][] textField; // TextFields to hold user input of the Sudoku puzzle
    JLabel status; // Label to indicate the status of the Sudoku puzzle (successful/unsuccessful)
    GridPanel sudokuGrid; // Custom grid panel for Sudoku puzzle

    /**
     * Constructor for SudokuSolverGUI. Initializes the JFrame, sets its properties, 
     * initializes its components, and makes it visible.
     */
    SudokuSolverGUI() {
        frame = new JFrame("Sudoku Solver");
        final Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        // Set the icon image for the frame
        final Image image = defaultToolkit.getImage(SudokuSolverTester.class.getClassLoader().getResource("SudokuSolverIcon.jpeg"));
        String s = System.getProperty("os.name").toLowerCase();
        if (s.contains("mac")) {
            final Taskbar taskbar = Taskbar.getTaskbar();
            taskbar.setIconImage(image);
        }
        frame.setIconImage(image);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(250, 305);
        frame.setLayout(new FlowLayout());
        frame.setResizable(false);

        // Add action listeners to the buttons
        solveButton = new JButton("Solve");
        clearButton = new JButton("Clear");
        status = new JLabel("Input Numbers");

        solveButton.addActionListener(this);
        clearButton.addActionListener(this);
        clearButton.setVisible(false);

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

        // Add components to JFrame
        frame.add(sudokuGrid);
        frame.add(solveButton);
        frame.add(clearButton);
        frame.add(status);
        frame.setVisible(true);
    }

    /**
     * Custom panel class for the Sudoku grid. Paints the lines on the grid.
     */
    public class GridPanel extends JPanel {
        GridPanel(GridLayout layout) {
            super(layout);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.BLACK);
            int width = getWidth();
            int height = getHeight();
            int cellWidth = width / 9;
            int cellHeight = height / 9;
            for (int i = 0; i <= 9; i++) {
                if (i % 3 == 0) {
                    g.fillRect(0, i * cellHeight - 2, width, 3);
                    g.fillRect(i * cellWidth - 2, 0, 3, height);
                } else {
                    g.fillRect(0, i * cellHeight, width, 1);
                    g.fillRect(i * cellWidth, 0, 1, height);
                }
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
     * Action Performed method that handles actions when the Solve or Clear button is pressed.
     * 
     * @param e The ActionEvent object.
     */
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == solveButton) 
        {
            solveButton.setVisible(false);
            clearButton.setVisible(true);
            int[][] board = new int[9][9];
            boolean invalidInput = false;
            try {
                // Put user input into a board array
                board = getBoardFromTextFields();
            } catch (Exception NumberFormatException) {
                invalidInput = true;
                playErrorSound();
            }
            SudokuSolver puzzle = new SudokuSolver(board); // Create new SudokuSolver object
            if (puzzle.solve() && !invalidInput) // If solution exists and inputs are valid
            {
                status.setForeground(new Color(0, 102, 0));
                status.setText("Successful To Solve");
                try {
                    // Set textfields with solved board
                    setBoardToTextFields(puzzle.getBoard());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            } else
            {
                status.setForeground(Color.red);
                status.setText("Unsuccessful To Solve");
                playErrorSound();
            }
        }
        if (e.getSource() == clearButton) {
            clearButton.setVisible(false);
            solveButton.setVisible(true);
            clearGrid();
            status.setForeground(Color.black);
            status.setText("Input Numbers");
        }
    }

    /**
     * Sets the textfields with the solved Sudoku inputs.
     * 
     * @param board The solved Sudoku board.
     */
    private void setBoardToTextFields(int[][] board) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                textField[x][y].setText(Integer.toString(board[x][y]));
            }
        }
        // Play a success sound
        playSuccessSound();
    }

    /**
     * Gets the user input from textfields and returns a board array.
     * 
     * @return User input in a board array.
     */
    private int[][] getBoardFromTextFields() {
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

    /**
     * Plays a success sound.
     */
    private void playSuccessSound() {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(getClass().getResource("FinishedSound.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Plays an error sound.
     */
    private void playErrorSound() {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(getClass().getResource("ErrorSound.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }   
}