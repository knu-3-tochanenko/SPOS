import java.util.Scanner;

public class ButtonListener implements Runnable{
    public void run() {
        Scanner s = new Scanner(System.in);
        String ch;
        while (true) {
            ch = s.nextLine();
            if (ch.charAt(0) == 'q') {
                if (ConfirmDialog.run())
                    break;
            }
        }
    }
}
