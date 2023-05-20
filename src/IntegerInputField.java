import javax.swing.*;
import javax.swing.text.*;

public class IntegerInputField extends JTextField {
    public IntegerInputField() {
        super();
        setDocument(new IntegerDocument());
    }

    private static class IntegerDocument extends PlainDocument {
        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            if (str == null) {
                return;
            }
            String currentText = getText(0, getLength());
            String newText = currentText.substring(0, offs) + str + currentText.substring(offs);

            try {
                int value = Integer.parseInt(newText);
                if (value >= 1 && value <= 9) {
                    super.insertString(offs, str, a);
                }
            } catch (NumberFormatException e) {
                // Ignore non-integer input
            }
        }
    }
}
