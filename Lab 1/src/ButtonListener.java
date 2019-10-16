import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class ButtonListener implements Runnable {

    private final AtomicBoolean running = new AtomicBoolean(false);

    public void run() {
        Scanner s = new Scanner(System.in);
        String ch;
        while (true) {
            System.out.println("Q to quit: ");
            ch = s.nextLine();
            if (ch.charAt(0) == 'q') {
                break;
            }
        }
    }


}
