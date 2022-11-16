package ex02;

import java.util.Random;

public class Consumer implements Runnable {

    private final Buffer buffer;
    private final Random random = new Random();

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }


    @Override
    public void run() {
        while (true) {
            int elements = random.nextInt(1, this.buffer.getM() + 1);
            try {
                buffer.take(elements);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
