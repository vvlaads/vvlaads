# Проверка, что аргумент является целым числом
def is_int(value):
    try:
        int(value)
        return True
    except ValueError:
        return False


# Проверка, что аргумент является числом с плавающей точкой
def is_float(value):
    try:
        float(value)
        return True
    except ValueError:
        return False
