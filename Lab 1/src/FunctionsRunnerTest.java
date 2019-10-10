import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;

class FunctionsRunnerTest {

    private void testResult(int paramF, int paramG, int delayF, int delayG, int resultF, int resultG)
            throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<List<Integer>> resultCall = executor.submit(new FunctionsRunner(paramF, paramG, delayF, delayG));
        List<Integer> res = resultCall.get();
        assertEquals(res.get(0), resultF);
        assertEquals(res.get(1), resultG);
    }

    @Test
    void test1() throws ExecutionException, InterruptedException {
        testResult(0, 0, 2, 8, 3, 5);
    }

    @Test
    void test2() throws ExecutionException, InterruptedException {
        testResult(4, 1, 8, 2, 3, 5);
    }

    @Test
    void test3() throws ExecutionException, InterruptedException {
        testResult(2, 1, 4, 10, 0, -2);
    }

    @Test
    void test4() throws ExecutionException, InterruptedException {
        testResult(1, 3, 4, 10, -2, 0);
    }
}