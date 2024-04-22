package worker;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Класс, представляющий организации.
 */
@JsonPropertyOrder({"fullName", "employeesCount", "type", "postalAddress"})
public class Organization {
    /**
     * Название организации.
     */
    private String fullName; //Длина строки не должна быть больше 943, Поле может быть null
    /**
     * Количество сотрудников.
     */
    private Integer employeesCount; //Поле не может быть null, Значение поля должно быть больше 0

    /**
     * Тип организации.
     */
    private OrganizationType type; //Поле не может быть null
    /**
     * Адрес организации.
     */
    private Address postalAddress; //Поле может быть null

    /**
     * Возвращает строковое представление организации.
     *
     * @return строковое представление организации
     */
    @Override
    public String toString() {
        return "Organization{" +
                "fullName='" + getFullName() + '\'' +
                ", employeesCount=" + getEmployeesCount() +
                ", type=" + getType() +
                ", postalAddress=" + getPostalAddress() +
                '}';
    }

    /**
     * Конструктор по умолчанию.
     */
    public Organization() {
        setEmployeesCount(1);
        setType(OrganizationType.PRIVATE_LIMITED_COMPANY);
    }

    /**
     * Конструктор задает название, количество сотрудников, тип и адрес организации.
     *
     * @param fullName       название организации
     * @param employeesCount количество сотрудников
     * @param type           тип организации
     * @param postalAddress  адрес организации
     */
    public Organization(String fullName, Integer employeesCount, OrganizationType type, Address postalAddress) {
        setFullName(fullName);
        setEmployeesCount(employeesCount);
        setType(type);
        setPostalAddress(postalAddress);
    }

    /**
     * Возвращает название организации.
     *
     * @return название организации
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Устанавливает название организации.
     *
     * @param fullName название организации
     */
    public void setFullName(String fullName) {
        if (fullName != null && fullName.length() <= 943) {
            this.fullName = fullName;
        }
    }

    /**
     * Возвращает количество сотрудников организации.
     *
     * @return количество сотрудников организации
     */
    public Integer getEmployeesCount() {
        return employeesCount;
    }

    /**
     * Устанавливает количество сотрудников организации.
     *
     * @param employeesCount количество сотрудников организации
     */
    public void setEmployeesCount(Integer employeesCount) {
        if (employeesCount != null && employeesCount > 0) {
            this.employeesCount = employeesCount;
        } else {
            this.employeesCount = 1;
        }
    }

    /**
     * Возвращает тип организации.
     *
     * @return тип организации
     */
    public OrganizationType getType() {
        return type;
    }

    /**
     * Устанавливает тип организации.
     *
     * @param type тип организации
     */
    public void setType(OrganizationType type) {
        this.type = type != null ? type : OrganizationType.PRIVATE_LIMITED_COMPANY;
    }

    /**
     * Возвращает адрес организации.
     *
     * @return адрес организации
     */
    public Address getPostalAddress() {
        return postalAddress;
    }

    /**
     * Устанавливает адрес организации.
     *
     * @param postalAddress адрес организации
     */
    public void setPostalAddress(Address postalAddress) {
        this.postalAddress = postalAddress;
    }
}