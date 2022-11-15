package ex01;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Operation {

    private final Buffer buffer;
    private final List<Thread> processors = new ArrayList<>();

    public void simulate() {
        processors.forEach(Thread::start);
        processors.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public Operation(int bufferSize, int processors) {
        this.buffer = new Buffer(bufferSize);
        Processor producer = new Processor(this.buffer, 0, -1, x -> 0);
        Processor consumer = new Processor(this.buffer, -1, processors, x -> -1);
        this.processors.add(new Thread(producer));
        IntStream.range(1, processors + 1)
                .mapToObj(i -> new Thread(new Processor(this.buffer, i, i - 1, x -> x + 1))).forEach(this.processors::add);
        this.processors.add(new Thread(consumer));
    }

    public static void main(String[] args) {
        int processors = 5;
        int bufferSize = 10;
        Operation operation = new Operation(bufferSize, processors);
        operation.simulate();
    }
}
