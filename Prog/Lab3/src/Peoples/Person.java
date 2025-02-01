package Peoples;

import Interfaces.Sleep_able;
import Interfaces.Speak_able;

public class Person implements Sleep_able, Speak_able {
    private String name;
    private Gender gender;
    private int age;
    private double height;
    private double location;
    private Mood mood;

    public void sleep() {
        switch (this.mood) {
            case BAD:
                System.out.println(this.getName() + " спит с плохим настроением");
                break;
            case GOOD:
                System.out.println(this.getName() + " спит с хорошим настроением");
                break;
            default:
                System.out.println(this.getName() + " спит");
        }
    }

    public void say(String string) {
        if (this.mood == Mood.BAD) {
            System.out.println(this.name + " издает недовольные звуки");
        } else {
            System.out.println(this.name + ": " + string);
        }
    }

    @Override
    public String toString() {
        return "Имя: " + this.getName() + ", Пол: " + this.gender.getTitle() + ", Возраст: " + this.getAge() +
                " лет, Рост: " + this.getHeight() + " см, Местоположение: " + this.getLocation() + ", Настроение: "
                + this.mood.getTitle();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person that = (Person) obj;
        if (age != that.age) return false;
        if (height != that.height) return false;
        if (!gender.equals(that.gender)) return false;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = name == null ? 0 : name.hashCode();
        result = 31 * result + age;
        result = 31 * result + gender.hashCode();
        result = 31 * result + (int) (height * 10000);
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

    public final void setGender(Gender gender) {
        if (this.gender != gender) {
            this.gender = gender;
        }
    }

    public final Gender getGender() {
        return this.gender;
    }

    public final String getTitleGender() {
        return this.gender.getTitle();
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

    public final void setMood(Mood mood) {
        if (this.mood != mood) {
            this.mood = mood;
        }
    }

    public final Mood getMood() {
        return this.mood;
    }

    public final String getTitleMood() {
        return this.mood.getTitle();
    }


    public Person() {
        this.name = "Неизвестный";
        this.gender = Gender.MALE;
        this.age = 0;
        this.height = 1.0;
        this.location = 0.0;
        this.mood = Mood.NORMAL;
    }

    public Person(String name, Gender gender, int age, double height, double location, Mood mood) {
        this.name = name;
        this.gender = gender;
        this.setAge(age);
        this.setHeight(height);
        this.location = location;
        this.mood = mood;
    }
}
