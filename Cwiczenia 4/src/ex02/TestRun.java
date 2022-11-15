package ex02;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestRun {

    private static final int DURATION = 300;
    private static final List<Integer> testBounds = new ArrayList<>(List.of(1000, 10000, 100000));
    private static final List<Integer> testWorkers = new ArrayList<>(List.of(10, 100, 1000));

    public static void main(String[] args) throws InterruptedException, IOException {
        int experiment = 0;

        for (Integer testBound : testBounds) {
            for (Integer testWorker : testWorkers) {
                for (int bufferType = 0; bufferType < 2; bufferType++) {
                    StatsCollector producersStatsCollector = new StatsCollector();
                    StatsCollector consumersStatsCollector = new StatsCollector();

                    Buffer buffer = bufferType == 0 ? new NaiveBuffer(testBound) : new ImprovedBuffer(testBound);

                    List<Thread> threadList = new ArrayList<>();

                    for (int i = 0; i < testWorker; i++) {
                        Thread thread = new Thread(new Producer(buffer, producersStatsCollector));
                        threadList.add(thread);
                    }
                    for (int i = 0; i < testWorker; i++) {
                        Thread thread = new Thread(new Consumer(buffer, consumersStatsCollector));
                        threadList.add(thread);
                    }

                    for (Thread thread : threadList) {
                        thread.start();
                    }

//                    Thread.sleep(DURATION);

                    for (Thread thread : threadList) {
                        thread.interrupt();
//                        thread.join();
                    }

                    FileWriter fileWriter = new FileWriter("./src/ex02/result" + experiment + ".txt");
                    fileWriter.write(producersStatsCollector.toString());
                    fileWriter.write(consumersStatsCollector.toString());
                    fileWriter.close();

                    experiment++;
                }
            }
        }


    }
}
