package ex02;

import java.util.Random;

public class Producer implements Runnable {

    private final Buffer buffer;
    private final Random random = new Random();
    private final StatsCollector statsCollector;

    public Producer(Buffer buffer, StatsCollector statsCollector) {
        this.buffer = buffer;
        this.statsCollector = statsCollector;
    }

    public Producer(Buffer buffer) {
        this(buffer, new StatsCollector());
    }

    @Override
    public void run() {
        while (true) {
            int elements = random.nextInt(1, this.buffer.getM() + 1);
            Long start = System.nanoTime();
            try {
                buffer.put(elements);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Long stop = System.nanoTime();
            statsCollector.addEntry(stop - start, elements);
        }
    }
}
