package Animals;

import static java.lang.Math.abs;

import Exceptions.*;
import Interfaces.*;
import Items.*;

public abstract class Animal implements Sleepable, Playable, Sayable {
    private String name;
    private int age;
    private double height;
    private double location;
    private Mood mood;

    public abstract void sleep();

    public final void say(String phrase) {
        System.out.println(getName() + ": " + phrase);
    }

    public final void go(double location) {
        double distance = abs(getLocation() - location);
        setLocation(location);
        System.out.println(getName() + " прошёл " + distance + " м, текущее местоположение: " + getLocation());
    }

    public final void cast(Item item, double location) {
        go(item.getLocation());
        System.out.println(getName() + " бросил предмет " + item.getName());
        item.fly(location);
    }

    public final void play(Item item, double location) {
        System.out.println(getName() + " играет в пустяки");
        cast(item, location);
        go(location);
        if (mood.ordinal() + 1 <= Mood.values().length - 1) {
            setMood(Mood.values()[mood.ordinal() + 1]);
            System.out.println("Текущее настроение у " + getName() + ": " + getMoodTitle());
        }
    }

    @Override
    public String toString() {
        return "Имя: " + name + ", Возраст: " + age + " лет, Рост: " + height + " см, Местоположение: " + location +
                ", Настроение: " + getMoodTitle();
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
        result = 31 * result + (int) height * 10000;
        return result;
    }

    public final String getName() {
        return name;
    }

    public final void setName(String name) {
        if (name == null) {
            try {
                throw new InvalidNameException();
            } catch (InvalidNameException e) {
                if (this.name == null) {
                    this.name = "Животное";
                }
            } finally {
                System.out.println("Имя объекта не может быть изменено на null, установлено имя: '" + getName() + "'");
            }
        } else if (this.name == null || !this.name.equals(name)) {
            this.name = name;
        }
    }

    public final int getAge() {
        return age;
    }

    public final void setAge(int age) {
        if (age >= 0) {
            this.age = age;
        }
    }

    public final double getHeight() {
        return height;
    }

    public final void setHeight(double height) {
        if (height > 0) {
            this.height = height;
        }
    }

    public final double getLocation() {
        return location;
    }

    public final void setLocation(double location) {
        if (this.location != location) {
            this.location = location;
        }
    }

    public final Mood getMood() {
        return mood;
    }

    public final String getMoodTitle() {
        return mood.getTitle();
    }

    public final void setMood(Mood mood) {
        if (mood == null) {
            try {
                throw new MoodException();
            } catch (MoodException e) {
                if (this.mood == null) {
                    this.mood = Mood.NORMAL;
                }
            } finally {
                System.out.println("Настроение не может быть изменено на null, установлено настроение: '"
                        + getMoodTitle() + "'");
            }
        } else if (this.mood == null || !this.mood.equals(mood)) {
            this.mood = mood;
        }
    }

    public Animal() {
        name = "Животное";
        age = 0;
        height = 1.0;
        location = 0.0;
        mood = Mood.NORMAL;
    }

    public Animal(String name, int age, double height, double location, Mood mood) {
        setName(name);
        setAge(age);
        setHeight(height);
        this.location = location;
        setMood(mood);
    }
}
