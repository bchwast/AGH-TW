package ex02;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class NaiveBuffer implements Buffer {

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition producerCondition = lock.newCondition();
    private final Condition consumerCondition = lock.newCondition();

    private final int M;
    private final int bound;
    private int current = 0;

    public NaiveBuffer(int bound) {
        this.M = bound / 2;
        this.bound = bound;
    }


    @Override
    public int getM() {
        return M;
    }

    @Override
    public void put(int elements) throws InterruptedException {
        lock.lock();
        while (current + elements > bound) {
            producerCondition.await();
        }
        current += elements;
        System.out.println("put: " + elements + ", buffer state: " + current + " taken of " + bound + " possible");
        consumerCondition.signalAll();
        lock.unlock();
    }

    @Override
    public void take(int elements) throws InterruptedException {
        lock.lock();
        while (current - elements < 0) {
            consumerCondition.await();
        }
        current -= elements;
        System.out.println("taken: " + elements + ", buffer state: " + current + " taken of " + bound + " possible");
        producerCondition.signalAll();
        lock.unlock();
    }
}
