package utility.command;

import java.io.Serial;
import java.io.Serializable;

/**
 * Класс аргумента для команд.
 */
public class Argument implements Serializable {
    /**
     * Поле для сериализация.
     */
    @Serial
    private static final long serialVersionUID = 200L;
    /**
     * Название аргумента.
     */
    private String name;

    /**
     * Возвращает название аргумента.
     *
     * @return название аргумента
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает название аргумента.
     *
     * @param name название аргумента
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Конструктор задает название аргумента.
     *
     * @param name название аргумента
     */
    public Argument(String name) {
        setName(name);
    }

    /**
     * Пустой конструктор.
     */
    public Argument() {
    }
}
