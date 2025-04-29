from enum import Enum


# Опции для ввода данных
class InputMode(Enum):
    CONSOLE = 0,
    FILE = 1,
    FUNCTION = 2


# Список опций для ввода
def get_input_options():
    return [["Ввод через консоль", InputMode.CONSOLE], ["Ввод через файл", InputMode.FILE],
            ["Выбрать функцию", InputMode.FUNCTION]]
