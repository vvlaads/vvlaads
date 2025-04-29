from validators import *


# Многочлен Лагранжа
def lagrange(size, x_values, y_values, value):
    result = 0
    for i in range(size):
        l_i = 1
        for j in range(size):
            if i != j:
                l_i *= (value - x_values[j]) / (x_values[i] - x_values[j])
        result += y_values[i] * l_i
    return result


# Многочлен Ньютона с разделенными разностями
def newton_divided_differences(size, x_values, y_values, value):
    differences = get_divided_differences(size, x_values, y_values)
    result = y_values[0]
    product = 1
    for i in range(size - 1):
        product *= (value - x_values[i])
        result += differences[i][0] * product
    return result


# Многочлен Ньютона с конечными разностями
def newton_finite_differences(size, x_values, y_values, value):
    if not is_uniform_spacing(x_values):
        raise ValueError("x_values должны быть равномерно распределены")

    differences = get_finite_differences(size, y_values)
    h = x_values[1] - x_values[0]
    if abs(value - x_values[0]) <= abs(value - x_values[-1]):
        t = (value - x_values[0]) / h
        result = y_values[0]
        multiplier = 1
        for i in range(1, size):
            multiplier *= (t - (i - 1)) / i
            result += differences[i - 1][0] * multiplier
    else:
        t = (value - x_values[-1]) / h
        result = y_values[-1]
        multiplier = 1
        for i in range(1, size):
            multiplier *= (t + (i - 1)) / i
            result += differences[i - 1][-1] * multiplier
    return result


# Получение разделенных разностей
def get_divided_differences(size, x_values, y_values):
    differences = [[]]
    for i in range(size - 1):
        current = (y_values[i + 1] - y_values[i]) / (x_values[i + 1] - x_values[i])
        differences[0].append(current)

    for k in range(1, size - 1):
        differences.append([])
        for i in range(size - k - 1):
            numerator = differences[k - 1][i + 1] - differences[k - 1][i]
            denominator = x_values[i + k + 1] - x_values[i]
            current = numerator / denominator
            differences[k].append(current)
    return differences


# Получение конечных разностей
def get_finite_differences(size, y_values):
    differences = [[]]
    for i in range(size - 1):
        differences[0].append(y_values[i + 1] - y_values[i])

    for k in range(1, size - 1):
        differences.append([])
        for i in range(len(differences[k - 1]) - 1):
            differences[k].append(differences[k - 1][i + 1] - differences[k - 1][i])
    return differences


# Таблица конечных разностей с выравниванием
def print_table_finite_differences(size, x_values, y_values):
    differences = get_finite_differences(size, y_values)

    # Заголовок таблицы
    header = ["x", "y"] + [f"∆{k + 1}y" for k in range(size - 1)]
    col_width = 10  # фиксированная ширина столбцов
    header_row = "|".join(f"{name:^{col_width}}" for name in header)
    print(header_row)
    print("-" * len(header_row))

    # Строки таблицы
    for row in range(size):
        row_data = [x_values[row], y_values[row]]
        for k in range(size - 1 - row):
            row_data.append(differences[k][row])
        row_text = "|".join(
            f"{value:^{col_width}.4f}" if isinstance(value, float) else f"{value:^{col_width}}" for value in row_data)
        print(row_text)
    print("-" * len(header_row))
