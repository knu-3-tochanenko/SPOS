public class ThreadTimeChecker implements Runnable {
    private static long TOO_LONG = 10_000;

    @Override
    public void run() {
        long time = System.currentTimeMillis();
        while (System.currentTimeMillis() - time < TOO_LONG) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (System.currentTimeMillis() - time >= TOO_LONG)
            System.exit(-1);
    }
}
