from methods import *


def func(x, y):
    return 7 * x ** 2 + 3 * y ** 2 + 0.5 * x * y - 3 * x - 5 * y + 2


def func_dx(x, y):
    return 14 * x + 0.5 * y - 3


def func_dy(x, y):
    return 6 * y + 0.5 * x - 5


x0, y0 = 2, -2
epsilon = 0.0001

print("Метод покоординатного спуска")
res, vector, count = coordinate_descent(func, x0, y0, epsilon)
print("Значение функции:", res)
print("Найденный вектор:", *vector)
print("Количество итераций:", count)

print("Метод градиентного спуска")
res, vector, count = gradient_descent(func, x0, y0, epsilon, func_dx, func_dy)
print("Значение функции:", res)
print("Найденный вектор:", *vector)
print("Количество итераций:", count)

print("Метод наискорейшего спуска")
res, vector, count = fastest_descent(func, x0, y0, epsilon, func_dx, func_dy)
print("Значение функции:", res)
print("Найденный вектор:", *vector)
print("Количество итераций:", count)
