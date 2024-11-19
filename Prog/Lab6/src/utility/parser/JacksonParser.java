package utility.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import utility.worker.Worker;

import java.io.*;
import java.util.*;

/**
 * Класс для преобразования JSON-объектов в Java-объекты.
 */
public class JacksonParser {
    /**
     * Возвращает Коллекцию TreeMap, полученную в ходе преобразования строки, содержащей JSON-объекты.
     *
     * @param json строка, содержащая JSON-объекты
     * @return коллекция TreeMap, хранящая работников с целочисленными ключами
     * @throws JsonProcessingException ошибка десериализации строки
     */
    public TreeMap<Integer, Worker> parseFromJson(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        TreeMap<Integer, Worker> workerTreeMap;
        workerTreeMap = objectMapper.readValue(json, new TypeReference<>() {
        });
        return workerTreeMap;
    }

    /**
     * Возвращает строку, полученную в ходе преобразования коллекции TreeMap в формат JSON.
     *
     * @param workerTreeMap коллекция TreeMap, содержащая работников с целочисленными ключами
     * @return строка в формате JSON
     * @throws IOException ошибка сериализации коллекции
     */
    public String parseToJson(TreeMap<Integer, Worker> workerTreeMap) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(workerTreeMap);
    }
}
