package Animals;

import Interfaces.Bite_able;
import Peoples.*;

public class Bedbug extends Animal implements Bite_able {
    public Bedbug() {
        super("Клоп", 0, 0.02, 0.0);
    }

    public Bedbug(String name) {
        super(name, 0, 0.02, 0.0);
    }

    @Override
    public void sleep() {
        System.out.println("Клоп с именем " + getName() + " спит");
    }

    public void bite(Person person) {
        setLocation(person.getLocation());
        person.setMood(Mood.BAD);
        System.out.println("Клоп " + getName() + " укусил человека под именем " + person.getName() +
                ", Местоположение: " + getLocation());
    }
}
