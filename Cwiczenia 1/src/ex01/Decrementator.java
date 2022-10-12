package ex01;

public class Decrementator implements Runnable {
    private final Counter counter;

    public Decrementator(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100000000; i++) {
            counter.decrement();
        }
    }
}
