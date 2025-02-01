package utility.calculate;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Класс для хеширования паролей.
 */
public class HashPassword {
    /**
     * Хеширование пароля алгоритмом SHA-224.
     *
     * @param password исходный пароль
     * @return хэшированный пароль
     * @throws NoSuchAlgorithmException ошибка преобразования
     */
    public String hashPassword(String password) throws NoSuchAlgorithmException {
        byte[] bytes = password.getBytes(StandardCharsets.UTF_8);
        MessageDigest messageDigest = MessageDigest.getInstance("MD2");
        byte[] hash = messageDigest.digest(bytes);
        return byteToHex(hash);
    }

    /**
     * Преобразует массив байтов в строку с шестнадцатеричным видом
     *
     * @param bytes массив байтов
     * @return строка с шестнадцатеричным представлением массива байтов
     */
    public String byteToHex(byte[] bytes) {
        String hexString = "";
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xff & bytes[i]);
            if (hex.length() == 1) {
                hexString += '0';
            }
            hexString += hex;
        }
        return hexString;
    }
}
