package utility.worker;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.time.LocalDateTime;

/**
 * Класс, представляющий работников.
 */

@JsonPropertyOrder({"name", "coordinates", "salary", "position", "status", "organization"})
public class Worker implements Comparable<Worker>, Serializable {
    /**
     * Поле для сериализация.
     */
    @Serial
    private static final long serialVersionUID = 1000L;
    /**
     * Коллекция, хранящая значения ID работников.
     */
    @JsonIgnore
    private final static Deque<Long> idDeque = new ArrayDeque<>();
    /**
     * ID работника.
     */
    @JsonIgnore
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным,
    // Значение этого поля должно генерироваться автоматически
    /**
     * Имя работника.
     */
    private String name; //Поле не может быть null, Строка не может быть пустой
    /**
     * Расположение работника.
     */
    private Coordinates coordinates; //Поле не может быть null
    /**
     * Дата создания.
     */
    @JsonIgnore
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно
    // генерироваться автоматически
    /**
     * Зарплата работника.
     */
    private Long salary; //Поле может быть null, Значение поля должно быть больше 0
    /**
     * Должность работника.
     */
    private Position position; //Поле не может быть null
    /**
     * Статус работника.
     */
    private Status status; //Поле может быть null
    /**
     * Организация работника.
     */
    private Organization organization; //Поле не может быть null

    /**
     * Возвращает строковое представление работника.
     *
     * @return строковое представление работника
     */
    @Override
    public String toString() {
        return "Worker{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", coordinates=" + getCoordinates() +
                ", creationDate=" + getCreationDate() +
                ", salary=" + getSalary() +
                ", position=" + getPosition() +
                ", status=" + getStatus() +
                ", organization=" + getOrganization() +
                '}';
    }

    /**
     * Сравнивает работников по критериям и возвращает целое число:
     * <ul>
     * <li>положительное, если вызывающий объект больше объекта, переданного в качестве параметра;</li>
     * <li>отрицательное, если вызывающий объект меньше объекта, переданного в качестве параметра;</li>
     * <li>0, если объекты равны.</li>
     * </ul>
     *
     * @param worker работник, с которым хотим сравнить текущего работника
     * @return <ul>
     *      <li>положительное целое число, если вызывающий объект больше объекта, переданного в качестве параметра;</li>
     *      <li>отрицательное целое число, если вызывающий объект меньше объекта, переданного в качестве параметра;</li>
     *      <li>0, если объекты равны.</li>
     *      </ul>
     */
    public int compareTo(Worker worker) {
        return getName().compareTo(worker.getName());
    }

    /**
     * Конструктор по умолчанию.
     */
    public Worker() {
        setId();
        setName("Неизвестный");
        setCoordinates(new Coordinates());
        setCreationDate();
        setPosition(Position.LABORER);
        setOrganization(new Organization());
    }

    /**
     * Конструктор задает имя, расположение, зарплату, должность, статус и организацию работника.
     *
     * @param name         имя работника
     * @param coordinates  расположение работника
     * @param salary       зарплата работника
     * @param position     должность работника
     * @param status       статус работника
     * @param organization организация работника
     */
    public Worker(String name, Coordinates coordinates, Long salary, Position position, Status status,
                  Organization organization) {
        setId();
        setName(name);
        setCoordinates(coordinates);
        setCreationDate();
        setSalary(salary);
        setPosition(position);
        setStatus(status);
        setOrganization(organization);
    }

    /**
     * Возвращает ID работника.
     *
     * @return ID работника
     */
    public long getId() {
        return id;
    }

    /**
     * Устанавливает ID работника.
     */
    public void setId() {
        if (!idDeque.isEmpty()) {
            this.id = idDeque.getLast() + 1;
        } else {
            this.id = 1;
        }
        idDeque.addLast(this.id);
    }

    /**
     * Возвращает имя работника.
     *
     * @return имя работника
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает имя работника.
     *
     * @param name имя работника
     */
    public void setName(String name) {
        if (name != null && !name.isBlank()) {
            this.name = name;
        } else {
            System.out.println("Имя работника с ID:" + getId() + " не удовлетворяет условиям. " +
                    "Выставлено значение по умолчанию");
            this.name = "Неизвестный";
        }
    }

    /**
     * Возвращает расположение работника.
     *
     * @return расположение работника.
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Устанавливает расположение работника.
     *
     * @param coordinates расположение работника
     */
    public void setCoordinates(Coordinates coordinates) {
        if (coordinates != null) {
            this.coordinates = coordinates;
        } else {
            System.out.println("Расположение работника с ID:" + getId() + " не удовлетворяет условиям. " +
                    "Выставлено значение по умолчанию");
            new Coordinates();
        }
    }

    /**
     * Возвращает дату создания работника.
     *
     * @return дата создания работника
     */
    public java.time.LocalDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Устанавливает дату создания работника.
     */
    public void setCreationDate() {
        this.creationDate = LocalDateTime.now();
    }

    /**
     * Возвращает зарплату работника.
     *
     * @return зарплата работника
     */
    public Long getSalary() {
        return salary;
    }

    /**
     * Устанавливает зарплату работника.
     *
     * @param salary зарплата работника
     */
    public void setSalary(Long salary) {
        if (salary != null && salary > 0) {
            this.salary = salary;
        } else if (salary != null) {
            System.out.println("Зарплата работника с ID:" + getId() + " не удовлетворяет условиям. " +
                    "Выставлено значение по умолчанию");
            this.salary = null;
        } else {
            this.salary = null;
        }
    }

    /**
     * Возвращает должность работника.
     *
     * @return должность работника
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Устанавливает должность работника.
     *
     * @param position должность работника
     */
    public void setPosition(Position position) {
        if (position != null) {
            this.position = position;
        } else {
            System.out.println("Должность работника с ID:" + getId() + " не удовлетворяет условиям. " +
                    "Выставлено значение по умолчанию");
            this.position = Position.LABORER;
        }
    }

    /**
     * Возвращает статус работника.
     *
     * @return статус работника
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Устанавливает статус работника.
     *
     * @param status статус работника
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Возвращает организацию работника.
     *
     * @return организация работника
     */
    public Organization getOrganization() {
        return organization;
    }

    /**
     * Устанавливает организацию работника.
     *
     * @param organization организация работника
     */
    public void setOrganization(Organization organization) {
        if (organization != null) {
            this.organization = organization;
        } else {
            System.out.println("Организация работника с ID:" + getId() + " не удовлетворяет условиям. " +
                    "Выставлено значение по умолчанию");
            this.organization = new Organization();
        }
    }
}
