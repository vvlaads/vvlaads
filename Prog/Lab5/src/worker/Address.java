package worker;

/**
 * Класс, представляющий адрес организации.
 */
public class Address {
    /**
     * Адрес организации.
     */
    private String street; //Длина строки не должна быть больше 144, Поле не может быть null

    /**
     * Возвращает строковое представление адреса организации.
     *
     * @return строковое представление адреса организации
     */
    @Override
    public String toString() {
        return "Address{" +
                "street='" + getStreet() + '\'' +
                '}';
    }

    /**
     * Конструктор по умолчанию.
     */
    public Address() {
    }

    /**
     * Конструктор задает адрес организации работника.
     *
     * @param street адрес организации
     */
    public Address(String street) {
        setStreet(street);
    }

    /**
     * Возвращает адрес организации.
     *
     * @return адрес организации
     */
    public String getStreet() {
        return street;
    }

    /**
     * Устанавливает адрес организации.
     *
     * @param street адрес организации
     */
    public void setStreet(String street) {
        if (street != null && street.length() <= 144) {
            this.street = street;
        } else {
            System.out.println("Адрес не удовлетворяет условиям. Выставлено значение по умолчанию");
            this.street = "Неизвестно";
        }
    }
}