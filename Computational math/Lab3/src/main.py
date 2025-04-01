from validators import *
from funcs import *
from methods import *


def select_one(message, array):
    print(message)
    while True:
        for i in range(len(array)):
            print(array[i][0], ":", array[i][1])

        number = input_int()

        for i in range(len(array)):
            if number == array[i][0]:
                return number
        print("Не найдено такой опции, повторите ввод")


def input_float():
    while True:
        value = input()
        if is_float(value):
            return float(value)
        print("Введено не число, повторите ввод")


def input_int():
    while True:
        value = input()
        if is_int(value):
            return int(value)
        print("Введено не целое число, повторите ввод")


def input_limits():
    while True:
        print("Введите нижний предел интегрирования:")
        low_limit = input_float()
        print("Введите верхний предел интегрирования:")
        high_limit = input_float()
        if high_limit >= low_limit:
            return low_limit, high_limit
        print("Нижний предел не должен превышать верхний, повторите ввод")


def input_accuracy():
    print("Введите желаемую точность:")
    while True:
        value = input_float()
        if is_positive(value):
            return value
        print("Указана не положительная величина, повторите ввод")


func_number = select_one("Выберите функцию (введите соответствующее ей число)", get_all_funcs())
function = get_func_by_id(func_number)

method_number = select_one("Выберите метод (введите соответствующее ему число)", get_all_methods())
method = get_method_by_id(method_number)

a, b = input_limits()
epsilon = input_accuracy()
int_sum, n = method(a, b, function, epsilon)
print("Значение интеграла:", int_sum)
print("Количество отрезков для разбиения:", n)
