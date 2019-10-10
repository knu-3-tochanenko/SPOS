import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Thread functionsRunner = new Thread(new FunctionsRunner());
        System.out.println("Threads started");
        functionsRunner.start();
    }
}