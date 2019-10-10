import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class FunctionsRunner implements Callable<List<Integer>> {
    private int paramF, paramG, delayF, delayG;

    public FunctionsRunner(int paramF, int paramG, int delayF, int delayG) {
        this.paramF = paramF;
        this.paramG = paramG;
        this.delayF = delayF;
        this.delayG = delayG;
    }

    private int resultF = -2;
    private int resultG = -2;

    public void runProcesses(int f, int g, int secondsF, int secondsG) {
        //find class path
//        String classPath = System.getProperty("java.class.path");
//        String[] classpathEntries = classPath.split(";");
//        String path = classpathEntries[0];

        String path = "C:\\Workspace\\Repos\\knu-3\\SPOS\\Lab 1\\out\\production\\Lab 1 Lite\\";

        Thread buttonListener = new Thread(new ButtonListener());
        buttonListener.start();

        ProcessBuilder processBuilderF =
                new ProcessBuilder("java", "-jar", "f.jar",
                        Integer.toString(f), Integer.toString(secondsF * 1000));
        ProcessBuilder processBuilderG =
                new ProcessBuilder("java", "-jar", "g.jar",
                        Integer.toString(g), Integer.toString(secondsG * 1000));

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

        boolean fExecuted = false, gExecuted = false;
        while (true) {
            if (!buttonListener.isAlive()) {
                System.out.println("Interrupted by input!");
                System.exit(0);
            }
            if (!processF.isAlive() && !fExecuted) {
                fExecuted = true;
                resultF = processF.exitValue();
                if (getExitValue(processF, "F") == 0) {
                    destroy(processG, "G");
                    break;
                }
            }
            if (!processG.isAlive() && !gExecuted) {
                gExecuted = true;
                resultG = processG.exitValue();
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
        //System.exit(0);
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

    @Override
    public List<Integer> call() {
        runProcesses(paramF, paramG, delayF, delayG);
        List<Integer> res = new ArrayList<>();
        res.add(resultF);
        res.add(resultG);
        return res;
    }
}
