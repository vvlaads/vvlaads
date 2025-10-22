import re
from owlready2 import *
from util import *
from relations import show_existing_relations
from recommendations import show_recommended_relations


def start():
    """"Основной цикл выполнения программы"""
    onto = get_ontology("family_tree.owx").load()  # Загружаем онтологию
    while True:
        message = receive_message()
        handle_request(onto, message)


def receive_message():
    """Получение запроса от пользователя"""
    print("Введите запрос в формате: Покажи возможные связи для \"[ФИО]\", [Год] года рождения")
    return input()


def handle_request(onto, request):
    """"Обработка запроса пользователя"""
    pattern = r'Покажи возможные связи для\s+"([а-яА-Я]+\s[а-яА-Я]+\s[а-яА-Я]+)",\s+(\d{4})\s+года рождения'
    match = re.match(pattern, request)

    if match:
        name = match.group(1)
        year = int(match.group(2))
        print_sep()
        print("Вы ввели:")
        print(f"ФИО: {name}")
        print(f"Год рождения: {year}")
        print_sep()
        find_possible_relations(onto, name, year)
    else:
        print("Неверный формат запроса. Попробуйте ещё раз.")


def find_possible_relations(onto, name, year):
    """"Поиск существующих связей, либо их рекомендация"""
    person = find_person_by_name_year(onto, name, year)

    if person:
        print("Человек найден в онтологии")
        print_sep()
        show_existing_relations(onto, name, year)
    else:
        print("Человек не найден в онтологии")
        print_sep()
        show_recommended_relations(onto, name, year)


if __name__ == "__main__":
    start()
