package ex03;

public class Buffer {
    private String message;
    private boolean isEmpty = true;

    public synchronized String take() throws InterruptedException {
        while (isEmpty) {
            this.wait();
        }
        isEmpty = true;
        System.out.println("buffer clear");
        this.notifyAll();
        return message;
    }

    public synchronized void put(String message) throws InterruptedException {
        while (!isEmpty) {
            this.wait();
        }
        isEmpty = false;
        this.message = message;
        System.out.println("buffer full");
        this.notifyAll();
    }
}
