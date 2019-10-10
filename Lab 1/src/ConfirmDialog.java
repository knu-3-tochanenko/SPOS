import javax.swing.*;

public class ConfirmDialog {
    public static boolean run() {
        int response = JOptionPane.showConfirmDialog(
                null,
                "Terminate?",
                "Confirm",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        return response == JOptionPane.YES_OPTION;
    }
}