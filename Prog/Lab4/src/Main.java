import Animals.*;
import Items.*;
import Interfaces.TeaTime;

public class Main {
    public static void main(String[] args) {
        Winnie winnie = new Winnie();
        Piglet piglet = new Piglet();
        Rabbit rabbit = new Rabbit();
        Roo roo = new Roo();
        Eeyore eeyore = new Eeyore();

        Stick stick1 = new Stick(12);
        Stick stick2 = new Stick(3);
        Cone cone1 = new Cone();
        Cone cone2 = new Cone();
        Winnie.Clothes clothes = new Winnie.Clothes("красный");
        class Conversation {
            private final Animal first;
            private final Animal second;

            public Conversation(Animal animal1, Animal animal2) {
                first = animal1;
                second = animal2;
            }

            public void talk() {
                first.say("Привет, как настроение?");
                second.say("Привет, " + second.getMoodTitle());
            }
        }

        TeaTime teaTime = new TeaTime() {
            public void drinkTea(Animal animal) {
                System.out.println(animal.getName() + " пьёт чай");
            }

            public void eatCake(Animal animal) {
                System.out.println(animal.getName() + " ест пироженки");
            }
        };

        Conversation conversation = new Conversation(roo, rabbit);
        conversation.talk();
        teaTime.drinkTea(roo);
        teaTime.eatCake(rabbit);

        rabbit.say("Начали!");
        winnie.play(stick1, 100);
        roo.play(stick2, 100);
        rabbit.play(cone1, 100);
        piglet.play(cone2, 100);
        eeyore.swim();
        winnie.sleep();
    }
}
