public class JoinThread_01 extends Thread {
    public static volatile int n = 0;
       
    public void run() {
        for (int i = 0; i < 10; ++i, ++n) {
            try {
                sleep(3);
            } catch (Exception e) {}
        }
    }

    public static void main(String[] args) throws Exception {

        Thread threads[] = new Thread[100];

        for (int i = 0; i < threads.length; ++i) {
            threads[i] = new JoinThread_01();
        }

        for (int i = 0; i < threads.length; ++i) {
            threads[i].start();
        }

        // Ensure all threads complete their execution
        for (int i = 0; i < threads.length; ++i) {
            threads[i].join();
        }

        System.out.println("n = " + JoinThread_01.n);
    }
}
