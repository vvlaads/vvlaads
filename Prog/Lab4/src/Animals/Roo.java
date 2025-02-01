package Animals;

public class Roo extends Animal {
    public Roo() {
        super("Крошка ру", 5, 21.0, 0.0, Mood.NORMAL);
    }

    @Override
    public void sleep() {
        System.out.println("Кенгурёнок " + getName() + " спит");
    }
}
