package ex01;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class Buffer {

    private final List<ReentrantLock> locks = new ArrayList<>();
    private final List<Condition> conditions = new ArrayList<>();
    private final List<Integer> processIds = new ArrayList<>();
    private final List<Data> pipeline = new ArrayList<>();
    private final int size;

    public Buffer(int size) {
        this.size = size;
        IntStream.range(0, size).forEach(i -> {
            pipeline.add(new Data(i, -1));
            ReentrantLock lock = new ReentrantLock();
            locks.add(lock);
            conditions.add(lock.newCondition());
            processIds.add(-1);
        });

    }

    public int getSize() {
        return size;
    }

    public Data getData(int i, int prevId) throws InterruptedException {
        locks.get(i).lock();
        while (processIds.get(i) != prevId) {
            conditions.get(i).await();
        }

        return pipeline.get(i);
    }

    public void finishProcess(int i, int id) {
        processIds.set(i, id);
        conditions.get(i).signalAll();
        locks.get(i).unlock();
    }

    public String getState() {
        return pipeline.toString() + '\n' + processIds;
    }

}
