package calculate;

import worker.*;

import java.util.Scanner;

/**
 * Класс для создания рабочего через командную строку.
 */

public class CreateWorker {
    /**
     * Возвращает работника, созданного с помощью ввода данных из командной строки.
     *
     * @return работник, созданный с помощью ввода данных из командной строки
     */

    public static Worker createWorker() {
        Worker worker = new Worker();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Введите имя работника: ");
            String line = scanner.nextLine();
            if (!line.isEmpty()) {
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
        while (!positionIsWrite) {
            PrintEnum.printEnumPosition();
            System.out.println("Выберите должность из списка выше: ");
            String line = scanner.nextLine();
            switch (line) {
                case "DIRECTOR":
                    worker.setPosition(Position.DIRECTOR);
                    positionIsWrite = true;
                    break;
                case "LABORER":
                    worker.setPosition(Position.LABORER);
                    positionIsWrite = true;
                    break;
                case "HEAD_OF_DIVISION":
                    worker.setPosition(Position.HEAD_OF_DIVISION);
                    positionIsWrite = true;
                    break;
                case "DEVELOPER":
                    worker.setPosition(Position.DEVELOPER);
                    positionIsWrite = true;
                    break;
                case "COOK":
                    worker.setPosition(Position.COOK);
                    positionIsWrite = true;
                    break;
                default:
                    System.out.println("Некорректный ввод должности");
            }
        }

        boolean statusIsWrite = false;
        while (!statusIsWrite) {
            PrintEnum.printEnumStatus();
            System.out.println("Выберите статус из списка выше: ");
            String line = scanner.nextLine();
            switch (line) {
                case "":
                    statusIsWrite = true;
                    break;
                case "HIRED":
                    worker.setStatus(Status.HIRED);
                    statusIsWrite = true;
                    break;
                case "RECOMMENDED_FOR_PROMOTION":
                    worker.setStatus(Status.RECOMMENDED_FOR_PROMOTION);
                    statusIsWrite = true;
                    break;
                case "REGULAR":
                    worker.setStatus(Status.REGULAR);
                    statusIsWrite = true;
                    break;
                default:
                    System.out.println("Некорректный ввод статуса");
            }
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
        while (!typeIsWrite) {
            PrintEnum.printEnumOrganizationType();
            System.out.println("Выберите тип организации из списка выше: ");
            String line = scanner.nextLine();
            switch (line) {
                case "GOVERNMENT":
                    organization.setType(OrganizationType.GOVERNMENT);
                    typeIsWrite = true;
                    break;
                case "PRIVATE_LIMITED_COMPANY":
                    organization.setType(OrganizationType.PRIVATE_LIMITED_COMPANY);
                    typeIsWrite = true;
                    break;
                case "OPEN_JOINT_STOCK_COMPANY":
                    organization.setType(OrganizationType.OPEN_JOINT_STOCK_COMPANY);
                    typeIsWrite = true;
                    break;
                default:
                    System.out.println("Некорректный ввод типа");
            }
        }

        while (true) {
            System.out.println("Введите адрес организации: ");
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                break;
            } else if (line.length() <= 144) {
                Address address = new Address();
                address.setStreet(line);
                organization.setPostalAddress(address);
                break;
            }
        }
        worker.setOrganization(organization);
        return worker;
    }
}
