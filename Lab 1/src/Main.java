import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Thread functionsRunner = new Thread(new FunctionsRunner());
        Thread buttonListener = new Thread(new ButtonListener());
        functionsRunner.start();
        buttonListener.start();
        System.out.println("Threads started");
        while (functionsRunner.isAlive() || buttonListener.isAlive()) {
            Thread.sleep(100);
            if (!functionsRunner.isAlive()) {
                System.out.println("Functions calculated!");
                System.exit(0);
            }

            if (!buttonListener.isAlive()) {
                System.out.println("Interrupted by input!");
                System.exit(0);
            }
        }
    }
}