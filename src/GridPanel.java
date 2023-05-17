import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.FlatDarkLaf;

/**
 * Custom panel class for the Sudoku grid. Paints the lines on the grid.
 */
public class GridPanel extends JPanel {
    static {
        // Set the FlatLaf Dark theme
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    GridPanel(GridLayout layout) {
        super(layout);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(UIManager.getColor("Separator.foreground"));
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
