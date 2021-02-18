package geekbrains.homework.lesson9;

public class Robot implements Treadmill, Wall {
    private String name;
    private int maxLength;
    private int maxHeight;

    Robot (String name, int maxLength, int maxHeight) {
        this.name = name;
        this.maxLength = maxLength;
        this.maxHeight = maxHeight;
    }

    public String getName () {
        return name;
    }

    @Override
    public boolean run(int distance) {
        return (maxLength > distance);
    }

    @Override
    public boolean jump(int wallHeight) {
        return (maxHeight > wallHeight);
    }
}
