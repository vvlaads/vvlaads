package Animals;

import Interfaces.Bite_able;
import Peoples.*;

public class Rat extends Animal implements Bite_able {
    public Rat() {
        super("Крыса", 0, 1, 0.0);
    }

    public Rat(String name) {
        super(name, 0, 1, 0.0);
    }

    @Override
    public void sleep() {
        System.out.println("Крыса с именем " + getName() + " спит");
    }

    public void bite(Person person) {
        setLocation(person.getLocation());
        person.setMood(Mood.BAD);
        System.out.println("Крыса " + getName() + " укусила человека под именем " + person.getName() +
                ", Местоположение: " + getLocation());
    }
}
