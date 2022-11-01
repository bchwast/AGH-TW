package ex02;

import java.util.Random;

public class OfficeBloke implements Runnable {

    private final Random random = new Random();
    private final int number;
    private final Printers printers;

    public OfficeBloke(Printers printers, int number) {
        this.printers = printers;
        this.number = number;
    }

    private void prepare() throws InterruptedException {
        Thread.sleep(random.nextInt(3000));
    }

    private void print() throws InterruptedException {
        Thread.sleep(random.nextInt(5000));
    }

    @Override
    public void run() {
        while (true) {
            try {
                prepare();
                int index = printers.reserve();
                System.out.println("Office bloke " + number + " reserved printer " + index);
                print();
                printers.free(index);
                System.out.println("Office bloke " + number + " freed printer " + index);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
