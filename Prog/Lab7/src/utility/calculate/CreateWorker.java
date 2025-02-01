package utility.calculate;

import utility.worker.*;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Класс для создания рабочего через командную строку.
 */

public class CreateWorker {
    /**
     * Возвращает работника, созданного с помощью ввода данных из командной строки.
     *
     * @param scanner сканер, через который заполняются поля
     * @return работник, созданный с помощью ввода данных из командной строки
     * @throws NoSuchElementException ошибка отсутствия строк, получаемых из сканера
     */

    public Worker createWorker(Scanner scanner) throws NoSuchElementException {
        Worker worker = new Worker();
        while (true) {
            System.out.println("Введите имя работника: ");
            String line = scanner.nextLine();
            if (!line.isBlank()) {
                worker.setName(line);
                break;
            }
            System.out.println("Некорректный ввод имени");
        }

        Coordinates coordinates = new Coordinates();
        while (true) {
            System.out.println("Введите значение координаты X: ");
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                break;
            } else if (Calculate.stringIsLong(line)) {
                coordinates.setX(Long.parseLong(line));
                break;
            }
            System.out.println("Некорректный ввод значения X");
        }

        while (true) {
            System.out.println("Введите значение координаты Y: ");
            String line = scanner.nextLine();
            if (Calculate.stringIsInteger(line)) {
                if (Integer.parseInt(line) > -37) {
                    coordinates.setY(Integer.parseInt(line));
                    break;
                }
            }
            System.out.println("Некорректный ввод значения Y");
        }
        worker.setCoordinates(coordinates);

        while (true) {
            System.out.println("Введите значение Зарплаты: ");
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                break;
            } else if (Calculate.stringIsLong(line)) {
                if (Long.parseLong(line) > 0) {
                    worker.setSalary(Long.parseLong(line));
                    break;
                }
            }
            System.out.println("Некорректный ввод значения зарплаты");
        }

        boolean positionIsWrite = false;
        while (true) {
            PrintEnum.printEnumPosition();
            System.out.println("Выберите должность из списка выше: ");
            String line = scanner.nextLine();
            for (Position position : Position.values()) {
                if (line.equals(position.name())) {
                    worker.setPosition(position);
                    positionIsWrite = true;
                }
            }
            if (positionIsWrite) {
                break;
            }
            System.out.println("Некорректный ввод должности");
        }

        boolean statusIsWrite = false;
        while (true) {
            PrintEnum.printEnumStatus();
            System.out.println("Выберите статус из списка выше: ");
            String line = scanner.nextLine();
            for (Status status : Status.values()) {
                if (line.equals(status.name())) {
                    worker.setStatus(status);
                    statusIsWrite = true;
                } else if (line.isEmpty()) {
                    worker.setStatus(null);
                    statusIsWrite = true;
                }
            }
            if (statusIsWrite) {
                break;
            }
            System.out.println("Некорректный ввод статуса");
        }

        Organization organization = new Organization();
        while (true) {
            System.out.println("Введите название организации: ");
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                break;
            } else if (line.length() <= 943) {
                organization.setFullName(line);
                break;
            }
            System.out.println("Некорректный ввод имени");
        }

        while (true) {
            System.out.println("Введите количество работников: ");
            String line = scanner.nextLine();
            if (Calculate.stringIsInteger(line)) {
                if (Integer.parseInt(line) > 0) {
                    organization.setEmployeesCount(Integer.parseInt(line));
                    break;
                }
            }
            System.out.println("Некорректный ввод количества работников");
        }

        boolean typeIsWrite = false;
        while (true) {
            PrintEnum.printEnumOrganizationType();
            System.out.println("Выберите тип организации из списка выше: ");
            String line = scanner.nextLine();
            for (OrganizationType type : OrganizationType.values()) {
                if (line.equals(type.name())) {
                    organization.setType(type);
                    typeIsWrite = true;
                }
            }
            if (typeIsWrite) {
                break;
            }
            System.out.println("Некорректный ввод типа организации");
        }

        while (true) {
            System.out.println("Введите адрес организации: ");
            String line = scanner.nextLine();
            if (line.length() <= 144 && !line.isEmpty()) {
                Address address = new Address();
                address.setStreet(line);
                organization.setPostalAddress(address);
                break;
            } else if (line.isEmpty()) {
                organization.setPostalAddress(null);
                break;
            }
            System.out.println("Некорректный ввод адреса");
        }
        worker.setOrganization(organization);
        return worker;
    }
}
