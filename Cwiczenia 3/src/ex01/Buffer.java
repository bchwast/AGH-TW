package ex01;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    private final List<String> messages = new LinkedList<>();
    private final int bound;

    public Buffer(int bound) {
        this.bound = bound;
    }

    public String take() throws InterruptedException {
        lock.lock();
        try {
            while (messages.size() == 0) {
                notEmpty.await();
            }
            String message = messages.remove(messages.size() - 1);
            notFull.signal();
            return message;
        } finally {
            lock.unlock();
        }
    }

    public synchronized void put(String message) throws InterruptedException {
        lock.lock();
        try {
            while (messages.size() == bound) {
                notFull.await();
            }
            messages.add(message);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }
}
