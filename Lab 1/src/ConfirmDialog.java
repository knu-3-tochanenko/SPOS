import javax.swing.*;

public class ConfirmDialog {
    public static boolean run() {
        JDialog.setDefaultLookAndFeelDecorated(true);
        int response = JOptionPane.showConfirmDialog(
                null,
                "Do you want to stop?",
                "Confirm",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        return response == JOptionPane.YES_OPTION;
    }
}
