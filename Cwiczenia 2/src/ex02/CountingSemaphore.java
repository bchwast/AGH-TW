package ex02;

public class CountingSemaphore {
    private int cnt;

    public CountingSemaphore(int cnt) {
        this.cnt = cnt;
    }

    public synchronized void setDown(int number) throws InterruptedException {
        while (cnt <= 0) {
            wait();
        }
        cnt--;
        System.out.println(number + " cameIn, baskets left: " + cnt);
    }

    public synchronized void setUp(int number) {
        cnt++;
        System.out.println(number + " left, baskets left: " + cnt);
        notifyAll();
    }
}
