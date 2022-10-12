package ex03;

import static ex03.Main.ILOSC;

public class Consumer implements Runnable {
    private final Buffer buffer;
    private int consumedCnt = 0;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        for (int i = 0; i < ILOSC; i++) {
            try {
                String message = buffer.take();
                consumedCnt++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("consumer done and consumed " + consumedCnt + " messages");
    }
}
