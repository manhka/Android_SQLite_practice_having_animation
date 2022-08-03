package manhnguyen.myproject.studentmanager_sqlite_no_image;

public class Homework {
    private int id;
    private String name;

    public Homework() {
    }

    public Homework(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

