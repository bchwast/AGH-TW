package ex01;

public class Incrementator implements Runnable {
    private final Counter counter;

    public Incrementator(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        try {
            counter.semaphore.setDown();
            for (int i = 0; i < 10000000; i++) {
                counter.increment();
            }
            counter.semaphore.setUp();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
