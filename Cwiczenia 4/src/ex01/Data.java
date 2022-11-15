package ex01;

public class Data {

    private int value;
    private final int id;

    public Data(int id, int value) {
        this.id = id;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void updateValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "{ ID: " + id + ", value: " + value + " }";
    }
}
