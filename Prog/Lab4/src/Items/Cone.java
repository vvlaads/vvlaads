package Items;

import static java.lang.Math.abs;

public class Cone extends Item {
    public Cone() {
        super("Шишка", 0.0);
    }

    @Override
    public void fly(double location) {
        double distance = abs(getLocation() - location);
        setLocation(location);
        System.out.println(getName() + " улетела на расстояние " + distance + " м");
    }
}
