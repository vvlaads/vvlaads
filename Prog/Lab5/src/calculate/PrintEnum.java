package calculate;

import worker.Position;
import worker.Status;
import worker.OrganizationType;

/**
 * Класс для вывода в командную строку списка констант типа Enum.
 */
public class PrintEnum {
    /**
     * Вывод в командную строку списка должностей.
     */
    public static void printEnumPosition() {
        System.out.println("------------");
        for (Position position : Position.values()) {
            System.out.println(position);
        }
        System.out.println("------------");
    }

    /**
     * Вывод в командную строку списка статусов.
     */
    public static void printEnumStatus() {
        System.out.println("------------");
        for (Status status : Status.values()) {
            System.out.println(status);
        }
        System.out.println("------------");
    }

    /**
     * Вывод в командную строку списка типов организаций.
     */
    public static void printEnumOrganizationType() {
        System.out.println("------------");
        for (OrganizationType organizationType : OrganizationType.values()) {
            System.out.println(organizationType);
        }
        System.out.println("------------");
    }
}
