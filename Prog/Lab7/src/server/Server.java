package server;

import utility.network.ClientData;
import utility.database.DatabaseManager;
import utility.network.Request;
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
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.concurrent.*;
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
     * Менеджер для работы с базой данных.
     */
    private DatabaseManager databaseManager;
    private final ExecutorService readPool = Executors.newCachedThreadPool();
    private final ExecutorService executePool = Executors.newCachedThreadPool();
    private final ExecutorService sendPool = Executors.newCachedThreadPool();

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
     * Возвращает менеджер для работы с командами.
     *
     * @return менеджер для работы с командами
     */
    public synchronized CommandManager getCommandManager() {
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
    public synchronized DatagramChannel getDatagramChannel() {
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
     * Возвращает менеджер для работы с базой данных.
     *
     * @return менеджер для работы с базой данных
     */
    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    /**
     * Устанавливает менеджер для работы с базой данных.
     *
     * @param databaseManager менеджер для работы с базой данных
     */
    public void setDatabaseManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    /**
     * Конструктор задает менеджеры для работы с командами и базой данных, а также порт сервера.
     *
     * @param commandManager  менеджер для работы с командами
     * @param databaseManager менеджер для работы с базой данных
     * @param port            порт сервера
     */

    public Server(CommandManager commandManager, DatabaseManager databaseManager, int port) {
        setPort(port);
        setDatabaseManager(databaseManager);
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
            getDatagramChannel().configureBlocking(false);
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
    public synchronized Request readMessage() {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1000 * 1000);
            SocketAddress socketAddress = getDatagramChannel().receive(buffer);
            byte[] bytes = buffer.array();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            logger.log(Level.INFO, Thread.currentThread().getName() + ": Получен запрос");
            Request request1 = (Request) objectInputStream.readObject();
            request1.getClientData().setSocketAddress(socketAddress);
            return request1;
        } catch (ClassNotFoundException e) {
            logger.log(Level.WARNING, "Ошибка десериализации запроса");
            return null;
        } catch (IOException e2) {
            return null;
        }
    }

    /**
     * Отправляет сообщение клиенту по сетевому каналу.
     *
     * @param message сообщение для отправки
     * @param request запрос пользователя
     */
    public synchronized void sendMessage(String message, Request request) {
        try {
            SocketAddress socketAddress = request.getClientData().getSocketAddress();
            ByteBuffer buffer = ByteBuffer.allocate(1000 * 1000);
            buffer.put(message.getBytes(StandardCharsets.UTF_16));
            buffer.flip();
            getDatagramChannel().send(buffer, socketAddress);
            logger.log(Level.INFO, Thread.currentThread().getName() + ": Отправлен ответ клиенту");
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
    public synchronized String execute(Request request) {
        ClientData clientData = request.getClientData();
        boolean authorized;
        try {
            authorized = getDatabaseManager().authorizedUser(clientData);
        } catch (SQLException | NoSuchAlgorithmException e) {
            authorized = false;
        }
        if (authorized) {
            Command command = request.getCommand();
            Argument argument = request.getArgument();
            Worker worker = request.getWorker();
            getCommandManager().setClientData(clientData);
            Command serverCommand = getCommandManager().getCommand(command.getName());
            logger.log(Level.INFO, Thread.currentThread().getName() + ": Обработка запроса");
            return serverCommand.execute(argument, worker);
        } else {
            logger.log(Level.WARNING, "Ошибка авторизации клиента");
            return "Ошибка авторизации пользователя";
        }
    }

    /**
     * Запускает сервер для обработки запросов от клиента.
     *
     * @throws InterruptedException ошибка прерывания потока
     */
    public void start() throws InterruptedException {
        logger.log(Level.INFO, "Сервер запущен");
        setRunning(true);
        while (isRunning()) {
            if (!isOpening()) {
                openChannel();
            }
            Request request = readMessage();
            if (request != null) {
                String message = execute(request);
                sendMessage(message, request);
                if (request.getCommand().getName().equals("exit")) {
                    disconnectClient();
                }
            }
        }
    }

    /**
     * Запускает сервер в многопоточном режиме.
     *
     * @throws Exception возможные ошибки сервера
     */
    public void startMultiThreading() throws Exception {
        logger.log(Level.INFO, "Сервер запущен с многопоточностью");
        setRunning(true);
        while (isRunning()) {
            if (!isOpening()) {
                openChannel();
            }
            Future<Request> futureRequest = readPool.submit(this::readMessage);
            Request request1 = futureRequest.get();
            if (request1 != null) {
                Future<String> futureString = executePool.submit(() -> {
                    return execute(request1);
                });
                sendPool.execute(() -> {
                    try {
                        sendMessage(futureString.get(), request1);
                        if (request1.getCommand().getName().equals("exit")) {
                            disconnectClient();
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            Future<Request> futureRequest2 = readPool.submit(this::readMessage);
            Request request2 = futureRequest2.get();
            if (request2 != null) {
                Future<String> futureString = executePool.submit(() -> {
                    return execute(request2);
                });
                sendPool.execute(() -> {
                    try {
                        sendMessage(futureString.get(), request2);
                        if (request2.getCommand().getName().equals("exit")) {
                            disconnectClient();
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
    }

    /**
     * Останавливает сервер.
     */
    public void stop() {
        logger.log(Level.WARNING, "Остановка сервера");
        disconnectClient();
        setRunning(false);
    }

    /**
     * Отключение клиента от сервера и закрытие канала.
     */
    public void disconnectClient() {
        logger.log(Level.INFO, "Изменения сохранены");
        closeChannel();
        setOpening(false);
        logger.log(Level.INFO, "Отключение клиента");
    }
}