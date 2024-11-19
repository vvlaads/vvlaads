package calculate;

/**
 * Класс для проверки преобразования строки в Long или Integer.
 */
public class Calculate {
    /**
     * Возвращает
     * <ul>
     *  <li>true, если строка может быть преобразована в тип Long;</li>
     *  <li>false, если строка не может быть преобразована в тип Long.</li>
     * </ul>
     *
     * @param string строка для проверки
     * @return <ul>
     *  <li>true, если строка может быть преобразована в тип Long;</li>
     *  <li>false, если строка не может быть преобразована в тип Long.</li>
     * </ul>
     */
    public static boolean stringIsLong(String string) {
        try {
            Long.parseLong(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Возвращает
     * <ul>
     *  <li>true, если строка может быть преобразована в тип Integer;</li>
     *  <li>false, если строка не может быть преобразована в тип Integer.</li>
     * </ul>
     *
     * @param string строка для проверки
     * @return <ul>
     *  <li>true, если строка может быть преобразована в тип Integer;</li>
     *  <li>false, если строка не может быть преобразована в тип Integer.</li>
     * </ul>
     */
    public static boolean stringIsInteger(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
