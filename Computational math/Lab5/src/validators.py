# Проверка, что значение является числом с плавающей точкой
def is_float(value):
    try:
        float(value)
        return True
    except ValueError:
        return False


# Проверка, что значение является целым числом
def is_int(value):
    try:
        int(value)
        return True
    except ValueError:
        return False


# Проверка, что значение является натуральным числом
def is_nat(value):
    if is_int(value):
        value = int(value)
        if value > 0:
            return True
    return False


# Проверка, что функция вычисляема в заданной точке
def func_is_correct(value, function):
    try:
        function(value)
        return True
    except ValueError:
        return False


# Проверка, что значения x находятся на одинаковом расстоянии
def is_uniform_spacing(x_values):
    if len(x_values) < 2:
        return False
    step = x_values[1] - x_values[0]
    for i in range(len(x_values) - 1):
        if x_values[i + 1] - x_values[i] - step > 1e-6:
            return False
    return True
