package Items;

import Exceptions.InvalidNameException;
import Interfaces.Flyable;

public abstract class Item implements Flyable {
    private String name;
    private double location;

    public abstract void fly(double location);

    @Override
    public String toString() {
        return "Название: " + name + ", Местоположение: " + location;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Item that = (Item) obj;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name == null ? 0 : name.hashCode();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        if (name == null) {
            try {
                throw new InvalidNameException();
            } catch (InvalidNameException e) {
                if (this.name == null) {
                    this.name = "Предмет";
                }
            } finally {
                System.out.println("Имя объекта не может быть изменено на null, установлено имя: '" + getName() + "'");
            }
        } else if (this.name == null || !this.name.equals(name)) {
            this.name = name;
        }
    }

    public double getLocation() {
        return this.location;
    }

    public void setLocation(double location) {
        if (this.location != location) {
            this.location = location;
        }
    }

    public Item() {
        name = "Предмет";
        location = 0.0;
    }

    public Item(String name, double location) {
        setName(name);
        this.location = location;
    }
}
