package Peoples;

public enum Gender {
    MALE("Мужской"),
    FEMALE("Женский");
    private final String title;

    Gender(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}

