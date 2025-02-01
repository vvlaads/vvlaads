import Animals.*;
import Peoples.*;

public class Main {
    public static void main(String[] args) {
        Person anna = new Person();
        Person victor = new Person("Виктор", Gender.MALE, 36, 178, 0.0, Mood.GOOD);
        Dunno dunno = new Dunno();
        Dunno dunno1 = new Dunno();
        Bedbug bug1 = new Bedbug();
        Rat rat1 = new Rat();
        Rat rat2 = new Rat("Лариса");
        Person victor1 = new Person("Виктор", Gender.FEMALE, 36, 178.0001, 100.0, Mood.BAD);
        Bedbug bug2 = new Bedbug("Клоп 2");

        dunno.setLocation(100);
        anna.setName("Анна");
        anna.setName("Анна");
        victor1.setGender(Gender.MALE);
        anna.setGender(Gender.FEMALE);
        anna.setAge(24);
        anna.setHeight(178.2);
        rat2.bite(victor);
        bug2.bite(dunno);
        victor.sleep();
        dunno.say("Всем привет");
        bug1.sleep();
        rat1.sleep();

        System.out.println(bug2.equals(bug1));
        System.out.println(dunno1.equals(dunno));
        System.out.println(dunno);
        System.out.println(anna);
        System.out.println(rat1);
        System.out.println(bug1);
        System.out.println(dunno.getLocation());
        System.out.println(dunno1.getLocation());
        System.out.println(dunno.getTitleMood());
        System.out.println(dunno.getMood());
        System.out.println(anna.getName());
        System.out.println(anna.getAge());
        System.out.println(anna.getHeight());
        System.out.println(anna.getTitleGender());
        System.out.println(anna.getGender());
        System.out.println(victor.hashCode());
        System.out.println(victor1.hashCode());

    }
}