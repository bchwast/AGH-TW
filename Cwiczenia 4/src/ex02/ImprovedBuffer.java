package ex02;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ImprovedBuffer implements Buffer {

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition firstProducerCondition = lock.newCondition();
    private final Condition restProducersCondition = lock.newCondition();
    private final Condition firstConsumerCondition = lock.newCondition();
    private final Condition restConsumersCondition = lock.newCondition();

    private final int M;
    private final int bound;
    private int current = 0;

    public ImprovedBuffer(int bound) {
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
        try {
            while (lock.hasWaiters(firstProducerCondition)) {
                restProducersCondition.await();
            }
            while (current + elements > bound) {
                firstProducerCondition.await();
            }
            current += elements;
            System.out.println("put: " + elements + ", buffer state: " + current + " taken of " + bound + " possible");
            restProducersCondition.signal();
            firstConsumerCondition.signal();
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public void take(int elements) throws InterruptedException {
        lock.lock();
        try {
            while (lock.hasWaiters(firstConsumerCondition)) {
                restConsumersCondition.await();
            }
            while (current - elements < 0) {
                firstConsumerCondition.await();
            }
            current -= elements;
            System.out.println("taken: " + elements + ", buffer state: " + current + " taken of " + bound + " possible");
            restConsumersCondition.signal();
            firstProducerCondition.signal();
        } finally {
            lock.unlock();
        }
    }
}
