package ex01;

public class Consumer implements Runnable {
    private final Buffer buffer;
    private int consumedCnt = 0;
    private final int appetite;

    public Consumer(Buffer buffer, int appetite) {
        this.buffer = buffer;
        this.appetite = appetite;
    }

    public void run() {
        for (int i = 0; i < appetite; i++) {
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
