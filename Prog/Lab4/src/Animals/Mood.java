package Animals;

public enum Mood {
    BAD("плохое"),
    NORMAL("нормальное"),
    GOOD("хорошее");
    private final String title;

    Mood(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
