package ex01;

public class SynchronizedCounter extends Counter {

    public SynchronizedCounter() {
        super();
    };

    @Override
    public synchronized void increment() {
        counter++;
    }
    @Override
    public synchronized void decrement() {
        counter--;
    }
}
