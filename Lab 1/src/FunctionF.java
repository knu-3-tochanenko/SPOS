import spos.lab1.demo.IntOps;

public class FunctionF {

    public static void main(String[] args) throws InterruptedException {
        Thread timeChecker = new Thread(new ThreadTimeChecker());
        timeChecker.start();
        System.out.println("F started...");
        Thread.sleep(Integer.parseInt(args[1]));
        System.out.println("F waited for " + args[1]);
        int res = IntOps.funcF(Integer.parseInt(args[0]));
        System.out.println("F value is " + res);
        System.exit(res);
    }
}
