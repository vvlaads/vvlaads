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
    x_h = x_values
    y_h = method(y0, x_h, func)

    x_h2 = reduce_step_in_array(x_values)
    y_h2 = method(y0, x_h2, func)
    h2_index = 0
    errors = []
    step = x_h[1] - x_h[0]
    half_step = x_h2[1] - x_h2[0]
    for i in range(len(y_h)):
        error = abs(y_h[i] - y_h2[h2_index]) / (2 ** p - 1)
        print(f"Проверка точки {x_h[i]}:")
        print(f"  Значение при шаге {step}: y = {y_h[i]}")
        print(f"  Значение при шаге {half_step}: y = {y_h2[h2_index]}")
        if error > eps:
            symbol = ">"
        else:
            symbol = "<="
        print(f"  Погрешность: {error:.6f} {symbol} {eps}")
        if error > eps:
            print("-" * 100)
            print(f"Уменьшен шаг с {step} до {half_step}")
            print("-" * 100)
            return runge_rule(method, y0, x_h2, func, p, eps)
        errors.append(error)
        h2_index += 2
    print("-" * 100)
    print(f"Найден нужный шаг: {step}")
    print("-" * 100)
    return x_h, y_h, errors


# Метод Милна
def milne_method(y_start, x_values, func, eps):
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
        y_prev = y_predicated
        y_corr = y_predicated
        for j in range(100):
            y_corr = y_values[i - 2] + (h / 3) * (
                    func(x_values[i - 2], y_values[i - 2]) +
                    4 * func(x_values[i - 1], y_values[i - 1]) +
                    func(x_values[i], y_prev)
            )
            error = abs(y_corr - y_prev)
            print(f"Коррекция в точке {x_values[i]}:")
            print(f"  Предыдущее значение: y = {y_prev}")
            print(f"  Значение после коррекции: y = {y_corr}")
            if error > eps:
                symbol = ">"
            else:
                symbol = "<="
            print(f"  Погрешность: {error:.6f} {symbol} {eps}")
            if error < eps:
                break
            y_prev = y_corr

        y_values.append(y_corr)

    return y_values
