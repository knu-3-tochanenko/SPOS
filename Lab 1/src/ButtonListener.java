import java.util.Scanner;

public class ButtonListener implements Runnable {
    @Override
    public void run() {
        Scanner s = new Scanner(System.in);
        String ch = s.nextLine();
        while (ch.charAt(0) != 'q')
            ch = s.nextLine();
    }
}
