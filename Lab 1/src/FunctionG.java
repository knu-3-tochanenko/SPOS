import spos.lab1.demo.IntOps;

public class FunctionG {

    public static void main(String[] args) throws InterruptedException {
        //Thread timeChecker = new Thread(new ThreadTimeChecker());
        //timeChecker.start();
        System.out.println("G started...");
        Thread.sleep(Integer.parseInt(args[1]));
        System.out.println("G waited for " + args[1]);
        int res = IntOps.funcG(Integer.parseInt(args[0]));
        System.out.println("G value is " + res);
        System.exit(res);
    }
}
