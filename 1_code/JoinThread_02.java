import java.util.concurrent.atomic.AtomicInteger;

public class JoinThread_02 extends Thread {
    public static AtomicInteger n = new AtomicInteger(0);
       
    public void run() {
        for (int i = 0; i < 10; ++i) {
            n.incrementAndGet();
            try {
                sleep(3);
            } catch (Exception e) {}
        }
    }

    public static void main(String[] args) throws Exception {
        Thread threads[] = new Thread[100];

        for (int i = 0; i < threads.length; ++i) {
            threads[i] = new JoinThread_02();
        }

        for (int i = 0; i < threads.length; ++i) {
            threads[i].start();
        }

        for (int i = 0; i < threads.length; ++i) {
            threads[i].join();
        }

        System.out.println("n = " + JoinThread_02.n.get());
    }
}
