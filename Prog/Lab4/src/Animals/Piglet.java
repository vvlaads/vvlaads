package Animals;

public class Piglet extends Animal {
    public Piglet() {
        super("Пятачок", 5, 27.5, 0.0, Mood.NORMAL);
    }

    @Override
    public void sleep() {
        System.out.println("Поросёнок " + getName() + " спит");
    }
}
