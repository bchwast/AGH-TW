package ex03;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class Waiter {

    private final Lock lock = new ReentrantLock();
    private final Condition tableFree = lock.newCondition();
    private final List<Condition> pairNotifier = new ArrayList<>();

    private final boolean[] pairAwaiting;

    private int tableTakenBy = -1;

    public Waiter(int pairs) {
        pairAwaiting = new boolean[pairs];
        IntStream.range(0, pairs).mapToObj(i -> lock.newCondition()).forEach(pairNotifier::add);
    }

    public void free(int number) {
        lock.lock();

        if (pairAwaiting[tableTakenBy]) {
            System.out.println("Guest " + number + " from pair " + tableTakenBy + " left and freed the table");
            pairAwaiting[tableTakenBy] = false;
            tableTakenBy = -1;
            tableFree.signalAll();
        } else {
            System.out.println("Guest " + number + " from pair " + tableTakenBy + " left");
            pairAwaiting[tableTakenBy] = true;
        }

        lock.unlock();
    }

    public void reserve(int pair, int number) throws InterruptedException {
        lock.lock();

        if (!pairAwaiting[pair]) {
            System.out.println("Guest " + number + " from pair " + pair + " requested the table");
            pairAwaiting[pair] = true;
            while (pairAwaiting[pair]) {
                pairNotifier.get(pair).await();
            }
        } else {
            System.out.println("Guest " + number + " from pair " + pair + " also requested the table");
            pairAwaiting[pair] = false;
            pairNotifier.get(pair).signal();
        }

        while (tableTakenBy != -1 && tableTakenBy != pair) {
            tableFree.await();
        }
        tableTakenBy = pair;

        lock.unlock();
    }
}
