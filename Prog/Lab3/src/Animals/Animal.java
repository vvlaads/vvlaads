package Animals;

import Interfaces.Sleep_able;

public abstract class Animal implements Sleep_able {
    private String name;
    private int age;
    private double height;
    private double location;

    public abstract void sleep();

    @Override
    public String toString() {
        return "Имя: " + this.name + ", Возраст: " + this.age + " лет, Рост: " + this.height + " см, Местоположение: "
                + this.location;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Animal that = (Animal) obj;
        if (age != that.age) return false;
        if (height != that.height) return false;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = name == null ? 0 : name.hashCode();
        result = 31 * result + age;
        result = 31 * result + (int) height;
        return result;
    }


    public final void setName(String name) {
        if (!this.name.equals(name)) {
            this.name = name;
        }
    }

    public final String getName() {
        return this.name;
    }

    public final void setAge(int age) {
        if (age >= 0) {
            this.age = age;
        }
    }

    public final int getAge() {
        return this.age;
    }

    public final void setHeight(double height) {
        if (height > 0) {
            this.height = height;
        }
    }

    public final double getHeight() {
        return this.height;
    }

    public final void setLocation(double location) {
        if (this.location != location) {
            this.location = location;

        }
    }

    public final double getLocation() {
        return this.location;
    }

    public Animal() {
        this.name = "Животное";
        this.age = 0;
        this.height = 1.0;
        this.location = 0.0;
    }

    public Animal(String name, int age, double height, double location) {
        this.name = name;
        this.setAge(age);
        this.setHeight(height);
        this.location = location;
    }
}
