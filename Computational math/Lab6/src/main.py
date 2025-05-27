from validators import *
from funcs import *
from methods import *
import matplotlib.pyplot as plt


# Ввод целого числа
def input_int():
    print("Введите целое число:")
    while True:
        value = input()
        if is_int(value):
            return int(value)
        print("Не удалось преобразовать в целое число, повторите ввод")


# Ввод числа с плавающей точкой
def input_float():
    print("Введите число с плавающей точкой:")
    while True:
        value = input()
        if is_float(value):
            return float(value)
        print("Не удалось преобразовать в число с плавающей точкой, повторите ввод")


# Выбор одной опции из предложенных
def select_one(message, options):
    while True:
        print(message)
        for i in range(len(options)):
            print(f"{i + 1}. {options[i][0]}")
        option = input_int()
        if 1 <= option <= len(options):
            return options[option - 1][1]
        print("Неверно выбрана опция, повторите ввод")


# Ввод интервала дифференцирования
def input_interval():
    while True:
        print("Введите интервал дифференцирования (два числа через пробел):")
        line = input()
        digits = line.split()
        if len(digits) == 2:
            start = digits[0]
            end = digits[1]
            if is_float(digits[0]) and is_float(digits[1]):
                start = float(start)
                end = float(end)
                if start != end:
                    if start > end:
                        print("Границы интервала изменены местами")
                        start, end = end, start
                    return start, end
        print("Некорректный ввод данных, повторите ввод")


# Ввод шага для интервала
def input_step(start, end):
    while True:
        print("Введите шаг h:")
        step = input_float()
        if step <= 0:
            print("Введите положительное значение!")
        elif step > (end - start):
            print("Выбран слишком большой шаг, повторите ввод")
        else:
            return step


# Разбиение интервала с заданным шагом
def split_interval_by_step(start, end, step):
    x_values = []
    x_current = start
    while x_current <= end:
        x_values.append(x_current)
        x_current += step
    return x_values


# Ввод погрешности
def input_epsilon():
    while True:
        print("Введите значение epsilon:")
        eps = input_float()
        if eps <= 0:
            print("Введите положительное значение!")
        elif eps < 1e-5:
            print("Введено слишком маленькое число!")
        else:
            return eps


# Получить точное решение
def get_exact(y0, x_values, exact_func):
    try:
        result = []
        x0 = x_values[0]
        for x in x_values:
            result.append(exact_func(x, x0, y0))
        return result
    except ValueError:
        print("Ошибка при вычислении функции")
        return None
    except OverflowError:
        print("Ошибка переполнения значений")
        return None
    except ZeroDivisionError:
        print("Ошибка, при вычислениях вызвано деление на 0")
        return None


# Вывод таблицы приближенных значений
def print_table(name, x_values, values, errors, y_values):
    # Настройки ширины колонок
    widths = {
        "i": 5,
        "x_values": 10,
        "values": 20,
        "errors": 30,
        "y_values": 20
    }

    # Формируем заголовок
    header = f"{'i':^{widths['i']}} | {'x':^{widths['x_values']}} |"
    header += f" {'Метод ' + name:^{widths['values']}} |"
    header += f" {'Погрешность':^{widths['errors']}} |"
    header += f" {'Точное значение':^{widths['y_values']}} |"
    print(header)
    print("-" * len(header))

    # Формируем строки данных
    for i in range(len(x_values)):
        row = f"{i:^{widths['i']}} | {x_values[i]:^{widths['x_values']}.4f} |"
        row += f" {values[i]:^{widths['values']}.6f} |"
        row += f" {errors[i]:^{widths['errors']}.6f} |"
        row += f" {y_values[i]:^{widths['y_values']}.6f} |"
        print(row)
    print("-" * len(header))


# Вычисление одношагового метода
def calculate_one_step_method(name, method, y0, x_values, func, p, eps, exact_y):
    print("*" * 100)
    print(f"Вычисление методом {name}")
    print("*" * 100)
    try:
        y_result, error_result = runge_rule(method, y0, x_values, func, p, eps)
        print_table(name, x_values, y_result, error_result, exact_y)
        print(f"Максимальная погрешность: {max(error_result)}")
        print("-" * 100)
        return y_result, error_result
    except ValueError:
        print("Ошибка при вычислении функции")
        return None, None
    except OverflowError:
        print("Ошибка переполнения значений")
        return None, None
    except ZeroDivisionError:
        print("Ошибка, при вычислениях вызвано деление на 0")
        return None, None


# Вычисление многошагового метода
def calculate_multi_step_method(name, method, y0, x_values, func, eps, exact_y):
    print("*" * 100)
    print(f"Вычисление методом {name}")
    print("*" * 100)
    try:
        y_result = method(y0, x_values, func, eps)
        error_result = error_in_multi_step_method(exact_y, y_result)
        print_table(name, x_values, y_result, error_result, exact_y)
        print(f"Максимальная погрешность: {max(error_result)}")
        print("-" * 100)
        return y_result, error_result
    except ValueError:
        print("Ошибка при вычислении функции")
        return None, None
    except OverflowError:
        print("Ошибка переполнения значений")
        return None, None
    except ZeroDivisionError:
        print("Ошибка, при вычислениях вызвано деление на 0")
        return None, None


# Вычисление погрешности для многошагового метода
def error_in_multi_step_method(y_values, values):
    errors = []
    for i in range(len(y_values)):
        errors.append(abs(y_values[i] - values[i]))
    return errors


# === MAIN ===
# Выбор функции
function, exact_function = select_one("Выберите одну из предложенных функций (Введите цифру)", get_funcs())

# === Ввод исходных данных ===
a, b = input_interval()
h = input_step(a, b)
x_array = split_interval_by_step(a, b, h)

print("Значение y0:")
y_start = input_float()

epsilon = input_epsilon()

# === Вычисления ===
y_exact = get_exact(y_start, x_array, exact_function)
if y_exact is None:
    print("Не удалось получить точное решение")
else:
    y_euler, error_euler = calculate_one_step_method("Эйлера", euler_method,
                                                     y_start, x_array, function, 1, epsilon, y_exact)
    y_runge, error_runge = calculate_one_step_method("Рунге-Кутта", runge_kutta_method,
                                                     y_start, x_array, function, 4, epsilon, y_exact)
    if y_runge is not None and len(y_runge) >= 4:
        y_milne, error_milne = calculate_multi_step_method("Милна", milne_method,
                                                           y_runge[:4], x_array, function, epsilon, y_exact)
    else:
        print("Нельзя вычислить методом Милна, так как недостаточно точек")
        y_milne, error_milne = None, None

    # Вывод графиков
    if y_exact is not None:
        plt.plot(x_array, y_exact, label="Точное значение", color="black")
    if y_euler is not None:
        plt.plot(x_array, y_euler, label="Метод Эйлера", linestyle="--", color="red")
    if y_runge is not None:
        plt.plot(x_array, y_runge, label="Метод Рунге-Кутта 4-го порядка", linestyle="--", color="blue")
    if y_milne is not None:
        plt.plot(x_array, y_milne, label="Метод Милна", linestyle="--", color="green")

    if y_exact is not None or y_euler is not None or y_runge is not None or y_milne is not None:
        plt.legend()
        plt.grid(True)
        plt.show()
    else:
        print("Невозможно построить графики")
