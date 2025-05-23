from math import sin, cos, exp


# Функция 1
def func_1(x, y):
    return 2 * x - y


# Функция 2
def func_2(x, y):
    return y ** 2


# Функция 3
def func_3(x, y):
    return sin(x) - y


# Точное решение для функции 1 с начальным условием y(x0) = y0
def exact_solution_1(x, x0, y0):
    const = (y0 - 2 * x0 + 2) * exp(x0)
    return 2 * x - 2 + const * exp(-x)


# Точное решение для функции 2 с начальным условием y(x0) = y0
def exact_solution_2(x, x0, y0):
    if y0 == 0:
        return 0
    const = 1 / y0 + x0
    return 1 / (const - x)


# Точное решение для функции 3 с начальным условием y(x0) = y0
def exact_solution_3(x, x0, y0):
    const = -(1 / 2 * (sin(x0) - cos(x0)) - y0) * exp(x0)
    return 1 / 2 * (sin(x) - cos(x)) + const * exp(-x)


# Возвращает список функций, их решения и описание
def get_funcs():
    return [
        ("y' = 2x - y", (func_1, exact_solution_1)),
        ("y' = y^2", (func_2, exact_solution_2)),
        ("y' = sin(x) - y", (func_3, exact_solution_3))
    ]
