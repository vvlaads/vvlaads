package Items;

import static java.lang.Math.abs;
import static java.lang.Math.max;

public class Stick extends Item {
    private int countLeaves;

    private class Leaf {
        private void fall() {
            System.out.println("У " + getName() + " опало " + getCountLeaves() + " листьев");
            countLeaves = 0;
        }
    }

    public Stick(int countLeaves) {
        super("Палка", 0.0);
        setCountLeaves(countLeaves);
    }

    public Stick() {
        super("Палка", 0.0);
        countLeaves = 0;
    }

    @Override
    public void fly(double location) {
        Leaf leaf = new Leaf();
        double distance = abs(getLocation() - location);
        setLocation(location);
        leaf.fall();
        System.out.println(getName() + " улетела на расстояние " + distance + " м");
    }

    @Override
    public String toString() {
        return "Название: " + getName() + ", Местоположение: " + getLocation() + ", Количество листьев: "
                + getCountLeaves();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Stick that = (Stick) obj;
        if (countLeaves != that.countLeaves) return false;
        return getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        int result = getName() == null ? 0 : getName().hashCode();
        result = 31 * result + countLeaves;
        return result;
    }

    public int getCountLeaves() {
        return countLeaves;
    }

    public void setCountLeaves(int countLeaves) {
        this.countLeaves = max(countLeaves, 0);
    }


}
