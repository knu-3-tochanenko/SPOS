import java.util.Scanner;

public class ButtonListener implements Runnable {
    @Override
    public void run() {
        Scanner s = new Scanner(System.in);
        String ch = s.nextLine();
        while (true) {
            if (ch.charAt(0) == 'q')
                if (ConfirmDialog.run())
                    break;
            ch = s.nextLine();
        }
    }
}
