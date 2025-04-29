from src.input import *
from src.funcs import *
from src.methods import *
from src.validators import *
import numpy as np
import matplotlib.pyplot as plt


# Ввод числа с плавающей точкой из консоли
def input_float():
    print("Введите число с плавающей точкой:")
    while True:
        value = input()
        if is_float(value):
            return float(value)
        print("Введено не число с плавающей точкой, повторите ввод")


# Ввод целого числа из консоли
def input_int():
    print("Введите целое число:")
    while True:
        value = input()
        if is_int(value):
            return int(value)
        print("Введено не целое число, повторите ввод")


# Ввод натурального числа из консоли
def input_nat():
    print("Введите натуральное число:")
    while True:
        value = input()
        if is_nat(value):
            return int(value)
        print("Введено не натуральное число, повторите ввод")


# Выбрать одну опцию из предложенных
def select_one(message, options):
    print(message)
    while True:
        for i in range(len(options)):
            print(f'{i + 1}. {options[i][0]}')
        option = input_int()
        if 0 < option <= len(options):
            return options[option - 1][1]
        print("Не найдено такой опции, выберите повторно")


# Ввод строки таблицы x y
def input_row():
    while True:
        row = input()
        values = row.split()
        if len(values) == 2:
            if is_float(values[0]) and is_float(values[1]):
                return float(values[0]), float(values[1])
        print("Повторите ввод строки")


# Ввод таблицы x y
def input_table():
    print("Количество строк в таблице:")
    while True:
        size = input_nat()
        if size >= 2:
            break
        print("Для интерполяции необходимо минимум 2 точки")
    print("Введите значения x и y через пробел:")
    x_values = []
    y_values = []
    for i in range(size):
        x, y = input_row()
        x_values.append(x)
        y_values.append(y)
        print(f"Введено строк: {i + 1} из {size}")
    return size, x_values, y_values


# Чтение таблицы x y из файла
def read_file():
    print("Введите путь к файлу:")
    while True:
        filename = input()
        size = 0
        x_values = []
        y_values = []
        is_correct = True
        try:
            with open(filename, 'r') as file:
                for row in file:
                    size += 1
                    values = row.split()
                    if len(values) != 2:
                        is_correct = False
                        break
                    if not (is_float(values[0]) and is_float(values[1])):
                        is_correct = False
                        break
                    x_values.append(float(values[0]))
                    y_values.append(float(values[1]))
                if is_correct and size >= 2:
                    return size, x_values, y_values
            print("Некорректные данные в файле, выберите другой файл")
        except FileNotFoundError:
            print("Не найден файл, введите путь повторно")


# Ввод исследуемого интервала
def input_interval():
    while True:
        print("Начало интервала:")
        start = input_float()
        print("Конец интервала:")
        end = input_float()
        if start == end:
            print("Введена одна точка, повторите ввод")
        elif start > end:
            start, end = end, start
            print("Начало интервала больше конца интервала, значения были поменяны местами")
            break
        else:
            break
    print("Количество точек на интервале:")
    while True:
        count = input_nat()
        if count >= 2:
            break
        print("Для интерполяции необходимо минимум 2 точки")
    return count, np.linspace(start, end, count)


# Ввод аргумента для вычисления значения функции
def input_argument(low, high):
    print(f"Аргумент для вычисления значения функции (в интервале [{low}, {high}]):")
    while True:
        value = input_float()
        if low <= value <= high:
            return value
        print(f"Значение должно находится в интервале [{low}, {high}]")


# Получение массива значений функций по массиву x-ов
def get_func_values(x_values, function):
    y_values = []
    for i in range(len(x_values)):
        x = x_values[i]
        if func_is_correct(x, function):
            y_values.append(function(x))
        else:
            raise ValueError
    return y_values


# Получение таблицы x y по выбранной функции
def get_table_by_func(function):
    while True:
        try:
            size, x_values = input_interval()
            y_values = get_func_values(x_values, function)
            return size, x_values, y_values
        except ValueError:
            print("На выбранном интервале нельзя вычислить значение функции")


# Напечатать разделитель
def print_separator():
    print("-" * 50)


# Вычисление значения функции точки выбранным методом
def calculate(message, method, size, x_values, y_values, x):
    print(message)
    try:
        result = method(size, x_values, y_values, x)
        print(result)
    except ValueError:
        print("Невозможно выполнить интерполяцию выбранным методом")
    print_separator()


# === Main ===

# Ввод исходных данных
input_mode = select_one("Выберите формат ввода данных:", get_input_options())
if input_mode == InputMode.CONSOLE:
    n, x_arr, y_arr = input_table()
elif input_mode == InputMode.FILE:
    n, x_arr, y_arr = read_file()
else:
    func = select_one("Выберите функцию:", get_all_funcs())
    n, x_arr, y_arr = get_table_by_func(func)

# Вывод таблицы конечных разностей (если возможно построить)
if is_uniform_spacing(x_arr):
    print("Таблица конечных разностей:")
    print_table_finite_differences(n, x_arr, y_arr)
else:
    print("Нельзя построить таблицу конечных разностей, так как расстояние между значениями x не константно")

# Ввод аргумента для вычисления
min_value = min(x_arr)
max_value = max(x_arr)
argument = input_argument(min_value, max_value)
print_separator()

# Интерполяция соответствующими методами
message1 = "Интерполяция многочленом Лагранжа:"
calculate(message1, lagrange, n, x_arr, y_arr, argument)

message2 = "Интерполяция многочленом Ньютона с разделенными разностями:"
calculate(message2, newton_divided_differences, n, x_arr, y_arr, argument)

message3 = "Интерполяция многочленом Ньютона с конечными разностями:"
calculate(message3, newton_finite_differences, n, x_arr, y_arr, argument)

# Вывод графиков
plt.plot(x_arr, y_arr, label="Исходная функция")
plt.scatter(x_arr, y_arr, label="Узлы интерполяции")

x_newton = np.linspace(min_value, max_value, 100)
y_newton = []
for x_value in x_newton:
    y_newton.append(newton_divided_differences(n, x_arr, y_arr, x_value))
plt.plot(x_newton, y_newton, 'r--', label="Интерполяция многочленом Ньютона")
plt.scatter(argument, newton_divided_differences(n, x_arr, y_arr, argument), color="black", label="Искомое значение")

x_lagrange = np.linspace(min_value, max_value, 100)
y_lagrange = []
for x_value in x_lagrange:
    y_lagrange.append(lagrange(n, x_arr, y_arr, x_value))
plt.plot(x_lagrange, y_lagrange, '--', label="Интерполяция многочленом Лагранжа")

plt.grid(True)
plt.legend()
plt.show()
