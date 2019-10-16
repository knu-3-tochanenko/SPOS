import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<List<Integer>> resultCall = executor.submit(new FunctionsRunner(2, 3, 1, 1));
        List<Integer> res = resultCall.get();
//        System.out.println("F res is " + res.get(0));
//        System.out.println("G res is " + res.get(1));
        executor.shutdown();
    }
}