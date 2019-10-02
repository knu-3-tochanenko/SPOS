import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        testFG();
    }

    private static void testFG() throws IOException, InterruptedException {
        ProcessBuilder processBuilderF =
                new ProcessBuilder("java", "-jar", "f.jar", "0", "3000");
        ProcessBuilder processBuilderG =
                new ProcessBuilder("java", "-jar", "g.jar", "0", "10000");
        Process processF = processBuilderF.start();
        Process processG = processBuilderG.start();
        boolean f = false, g = false;
        while (true) {
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
            Thread.sleep(1000);
            System.out.print('.');
        }
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