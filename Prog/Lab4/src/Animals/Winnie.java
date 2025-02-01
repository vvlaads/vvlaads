package Animals;

public class Winnie extends Animal {
    public Winnie() {
        super("Винни-Пух", 5, 55.88, 0.0, Mood.NORMAL);
    }

    public static class Clothes {
        private String color;
        private final String KIND = "Футболка";

        public String getKind() {
            return KIND;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public Clothes(String color) {
            setColor(color);
        }
    }

    @Override
    public void sleep() {
        System.out.println("Медвежонок " + getName() + " спит");
    }

}
