import java.time.LocalTime;

public class Multithreading_01 {
    public static void main(String[] args) {
        Runnable timeTask = () -> {
            while (true) {
                System.out.println(Thread.currentThread().getName() + ": " + LocalTime.now());
                try {
                    Thread.sleep(1000); // Pauses for 1 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread thread1 = new Thread(timeTask, "Thread 1");
        Thread thread2 = new Thread(timeTask, "Thread 2");

        thread1.start();
        thread2.start();
    }
}