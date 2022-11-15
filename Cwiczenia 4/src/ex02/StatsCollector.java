package ex02;

import java.util.LinkedList;
import java.util.List;

public class StatsCollector {

    private final List<Long> times = new LinkedList<>();
    private final List<Integer> sizes = new LinkedList<>();

    public StatsCollector() {
    }

    public synchronized void addEntry(Long time, Integer size) {
        this.times.add(time);
        this.sizes.add(size);
    }

    @Override
    public String toString() {
        return "sizes: " + sizes + " times: " + times;
    }
}
