package client;

import utility.calculate.CreateWorker;
import utility.network.Request;
import utility.command.Argument;
import utility.command.Command;
import utility.manager.CommandManager;
import utility.worker.Worker;
import utility.network.ClientData;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Класс клиента.
 */
public class Client {
    /**
     * Данные клиента.
     */
    private ClientData clientData;
    /**
     * Порт сервера.
     */
    private int port;
    /**
     * Канал для передачи данных.
     */
    private DatagramChannel datagramChannel;
    /**
     * Адрес сервера.
     */
    private InetSocketAddress serverAddress;
    /**
     * Логическое значение
     * <ul>
     * <li>true, если клиент запущен</li>
     * <li>false, если клиент отключен.</li>
     * </ul>
     */
    private boolean running;
    /**
     * Менеджер для работы с командами.
     */
    private CommandManager commandManager;

    /**
     * Возвращает данные клиента.
     *
     * @return данные клиента
     */
    public ClientData getClientData() {
        return clientData;
    }

    /**
     * Устанавливает данные клиента.
     *
     * @param clientData данные клиента
     */
    public void setClientData(ClientData clientData) {
        this.clientData = clientData;
    }

    /**
     * Возвращает порт сервера.
     *
     * @return порт сервера
     */
    public int getPort() {
        return port;
    }

    /**
     * Устанавливает порт сервера.
     *
     * @param port порт сервера
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Возвращает канал для передачи данных.
     *
     * @return канал для передачи данных
     */
    public DatagramChannel getDatagramChannel() {
        return datagramChannel;
    }

    /**
     * Устанавливает сетевой канал для передачи данных
     *
     * @param datagramChannel сетевой канал для передачи данных
     */
    public void setDatagramChannel(DatagramChannel datagramChannel) {
        this.datagramChannel = datagramChannel;
    }

    /**
     * Возвращает адрес сервера.
     *
     * @return адрес сервера
     */
    public InetSocketAddress getServerAddress() {
        return serverAddress;
    }

    /**
     * Устанавливает адрес сервера.
     *
     * @param serverAddress адрес сервера
     */
    public void setServerAddress(InetSocketAddress serverAddress) {
        this.serverAddress = serverAddress;
    }

    /**
     * Возвращает логическое значение
     * <ul>
     * <li>true, если клиент обрабатывает запросы</li>
     * <li>false, если клиент не обрабатывает запросы.</li>
     * </ul>
     *
     * @return логическое значение
     * <ul>
     *                <li>true, если клиент обрабатывает запросы</li>
     *                <li>false, если клиент не обрабатывает запросы</li>
     *                </ul>
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Устанавливает логическое значение
     * <ul>
     * <li>true, если клиент обрабатывает запросы</li>
     * <li>false, если клиент не обрабатывает запросы.</li>
     * </ul>
     *
     * @param running логическое значение
     *                <ul>
     *                               <li>true, если клиент обрабатывает запросы</li>
     *                               <li>false, если клиент не обрабатывает запросы</li>
     *                               </ul>
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * Возвращает менеджер для работы с командами.
     *
     * @return менеджер для работы с командами
     */
    public CommandManager getCommandManager() {
        return commandManager;
    }

    /**
     * Устанавливает менеджер для работы с командами.
     *
     * @param commandManager менеджер для работы с командами
     */
    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    /**
     * Конструктор задает менеджер для работы с командами, информацию о клиенте и порт сервера.
     *
     * @param commandManager менеджер для работы с командами
     * @param port           порт сервера
     * @param clientData     информация о клиенте
     */
    public Client(CommandManager commandManager, int port, ClientData clientData) {
        setCommandManager(commandManager);
        setPort(port);
        setClientData(clientData);
    }

    /**
     * Открывает канал для передачи данных.
     */
    public void openChannel() {
        try {
            setDatagramChannel(DatagramChannel.open());
            getDatagramChannel().configureBlocking(false);
        } catch (IOException e) {
            System.out.println("Не удалось открыть сетевой канал");
        }
    }

