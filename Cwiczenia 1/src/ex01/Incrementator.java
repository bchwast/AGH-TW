package ex01;

public class Incrementator implements Runnable {
    private final Counter counter;

    public Incrementator(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100000; i++) {
            counter.increment();
        }
    }
}
