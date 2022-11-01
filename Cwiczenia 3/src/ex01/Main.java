package ex01;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final int bound = 10;
    private static final int PRODUCERS = 5;
    private static final int CONSUMERS = 5;


    public static void main(String[] args) {
        Buffer buffer = new Buffer(bound);
        List<Thread> threadList = new ArrayList<>();

        for (int i = 0; i < PRODUCERS; i++) {
            Thread thread = new Thread(new Producer(buffer, 15));
            threadList.add(thread);
            thread.start();
        }
        for (int i = 0; i < CONSUMERS; i++) {
            Thread thread = new Thread(new Consumer(buffer, 15));
            threadList.add(thread);
            thread.start();
        }

        threadList.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
