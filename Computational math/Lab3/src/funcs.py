def func_1(x):
    return x ** 3 - 3 * x ** 2 + 7 * x - 10


def func_2(x):
    return -x ** 3 - x ** 2 - 2 * x + 1


def func_3(x):
    return 5 * x ** 3 - 2 * x ** 2 + 3 * x - 15


def func_4(x):
    return 2 * x ** 3 - 3 * x ** 2 - 5 * x + 27


def func_5(x):
    return x ** 3 - 3 * x ** 2 + 6 * x - 28


def get_func_by_id(number):
    funcs = [func_1, func_2, func_3, func_4, func_5]
    return funcs[number - 1]


def get_all_funcs():
    return [
        (1, "x^3 - 3x^2 + 7x - 10"),
        (2, "-x^3 - x^2 - 2x + 1"),
        (3, "5x^3 - 2x^2 + 3x - 15"),
        (4, "2x^3 - 3x^2 - 5x + 27"),
        (5, "x^3 - 3x^2 + 6x - 28")
    ]
