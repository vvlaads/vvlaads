# Формирует массив с уменьшенным вдвое шагом от изначального
def reduce_step_in_array(x_values):
    result = []
    half_step = (x_values[1] - x_values[0]) / 2
    for i in range(len(x_values) - 1):
        result.append(x_values[i])
        result.append(x_values[i] + half_step)
    result.append(x_values[-1])
    return result


# Метод Эйлера
def euler_method(y0, x_values, func):
    h = x_values[1] - x_values[0]
    result = [y0]
    for i in range(len(x_values) - 1):
        x_current = x_values[i]
        y_current = result[-1]
        y_next = y_current + h * func(x_current, y_current)
        result.append(y_next)
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


# Правило Рунге
def runge_rule(method, y0, x_values, func, p, eps):
    errors = []

    x_h = x_values
    y_h = method(y0, x_h, func)
    step = x_values[1] - x_values[0]

    x_h2 = reduce_step_in_array(x_h)
    y_h2 = method(y0, x_h2, func)
    half_step = x_h2[1] - x_h2[0]

    h_index = 1
    h2_index = 2

    for i in range(1, len(x_values)):
        print(f"Проверка точки {x_values[i]}:")
        print(f"  Значение y при шаге {step}: {y_h[h_index]}")
        print(f"  Значение y при шаге {half_step}: {y_h2[h2_index]}")
        error = abs(y_h[i * h_index] - y_h2[i * h2_index]) / (2 ** p - 1)
        while error > eps and step > 1e-5:
            print(f"  Погрешность {error} > {eps}")
            print(f" Уменьшаем шаг до {half_step}")
            h_index *= 2
            h2_index *= 2
            x_h = x_h2
            y_h = y_h2
            x_h2 = reduce_step_in_array(x_h)
            y_h2 = method(y0, x_h2, func)
            step = half_step
            half_step = x_h2[1] - x_h2[0]
            error = abs(y_h[h_index] - y_h2[h2_index]) / (2 ** p - 1)
            print(f"  Значение y при шаге {step}: {y_h[h_index]}")
            print(f"  Значение y при шаге {half_step}: {y_h2[h2_index]}")
        if error < eps:
            print(f"  Погрешность {error} <= {eps}")
        elif step <= 1e-5:
            print(f"Уменьшение шага прекращено")

    y_result = []
    for i in range(len(x_values)):
        y_result.append(y_h[i * h_index])
        error = abs(y_h[i * h_index] - y_h2[i * h2_index]) / (2 ** p - 1)
        errors.append(error)
    return y_result, errors


# Метод Милна
def milne_method(y_start, x_values, func, eps):
    h = x_values[1] - x_values[0]
    result = y_start.copy()

    for i in range(4, len(x_values)):
        # Прогноз
        y_predicated = result[i - 4] + (4 * h / 3) * (
                2 * func(x_values[i - 3], result[i - 3]) -
                func(x_values[i - 2], result[i - 2]) +
                2 * func(x_values[i - 1], result[i - 1])
        )

        # Коррекция
        y_prev = y_predicated
        y_corr = y_predicated
        for j in range(100):
            y_corr = result[i - 2] + (h / 3) * (
                    func(x_values[i - 2], result[i - 2]) +
                    4 * func(x_values[i - 1], result[i - 1]) +
                    func(x_values[i], y_prev)
            )
            error = abs(y_corr - y_prev)
            if error < eps:
                break
            y_prev = y_corr

        result.append(y_corr)

    return result
