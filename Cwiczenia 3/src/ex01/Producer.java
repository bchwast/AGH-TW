package ex01;

public class Producer implements Runnable {
    private final Buffer buffer;
    private final int capabilities;

    public Producer(Buffer buffer, int capabilities) {
        this.buffer = buffer;
        this.capabilities = capabilities;
    }

    public void run() {
        for (int i = 0; i < capabilities; i++) {
            try {
                buffer.put("message "+i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("producer done");
    }
}
