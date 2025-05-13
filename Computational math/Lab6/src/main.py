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
            a = digits[0]
            b = digits[1]
            if is_float(digits[0]) and is_float(digits[1]):
                a = float(a)
                b = float(b)
                if a != b:
                    if a > b:
                        print("Границы интервала изменены местами")
                        a, b = b, a
                    return a, b
        print("Некорректный ввод данных, повторите ввод")


# Ввод шага для интервала
def input_step(a, b):
    while True:
        print("Введите шаг h:")
        h = input_float()
        if h <= (b - a):
            return h
        print("Выбран слишком большой шаг, повторите ввод")


# Разбиение интервала с заданным шагом
def split_interval_by_step(a, b, h):
    x_values = []
    while a <= b:
        x_values.append(a)
        a += h
    return x_values


# Получить точное решение
def get_exact(y_start, x_values, exact_func):
    result = []
    x0 = x_values[0]
    for x in x_values:
        result.append(exact_func(x, x0, y_start))
    return result


# Вывод таблицы приближенных значений
def print_table(x_values, euler=None, euler_err=None, runge=None, runge_err=None, milne=None, milne_err=None,
                y_values=None):
    # Настройки ширины колонок
    widths = {
        "i": 5,
        "x": 10,
        "euler": 18,
        "euler_err": 20,
        "runge": 20,
        "runge_err": 23,
        "milne": 18,
        "milne_err": 20,
        "true": 18
    }

    # Формируем заголовок
    header = f"{'i':^{widths['i']}} | {'x':^{widths['x']}} |"
    if euler is not None:
        header += f" {'Метод Эйлера':^{widths['euler']}} |"
    if euler_err is not None:
        header += f" {'Погрешность Эйлера':^{widths['euler_err']}} |"
    if runge is not None:
        header += f" {'Метод Рунге-Кутта':^{widths['runge']}} |"
    if runge_err is not None:
        header += f" {'Погрешность Рунге-Кутта':^{widths['runge_err']}} |"
    if milne is not None:
        header += f" {'Метод Милна':^{widths['milne']}} |"
    if milne_err is not None:
        header += f" {'Погрешность Милна':^{widths['milne_err']}} |"
    if y_values is not None:
        header += f" {'Точное значение':^{widths['true']}} |"
    print(header)
    print("-" * len(header))

    # Формируем строки данных
    for i in range(len(x_values)):
        row = f"{i:^{widths['i']}} | {x_values[i]:^{widths['x']}.4f} |"
        if euler is not None:
            row += f" {euler[i]:^{widths['euler']}.6f} |"
        if euler_err is not None:
            row += f" {euler_err[i]:^{widths['euler_err']}.6f} |"
        if runge is not None:
            row += f" {runge[i]:^{widths['runge']}.6f} |"
        if runge_err is not None:
            row += f" {runge_err[i]:^{widths['runge_err']}.6f} |"
        if milne is not None:
            row += f" {milne[i]:^{widths['milne']}.6f} |"
        if milne_err is not None:
            row += f" {milne_err[i]:^{widths['milne_err']}.6f} |"
        if y_values is not None:
            row += f" {y_values[i]:^{widths['true']}.6f} |"
        print(row)
    print("-" * len(header))


# Вычисление значений и погрешности одношаговым методом
def calculate_one_step_method(name, method, p, a, b, h, y_start, func):
    try:
        x_values = split_interval_by_step(a, b, h)
        y_h = method(y_start, x_values, func)
        x_values = split_interval_by_step(a, b, h / 2)
        y_h2 = method(y_start, x_values, func)
        err = runge_rule(y_h, y_h2, p)
        return y_h, err
    except ValueError:
        print(f"Ошибка вычисления при использовании {name}")
        return None, None
    except ZeroDivisionError:
        print(f"Ошибка деления на ноль при использовании {name}")
        return None, None
    except OverflowError:
        print(f"Ошибка переполнения при использовании {name}")
        return None, None


# Вычисление погрешности для многошагового метода
def error_in_multi_step_method(y_values, result):
    errors = []
    if y_values is None or result is None:
        return None
    for i in range(len(y_values)):
        errors.append(abs(y_values[i] - result[i]))
    return errors


# Проверка, удовлетворяет ли решение заданной точности
def check_accuracy(errors, eps):
    if errors is None:
        return False
    for i in range(len(errors)):
        if abs(errors[i]) > eps:
            return False
    return True


# === MAIN ===
# Выбор функции
function, exact_function = select_one("Выберите одну из предложенных функций (Введите цифру)", get_funcs())

# Ввод исходных данных
start, end = input_interval()
step = input_step(start, end)
x_array = split_interval_by_step(start, end, step)

print("Значение y0:")
y0 = input_float()

print("Значение epsilon:")
epsilon = input_float()

# Вычисления
try:
    y_array = get_exact(y0, x_array, exact_function)
except ValueError:
    print(f"Ошибка вычисления при при вычислении точного решения.")
    y_array = None
except ZeroDivisionError:
    print("Ошибка: деление на ноль при вычислении точного решения.")
    y_array = None
except OverflowError:
    print("Ошибка: переполнение при вычислении точного решения.")
    y_array = None

result_1, err_1 = calculate_one_step_method("Метод Эйлера", euler_method, 1, start, end, step, y0, function)

result_2, err_2 = calculate_one_step_method("Метод Рунге-Кутта", runge_kutta_method, 4, start, end, step,
                                            y0, function)

if result_2 and len(result_2) >= 4:
    try:
        result_3 = milne_method(result_2[:4], x_array, function)
    except ValueError:
        print(f"Ошибка вычисления при использовании Метода Милна")
        result_3 = None
    except ZeroDivisionError:
        print(f"Ошибка деления на ноль при использовании Метода Милна")
        result_3 = None
    except OverflowError:
        print(f"Ошибка переполнения при использовании Метода Милна")
        result_3 = None
else:
    print("Для метода Милна требуется минимум 4 точки, полученные методом Рунге-Кутта")
    result_3 = None
err_3 = error_in_multi_step_method(y_array, result_3)

# Проверяем погрешность методов с введенной
if check_accuracy(err_1, epsilon):
    print(f"Метод Эйлера удовлетворяет заданной точности {epsilon}")
else:
    print(f"Метод Эйлера не удовлетворяет заданной точности {epsilon}")
if check_accuracy(err_2, epsilon):
    print(f"Метод Рунге-Кутта удовлетворяет заданной точности {epsilon}")
else:
    print(f"Метод Рунге-Кутта не удовлетворяет заданной точности {epsilon}")
if check_accuracy(err_3, epsilon):
    print(f"Метод Милна удовлетворяет заданной точности {epsilon}")
else:
    print(f"Метод Милна не удовлетворяет заданной точности {epsilon}")

# Вывод таблицы
print_table(x_array, result_1, err_1, result_2, err_2, result_3, err_3, y_array)

# Вывод графиков
if y_array is not None:
    plt.plot(x_array, y_array, label="Точное значение")
if result_1 is not None:
    plt.plot(x_array, result_1, label="Метод Эйлера", linestyle="--")
if result_2 is not None:
    plt.plot(x_array, result_2, label="Метод Рунге-Кутта", linestyle="--")
if result_3 is not None:
    plt.plot(x_array, result_3, label="Метод Милна", linestyle="--")
plt.legend()
plt.grid(True)
plt.show()
