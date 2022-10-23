package ex01;

public class BinarySemaphore {
    private boolean down = false;

    public synchronized void setDown() throws InterruptedException {
        while (down) {
            wait();
        }
        down = true;
    }

    public synchronized void setUp() {
        down = false;
        notifyAll();
    }


}
