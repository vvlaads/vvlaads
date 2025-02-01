package Peoples;

public enum Mood {
    BAD("Плохое"),
    NORMAL("Обычное"),
    GOOD("Хорошее");
    private final String title;

    Mood(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
