package ex02;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Printers {

    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    private final boolean[] available;
    private int count;

    public Printers(int available) {
        this.available = new boolean[available];
        Arrays.fill(this.available, true);
        count = available;
    }

    public void free(int index) throws InterruptedException {
        lock.lock();
        try {
            while (count >= available.length) {
                notFull.await();
            }
            available[index] = true;
            count++;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public int reserve() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            int index = 0;
            while (!available[index]) index++;
            available[index] = false;
            count--;
            notFull.signal();
            return index;
        } finally {
            lock.unlock();
        }
    }
}
