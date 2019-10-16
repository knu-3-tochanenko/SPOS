import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class FunctionsRunner implements Callable<List<Integer>> {
    private int paramF, paramG, delayF, delayG;
    private static final int DELAY_BETWEEN_PROMPT = 3000;

    public FunctionsRunner(int paramF, int paramG, int delayF, int delayG) {
        this.paramF = paramF;
        this.paramG = paramG;
        this.delayF = delayF;
        this.delayG = delayG;
    }

    private int resultF = -2;
    private int resultG = -2;

    private void runCommand(Process process, String param) throws IOException, InterruptedException {
        ProcessBuilder suspend = new ProcessBuilder();
        suspend.command("bash", "-c", "\"kill -" + param + " " + process.pid() + "\"");
        Process command = suspend.start();
        command.waitFor();

        StringBuilder output = new StringBuilder();

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(command.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line + "\n");
        }

        int exitVal = command.waitFor();
        if (exitVal == 0) {
            System.out.println("Success!");
            System.out.println(output);
        }
    }

    private void suspendProcess(Process process) throws IOException, InterruptedException {
//        Process command = Runtime.getRuntime().exec("bash -c \"kill -STOP " + Long.toString(process.pid()) + "\"");
        runCommand(process, "STOP");
    }

    private void resumeProcess(Process process) throws IOException, InterruptedException {
//        Process command = Runtime.getRuntime().exec("bash -c \"kill -CONT " + Long.toString(process.pid()) + "\"");
        runCommand(process, "CONT");
    }

    public void runProcesses(int f, int g, int secondsF, int secondsG) throws InterruptedException, IOException {
        //find class path
//        String classPath = System.getProperty("java.class.path");
//        String[] classpathEntries = classPath.split(";");
//        String path = classpathEntries[0];

        String path = "C:\\Workspace\\Repos\\knu-3\\SPOS\\Lab 1\\out\\production\\Lab 1 Lite\\";

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

        AtomicInteger res = new AtomicInteger();
        res.set(0);

        Thread prompt;
        long time1 = System.currentTimeMillis(), time2 = time1;

        boolean needPrompt = true;

//        Thread butt = new Thread(new ButtonListener());
//        butt.start();

        while (true) {
//            if (!butt.isAlive()) {
//                System.out.println("Interrupted by input");
//                System.exit(0);
//            }

            time2 = System.currentTimeMillis();
            if (needPrompt && (time2 - time1 >= DELAY_BETWEEN_PROMPT)) {
//                butt.stop(); // I know what I am doing. Just kill it!
                suspendProcess(processF);
                suspendProcess(processG);
                prompt = new Thread(() -> {
                    res.set(ConfirmDialog.run());
                });
                prompt.start();
                prompt.join();
                if (res.get() == 3) {
                    System.out.println("Interrupted by input");
                    System.exit(0);
                } else if (res.get() == 1) {
                    time1 = System.currentTimeMillis();
                } else if (res.get() == 2) {
                    needPrompt = false;
                }
                resumeProcess(processF);
                resumeProcess(processG);
//                butt = new Thread(new ButtonListener());
//                butt.start();
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
    public List<Integer> call() throws InterruptedException, IOException {
        runProcesses(paramF, paramG, delayF, delayG);
        List<Integer> res = new ArrayList<>();
        res.add(resultF);
        res.add(resultG);
        return res;
    }
}
