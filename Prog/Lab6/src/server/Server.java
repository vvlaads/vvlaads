package server;

import utility.Request;
import utility.manager.FileManager;
import utility.parser.JacksonParser;
import utility.worker.Worker;
import utility.command.Argument;
import utility.command.Command;
import utility.manager.CommandManager;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс сервера.
 */
public class Server {
    /**
     * Порт сервера.
     */
    private int port;
    /**
     * Адрес сервера.
     */
    private InetSocketAddress serverAddress;
    /**
     * Адрес клиента.
     */
    private SocketAddress clientAddress;
    /**
     * Менеджер для работы с командами.
     */
    private CommandManager commandManager;
    /**
     * Канал для передачи данных.
     */
    private DatagramChannel datagramChannel;
    /**
     * Логическое значение
     * <ul>
     * <li>true, если сервер запущен</li>
     * <li>false, если сервер отключен.</li>
     * </ul>
     */
    private boolean running;
    /**
     * Логическое значение
     * <ul>
     * <li>true, если канал для передачи данных открыт</li>
     * <li>false, если канал для передачи данных закрыт.</li>
     * </ul>
     */
    private boolean opening;
    /**
     * Logger для отслеживания работы сервера
     */
    private static final Logger logger = Logger.getLogger("Server");

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
     * Возвращает адрес клиента.
     *
     * @return адрес клиента
     */
    public SocketAddress getClientAddress() {
        return clientAddress;
    }

    /**
     * Устанавливает адрес клиента.
     *
     * @param clientAddress адрес клиента
     */
    public void setClientAddress(SocketAddress clientAddress) {
        this.clientAddress = clientAddress;
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
     * Возвращает логическое значение
     * <ul>
     * <li>true, если сервер обрабатывает запросы</li>
     * <li>false, если сервер не обрабатывает запросы.</li>
     * </ul>
     *
     * @return логическое значение
     * <ul>
     *                <li>true, если сервер обрабатывает запросы</li>
     *                <li>false, если сервер не обрабатывает запросы</li>
     *                </ul>
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Устанавливает логическое значение
     * <ul>
     * <li>true, если сервер обрабатывает запросы</li>
     * <li>false, если сервер не обрабатывает запросы.</li>
     * </ul>
     *
     * @param running логическое значение
     *                <ul>
     *                               <li>true, если сервер обрабатывает запросы</li>
     *                               <li>false, если сервер не обрабатывает запросы</li>
     *                               </ul>
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * Возвращает логическое значение
     * <ul>
     * <li>true, если канал для передачи данных открыт</li>
     * <li>false, если канал для передачи закрыт.</li>
     * </ul>
     *
     * @return логическое значение
     * <ul>
     *                <li>true, если канал для передачи данных открыт</li>
     *                <li>false, если канал для передачи закрыт</li>
     *                </ul>
     */
    public boolean isOpening() {
        return opening;
    }

    /**
     * Устанавливает логическое значение
     * <ul>
     * <li>true, если канал для передачи данных открыт</li>
     * <li>false, если канал для передачи закрыт.</li>
     * </ul>
     *
     * @param opening логическое значение
     *                <ul>
     *                               <li>true, если канал для передачи данных открыт</li>
     *                               <li>false, если канал для передачи закрыт</li>
     *                               </ul>
     */
    public void setOpening(boolean opening) {
        this.opening = opening;
    }

    /**
     * Конструктор задает менеджер для работы с командами и порт сервера.
     *
     * @param commandManager менеджер для работы с командами
     * @param port           порт сервера
     */
    public Server(CommandManager commandManager, int port) {
        setPort(port);
        setServerAddress(new InetSocketAddress(getPort()));
        setCommandManager(commandManager);
        setOpening(false);
    }

    /**
     * Открывает канал для передачи данных.
     */
    public void openChannel() {
        try {
            setDatagramChannel(DatagramChannel.open());
            getDatagramChannel().bind(getServerAddress());
            setOpening(true);
            logger.log(Level.INFO, "Канал передачи данных открыт");
        } catch (IOException e) {
            logger.log(Level.WARNING, "Не удалось открыть сетевой канал");
        }
    }

    /**
     * Закрывает канал для передачи данных.
     */
    public void closeChannel() {
        try {
            getDatagramChannel().close();
            logger.log(Level.INFO, "Канал передачи данных закрыт");
        } catch (IOException e) {
            logger.log(Level.WARNING, "Не удалось закрыть сетевой канал");
        }
    }

    /**
     * Возвращает запрос, содержащий данные необходимые для выполнения команды.
     *
     * @return запрос, содержащий данные необходимые для выполнения команды
     */
    public Request readMessage() {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
            setClientAddress(getDatagramChannel().receive(buffer));
            byte[] bytes = buffer.array();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            logger.log(Level.INFO, "Получен запрос");
            return (Request) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            logger.log(Level.WARNING, "Ошибка десериализации запроса");
            return new Request();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Ошибка чтения");
            return new Request();
        }
    }

    /**
     * Отправляет сообщение клиенту по сетевому каналу.
     *
     * @param message сообщение для отправки
     */
    public void sendMessage(String message) {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(100 * 100);
            buffer.put(message.getBytes(StandardCharsets.UTF_16));
            buffer.flip();
            getDatagramChannel().send(buffer, getClientAddress());
            logger.log(Level.INFO, "Отправлен ответ клиенту");
        } catch (IOException e) {
            logger.log(Level.WARNING, "Ошибка отправки сообщения");
        }
    }

    /**
     * Выполняет команду по запросу и возвращает результат работы команды.
     *
     * @param request запрос, содержащий данные необходимые для выполнения команды
     * @return сообщение о выполнении команды
     */
    public String execute(Request request) {
        Command command = request.getCommand();
        Argument argument = request.getArgument();
        Worker worker = request.getWorker();
        Command serverCommand = getCommandManager().getCommand(command.getName());
        logger.log(Level.INFO, "Обработка запроса");
        return serverCommand.execute(argument, worker);
    }

    /**
     * Запускает сервер для обработки запросов от клиента.
     */
    public void run() {
        logger.log(Level.INFO, "Сервер запущен");
        setRunning(true);
        while (isRunning()) {
            if (!isOpening()) {
                openChannel();
            }
            Request request = readMessage();
            String message = execute(request);
            sendMessage(message);
            if (request.getCommand().getName().equals("exit")) {
                disconnectClient();
            }
        }
    }

    /**
     * Отключение клиента от сервера и закрытие канала.
     */
    public void disconnectClient() {
        FileManager fileManager = new FileManager(getCommandManager().getCollectionManager());
        try {
            JacksonParser jacksonParser = new JacksonParser();
            fileManager.write(jacksonParser.parseToJson(getCommandManager().getCollectionManager().getTreeMap()));
            logger.log(Level.INFO, "Изменения сохранены");
            closeChannel();
            setOpening(false);
            logger.log(Level.INFO, "Отключение клиента");
        } catch (IOException e) {
            logger.log(Level.WARNING, "Ошибка! Нельзя сохранить в файл");
        }
    }
}