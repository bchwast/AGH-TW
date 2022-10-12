package ex02;

import ex01.Counter;
import ex01.Decrementator;
import ex01.Incrementator;

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

    public static void main(String[] args) throws InterruptedException {
        SynchronizedCounter counter = new SynchronizedCounter();
        Thread incrementator = new Thread(new Incrementator(counter));
        Thread decrementator = new Thread(new Decrementator(counter));

        incrementator.start();
        decrementator.start();

        incrementator.join();
        decrementator.join();

        System.out.println(counter.getCounter());
    }
}
