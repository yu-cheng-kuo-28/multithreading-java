class ThreadA extends Thread {
    public void run() {
        Lock.obj.a();
    }
}

class ThreadB extends Thread {
    public void run() {
        Lock.obj.b();
    }
}

class Lock {
    public static Lock obj = new Lock();
    private boolean bExecuted = false;

    public synchronized void a() {
        System.out.println("Method A is prcoessing...");
        while (!bExecuted) {}
        System.out.println("Method A finished.");
    }

    public synchronized void b() {
        System.out.println("Method B is processing...");
        bExecuted = true;
        System.out.println("Method B finished.");
    }

}


public class Deadlock_01 {
    public static void main(String[] argv) {
        ThreadA ta = new ThreadA();
        ThreadB tb = new ThreadB();
        try {
            ta.start();
            tb.start();
            ta.join();
            tb.join();
        } catch (InterruptedException e) {}
    
    System.out.println("The program ended.");
    }
}
