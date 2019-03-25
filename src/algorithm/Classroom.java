package algorithm;

public class Classroom {
    private String name;
    private String address;
    private int capacity;
    private  ClassType type;

    Classroom(String name, String address, int capacity, ClassType type) {
        this.name = name;
        this.address = address;
        this.capacity = capacity;
        this.type = type;
    }
}
