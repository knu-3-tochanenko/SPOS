import spos.lab1.demo.IntOps;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        TimeUnit.SECONDS.sleep(Integer.parseInt(args[1]));
        int res = IntOps.funcG(Integer.parseInt(args[0]));
        System.exit(res);
    }
}
