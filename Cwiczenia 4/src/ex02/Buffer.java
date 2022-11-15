package ex02;

public interface Buffer {

    void put(int elements) throws InterruptedException;
    void take(int elements) throws InterruptedException;
    int getM();
}
