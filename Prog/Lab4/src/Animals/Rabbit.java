package Animals;

public class Rabbit extends Animal {
    public Rabbit() {
        super("Кролик", 5, 56.0, 0.0, Mood.NORMAL);
    }

    @Override
    public void sleep() {
        System.out.println("Кролик " + getName() + " спит");
    }
}
