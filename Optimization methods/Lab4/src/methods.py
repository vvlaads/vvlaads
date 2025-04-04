import math


def half_division_method(a, b, epsilon, func):
    for k in range(1, 1000):
        x1 = (a + b - epsilon) / 2
        x2 = (a + b + epsilon) / 2
        if func(x1) > func(x2):
            a = x1
        else:
            b = x2

        if b - a <= 2 * epsilon:
            break
    xm = (a + b) / 2
    return xm


def coordinate_descent(func, x0, y0, epsilon):
    count_of_iteration = 0
    vector = [0, 0]
    result = 0
    x_cur = x0
    y_cur = y0

    for n in range(1, 1000):
        def f1(x):
            return func(x, y_cur)

        def f2(y):
            return func(x_cur, y)

        count_of_iteration = n
        x_start = x_cur
        y_start = y_cur

        a = x_cur - 1
        b = x_cur + 1
        x_cur = half_division_method(a, b, epsilon, f1)

        a = y_cur - 1
        b = y_cur + 1
        y_cur = half_division_method(a, b, epsilon, f2)

        if abs(func(x_cur, y_cur) - func(x_start, y_start)) <= epsilon:
            result = func(x_cur, y_cur)
            vector[0] = x_cur
            vector[1] = y_cur
            break

    return result, vector, count_of_iteration


def gradient_descent(func, x0, y0, epsilon, f_dx, f_dy):
    alpha = 1
    result = 0
    vector = [0, 0]
    count_of_iteration = 0
    x_cur = x0
    y_cur = y0
    for n in range(1, 1000):
        count_of_iteration = n
        x1 = x_cur - alpha * f_dx(x_cur, y_cur)
        y1 = y_cur - alpha * f_dy(x_cur, y_cur)
        if func(x1, y1) > func(x_cur, y_cur):
            alpha /= 10
        if abs(func(x1, y1) - func(x_cur, y_cur)) < epsilon:
            vector[0] = x1
            vector[1] = y1
            result = func(x1, y1)
            break
        x_cur = x1
        y_cur = y1

    return result, vector, count_of_iteration


def fastest_descent(func, x0, y0, epsilon, f_dx, f_dy):
    x_cur = x0
    y_cur = y0
    result = 0
    vector = [0, 0]
    count_of_iteration = 0
    for n in range(1, 1000):
        count_of_iteration = n
        f1 = f_dx(x_cur, y_cur)
        f2 = f_dy(x_cur, y_cur)
        norm = math.sqrt(f1 ** 2 + f2 ** 2)
        if norm < epsilon:
            vector[0] = x_cur
            vector[1] = y_cur
            result = func(x_cur, y_cur)
            break

        def alpha_func(value):
            return func(x_cur - value * f1, y_cur - value * f2)

        alpha = half_division_method(-100, 100, epsilon, alpha_func)

        x_cur -= alpha * f1
        y_cur -= alpha * f2
    return result, vector, count_of_iteration
