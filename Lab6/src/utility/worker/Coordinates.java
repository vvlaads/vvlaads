package utility.worker;

import java.io.Serial;
import java.io.Serializable;

/**
 * Класс, представляющий расположение работника.
 */
public class Coordinates implements Serializable {
    @Serial
    private static final long serialVersionUID = 1002L;
    /**
     * Координата X.
     */
    private long x;
    /**
     * Координата Y.
     */
    private Integer y; //Значение поля должно быть больше -37, Поле не может быть null

    /**
     * Возвращает строковое представление расположения работника.
     *
     * @return строковое представление расположения работника
     */
    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + getX() +
                ", y=" + getY() +
                '}';
    }

    /**
     * Конструктор по умолчанию.
     */
    public Coordinates() {
        setY(0);
    }

    /**
     * Конструктор задает координату X и координату Y.
     *
     * @param x координата X
     * @param y координата Y
     */
    public Coordinates(long x, Integer y) {
        setX(x);
        setY(y);
    }

    /**
     * Возвращает координату X.
     *
     * @return координата X
     */
    public long getX() {
        return x;
    }

    /**
     * Устанавливает координату X.
     *
     * @param x координата X
     */
    public void setX(long x) {
        this.x = x;
    }

    /**
     * Возвращает координату Y.
     *
     * @return координата Y
     */
    public Integer getY() {
        return y;
    }

    /**
     * Устанавливает координату Y.
     *
     * @param y координата Y
     */
    public void setY(Integer y) {
        if (y != null && y > -37) {
            this.y = y;
        } else {
            System.out.println("Координата Y не удовлетворяет условиям. Выставлено значение по умолчанию");
            this.y = 0;
        }
    }
}
