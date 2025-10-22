from owlready2 import *
from util import *


def get_potential_parents(onto, name, year):
    """Возвращает множества возможных отцов и матерей."""
    surname, first_name, patronymic = parse_name(name)
    fathers = set()
    mothers = set()

    # Определяем пол
    is_man = patronymic is not None and not patronymic.endswith(('вна', 'чна'))

    # === Поиск возможных отцов ===
    if patronymic:
        for father in onto.Man.instances():
            father_full_name, father_year = get_person_info(father)
            father_surname, father_first_name, _ = parse_name(father_full_name)

            if (is_name_match_patronymic(father_first_name, patronymic) and
                    father_year is not None and year > father_year and
                    father_year + 15 <= year <= father_year + 60):
                fathers.add(father)

    # === Уточнение по фамилии для мужчин ===
    if is_man and surname:
        fathers_to_remove = set()
        for father in fathers:
            father_name, father_year = get_person_info(father)
            father_surname, _, _ = parse_name(father_name)
            if father_surname != surname:
                fathers_to_remove.add(father)
        fathers -= fathers_to_remove

    # === Поиск матерей по бракам найденных отцов ===
    for father in fathers:
        father_name, father_year = get_person_info(father)
        for marriage in onto.Marriage.instances():
            husband, wife, marriage_year = get_marriage_info(marriage)
            husband_name, husband_year = get_person_info(husband)

            if husband_name == father_name and husband_year == father_year:
                mothers.add(wife)

    return fathers, mothers


def show_rec_parents_info(onto, name, year):
    """Вывод информации о рекомендованных родителях."""
    fathers, mothers = get_potential_parents(onto, name, year)

    print("Возможные родители:")
    if not fathers and not mothers:
        print("Не удалось определить возможных родителей")
    else:
        if fathers:
            print("Возможные отцы:")
            for father in fathers:
                father_name, father_year = get_person_info(father)
                print(f"\t{father_name} ({father_year})")
        else:
            print("Отцы: не определены")

        if mothers:
            print("Возможные матери:")
            for mother in mothers:
                mother_name, mother_year = get_person_info(mother)
                print(f"\t{mother_name} ({mother_year})")
        else:
            print("Матери: не определены")

    print_sep()


def show_rec_siblings_info(onto, name, year):
    """Вывод информации о возможных братьях и сёстрах."""
    surname, first_name, patronymic = parse_name(name)
    fathers, mothers = get_potential_parents(onto, name, year)

    # Определяем пол
    is_man = patronymic is not None and not patronymic.endswith(('вна', 'чна'))

    brothers = set()
    sisters = set()

    person_name = name
    person_year = year

    # === Если есть родители ===
    if fathers or mothers:
        potential_parents = fathers | mothers

        for parent in potential_parents:
            for child in parent.hasChild:
                child_name, child_year = get_person_info(child)

                # Пропускаем самого человека
                if child_name == person_name and child_year == person_year:
                    continue

                # Определяем пол ребёнка
                if isinstance(child, onto.Man):
                    brothers.add(child)
                elif isinstance(child, onto.Woman):
                    sisters.add(child)

    # === Если родителей не удалось определить ===
    else:
        for sibling in onto.Person.instances():
            sibling_name, sibling_year = get_person_info(sibling)
            sibling_surname, _, sibling_patronymic = parse_name(sibling_name)

            # Проверяем схожесть по отчеству и возрасту
            if patronymic[:4] == sibling_patronymic[:4] and abs(sibling_year - year) < 20:
                if isinstance(sibling, onto.Man):
                    if is_man:
                        if sibling_surname == surname:
                            brothers.add(sibling)
                    else:
                        brothers.add(sibling)
                elif isinstance(sibling, onto.Woman):
                    sisters.add(sibling)

    # === Вывод результатов ===
    print("Возможные братья и сёстры:")
    if not brothers and not sisters:
        print("\tНе удалось определить возможных братьев или сестёр.")
    else:
        if brothers:
            print("Братья:")
            for brother in brothers:
                brother_name, brother_year = get_person_info(brother)
                print(f"\t{brother_name} ({brother_year})")
        else:
            print("Братья: не найдены")

        if sisters:
            print("Сёстры:")
            for sister in sisters:
                sister_name, sister_year = get_person_info(sister)
                print(f"\t{sister_name} ({sister_year})")
        else:
            print("Сёстры: не найдены")

    print_sep()


def show_rec_grandparents_info(onto, name, year):
    """Вывод информации о возможных бабушках и дедушках."""
    fathers, mothers = get_potential_parents(onto, name, year)

    grandfathers = set()
    grandmothers = set()

    # === Если есть родители ===
    if fathers or mothers:
        potential_parents = fathers | mothers

        for parent in potential_parents:
            for grandparent in parent.hasParent:
                # Определяем пол
                if isinstance(grandparent, onto.Man):
                    grandfathers.add(grandparent)
                elif isinstance(grandparent, onto.Woman):
                    grandmothers.add(grandparent)

    # === Вывод результатов ===
    print("Возможные бабушки и дедушки:")
    if not grandfathers and not grandmothers:
        print("\tНе удалось определить возможных бабушек или дедушек.")
    else:
        if grandfathers:
            print("Дедушки:")
            for grandfather in grandfathers:
                grandfather_name, grandfather_year = get_person_info(grandfather)
                print(f"\t{grandfather_name} ({grandfather_year})")
        else:
            print("Дедушки: не найдены")

        if grandmothers:
            print("Бабушки:")
            for grandmother in grandmothers:
                grandmother_name, grandmother_year = get_person_info(grandmother)
                print(f"\t{grandmother_name} ({grandmother_year})")
        else:
            print("Бабушки: не найдены")

    print_sep()


def show_recommended_relations(onto, name, year):
    """"Рекомендует связи для человека на основе существующих связей в онтологии"""
    print(f"Рекомендуемые связи для {name} ({year}): ")

    show_rec_parents_info(onto, name, year)
    show_rec_siblings_info(onto, name, year)
    show_rec_grandparents_info(onto, name, year)
