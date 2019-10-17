import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;

class FunctionsRunnerTest {

    private List<Integer> testResult(int paramF, int paramG, int delayF, int delayG)
            throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<List<Integer>> resultCall = executor.submit(new FunctionsRunner(paramF, paramG, delayF, delayG));
        return resultCall.get();
    }

    @Test
    void test1() throws ExecutionException, InterruptedException {
        List<Integer> res = testResult(0, 0, 2, 8);
        assertEquals(res.get(0), 3);
        assertEquals(res.get(1), 5);
    }

    @Test
    void test2() throws ExecutionException, InterruptedException {
        List<Integer> res = testResult(4, 1, 8, 2);
        assertEquals(res.get(0), 3);
        assertEquals(res.get(1), 5);
    }

    @Test
    void test3() throws ExecutionException, InterruptedException {
        List<Integer> res = testResult(2, 1, 4, 10);
        assertEquals(res.get(0), 0);
    }

    @Test
    void test4() throws ExecutionException, InterruptedException {
        List<Integer> res = testResult(1, 3, 4, 10);
        assertEquals(res.get(1), 0);
    }
}