    /**
     * Закрывает канал для передачи данных.
     */
    public void closeChannel() {
        try {
            getDatagramChannel().close();
        } catch (IOException e) {
            System.out.println("Не удалось закрыть сетевой канал");
        }
    }

    /**
     * Возвращает сообщение о выполнении команды.
     *
     * @return сообщение о выполнении команды
     */
    public String readMessage() {
        try {
            boolean get = false;
            byte[] bytes = null;
            int length = 0;
            long time = 0;
            while (!get) {
                time++;
                ByteBuffer buffer = ByteBuffer.allocate(1000 * 1000);
                SocketAddress address = getDatagramChannel().receive(buffer);
                length = buffer.position();
                bytes = buffer.array();
                if (address != null) {
                    get = true;
                } else if (time > 50000) {
                    return ("Сервер недоступен, повторите запрос:");
                }
            }
            return new String(bytes, 0, length, StandardCharsets.UTF_16);
        } catch (IOException e) {
            System.out.println("Ошибка принятия сообщения");
            System.exit(1);
            return "";
        }
    }

    /**
     * Отправляет запрос, содержащий данные необходимые для выполнения команды.
     *
     * @param request запрос, содержащий данные необходимые для выполнения команды
     */
    public void sendMessage(Request request) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(request);
            ByteBuffer buffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
            getDatagramChannel().send(buffer, getServerAddress());
        } catch (IOException e) {
            System.out.println("Не удалось отправить сообщение");
            System.exit(1);
        }
    }

    /**
     * Отправляет команду для выполнения на сервер и возвращает сообщение о выполнении.
     */
    public void readCommand() {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine() && isRunning()) {
            String line = sc.nextLine();
            Request request = new Request();
            if (!line.isBlank()) {
                String[] tokens = line.split(" ");
                Command command = getCommandManager().getCommand(tokens[0]);
                if (command != null) {
                    Argument argument = new Argument();
                    if (getCommandManager().getCommandsWithArguments().contains(command)) {
                        if (tokens.length == 2) {
                            argument.setName(tokens[1]);
                            request.setCommand(command);
                            request.setArgument(argument);
                            request.setClientData(getClientData());
                            if (getCommandManager().getCommandsWithCreateWorker().contains(command.getName())) {
                                CreateWorker createWorker = new CreateWorker();
                                Worker worker = createWorker.createWorker(sc);
                                request.setWorker(worker);
                            }
                            sendMessage(request);
                            String message = readMessage();
                            System.out.println(message);
                            if (request.getCommand().getName().equals("exit")) {
                                setRunning(false);
                                closeChannel();
                                break;
                            }
                        } else {
                            System.out.println("Неверный ввод аргумента для выбранной команды!");
                        }
                    } else if (tokens.length > 1) {
                        System.out.println("У выбранной команды отсутствуют аргументы!");
                    } else {
                        request.setCommand(command);
                        request.setArgument(argument);
                        request.setClientData(getClientData());
                        if (getCommandManager().getCommandsWithCreateWorker().contains(command.getName())) {
                            CreateWorker createWorker = new CreateWorker();
                            Worker worker = createWorker.createWorker(sc);
                            request.setWorker(worker);
                        }
                        sendMessage(request);
                        String message = readMessage();
                        System.out.println(message);
                        if (request.getCommand().getName().equals("exit")) {
                            setRunning(false);
                            closeChannel();
                            break;
                        }
                    }
                } else {
                    System.out.println("Неверно введена команда!");
                }
            }
        }
        sc.close();
    }

    /**
     * Запускает обработку запросов со стороны клиента.
     */
    public void run() {
        setServerAddress(new InetSocketAddress("localhost", getPort()));
        openChannel();
        setRunning(true);
        System.out.println("Введите команду: ");
        while (isRunning()) {
            readCommand();
        }
    }
}
