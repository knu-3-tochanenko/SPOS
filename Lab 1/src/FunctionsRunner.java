import java.io.IOException;

public class FunctionsRunner implements Runnable {

    @Override
    public void run() {
        Thread buttonListener = new Thread(new ButtonListener());
        buttonListener.start();

        ProcessBuilder processBuilderF =
                new ProcessBuilder("java", "-jar", "f.jar", "0", "3000");
        ProcessBuilder processBuilderG =
                new ProcessBuilder("java", "-jar", "g.jar", "0", "10000");

        Process processF = null;
        try {
            processF = processBuilderF.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Process processG = null;
        try {
            processG = processBuilderG.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean f = false, g = false;
        while (true) {
            if (!buttonListener.isAlive()) {
                System.out.println("Interrupted by input!");
                System.exit(0);
            }
            if (!processF.isAlive() && !f) {
                f = true;
                if (getExitValue(processF, "F") == 0) {
                    destroy(processG, "G");
                    break;
                }
            }
            if (!processG.isAlive() && !g) {
                g = true;
                if (getExitValue(processG, "G") == 0) {
                    destroy(processF, "F");
                    break;
                }
            }
            if (!processF.isAlive() && !processG.isAlive())
                break;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Functions calculated!");
        System.exit(0);
    }

    private static int getExitValue(Process process, String name) {
        process.destroy();
        int exitValue = process.exitValue();
        System.out.println("\n" + name + " returned : " + exitValue);
        return exitValue;
    }

    private static void destroy(Process process, String name) {
        process.destroy();
        System.out.println("Destroyed " + name + " forcibly");
    }
}
