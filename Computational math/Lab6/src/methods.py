# Метод Эйлера
def euler_method(y0, x_values, func):
    h = x_values[1] - x_values[0]
    result = [y0]
    for i in range(len(x_values) - 1):
        x_current = x_values[i]
        y_current = result[-1]

        y_current = y_current + h * func(x_current, y_current)
        result.append(y_current)
    return result


# Метод Рунге-Кутта 4-го порядка
def runge_kutta_method(y0, x_values, func):
    h = x_values[1] - x_values[0]
    result = [y0]
    for i in range(len(x_values) - 1):
        x_current = x_values[i]
        y_current = result[-1]

        k1 = h * func(x_current, y_current)
        k2 = h * func(x_current + h / 2, y_current + k1 / 2)
        k3 = h * func(x_current + h / 2, y_current + k2 / 2)
        k4 = h * func(x_current + h, y_current + k3)

        y_next = y_current + (k1 + 2 * k2 + 2 * k3 + k4) / 6
        result.append(y_next)

    return result


# Метод Милна
def milne_method(y_start, x_values, func):
    h = x_values[1] - x_values[0]
    y_values = y_start.copy()

    for i in range(4, len(x_values)):
        # Прогноз
        y_predicated = y_values[i - 4] + (4 * h / 3) * (
                2 * func(x_values[i - 3], y_values[i - 3]) -
                func(x_values[i - 2], y_values[i - 2]) +
                2 * func(x_values[i - 1], y_values[i - 1])
        )

        # Коррекция
        y_corr = y_values[i - 2] + (h / 3) * (
                func(x_values[i - 2], y_values[i - 2]) +
                4 * func(x_values[i - 1], y_values[i - 1]) +
                func(x_values[i], y_predicated)
        )

        y_values.append(y_corr)

    return y_values


# Оценка погрешности по правилу Рунге
def runge_rule(y_h, y_h2, p):
    result = []
    for i in range(len(y_h)):
        result.append(abs(y_h[i] - y_h2[i]) / (2 ** p - 1))
    return result
