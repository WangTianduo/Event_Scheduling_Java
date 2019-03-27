package algorithm;

public class Classroom {
    private int id;
    private String name;
    private String address;
    private int capacity;
    private  ClassType type;

    Classroom(String name, String address, int capacity, ClassType type,
              int id) {
        this.name = name;
        this.address = address;
        this.capacity = capacity;
        this.type = type;
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
