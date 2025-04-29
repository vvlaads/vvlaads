from math import sin, log


# Функция 1
def func1(x):
    return sin(x)


# Описание функции 1
def func1_description():
    return "sin(x)"


# Функция 2
def func2(x):
    return log(x)


# Описание функции 2
def func2_description():
    return "ln(x)"


# Список функций
def get_all_funcs():
    return [[func1_description(), func1], [func2_description(), func2]]
