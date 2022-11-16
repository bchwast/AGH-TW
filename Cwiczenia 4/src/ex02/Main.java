package ex02;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final int bound = 100;
    private static final int PRODUCERS = 5;
    private static final int CONSUMERS = 5;

    public static void main(String[] args) {
        Buffer buffer = new NaiveBuffer(bound);
//        Buffer buffer = new ImprovedBuffer(bound);

        List<Thread> threadList = new ArrayList<>();

        for (int i = 0; i < PRODUCERS; i++) {
            Thread thread = new Thread(new Producer(buffer));
            threadList.add(thread);
        }
        for (int i = 0; i < CONSUMERS; i++) {
            Thread thread = new Thread(new Consumer(buffer));
            threadList.add(thread);
        }

        threadList.forEach(Thread::start);
    }
}
