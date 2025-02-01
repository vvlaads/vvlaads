package Animals;

import Interfaces.Swimmable;

public class Eeyore extends Animal implements Swimmable {
    public Eeyore() {
        super("Иа-иа", 5, 35.0, 0.0, Mood.NORMAL);
    }

    public void swim() {
        System.out.println("Ослик " + getName() + " плывёт по реке");
    }

    @Override
    public void sleep() {
        System.out.println("Ослик " + getName() + " спит");
    }
}
