package edu.escuelaing.arep.app;
import org.junit.Test;
import java.io.IOException;

public class ConcurrentTest {

    @Test
    public void testConcurrentRequests() {
        int numThreads = 10;

        for (int i = 0; i < numThreads; i++) {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    try {
                        ThreadConcurrentTest.test();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
