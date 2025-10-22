from owlready2 import *
from util import *


def get_parents(onto, person):
    """Получение родителей из онтологии"""
    parents = list(person.hasParent)

    if not parents:
        return None, None

    father = None
    mother = None
    for parent in parents:
        if isinstance(parent, onto.Man):
            father = parent
        else:
            mother = parent
    return father, mother


def show_parents_info(onto, person):
    """Вывод информации о родителях"""
    father, mother = get_parents(onto, person)

    if father is None:
        father_info = "Нет данных"
    else:
        father_name, father_year = get_person_info(father)
        father_info = f"{father_name} ({father_year})"

    if mother is None:
        mother_info = "Нет данных"
    else:
        mother_name, mother_year = get_person_info(mother)
        mother_info = f"{mother_name} ({mother_year})"

    print("Родители:")
    print(f"Отец: {father_info}")
    print(f"Мать: {mother_info}")
    print_sep()


def show_siblings_info(onto, person):
    """Вывод информации о братьях и сестрах"""

    person_name, person_year = get_person_info(person)
    parents = person.hasParent
    brothers = set()
    sisters = set()

    for parent in parents:
        for child in parent.hasChild:
            child_name, child_year = get_person_info(child)
            if child_name == person_name and child_year == person_year:
                continue

            if isinstance(child, onto.Man):
                brothers.add(child)
            else:
                sisters.add(child)

    print("Братья и сёстры:")
    if len(brothers) == 0 and len(sisters) == 0:
        print("Нет данных")
    else:
        for brother in brothers:
            brother_name, brother_year = get_person_info(brother)
            print(f"Брат: {brother_name} ({brother_year})")
        for sister in sisters:
            sister_name, sister_year = get_person_info(sister)
            print(f"Сестра: {sister_name} ({sister_year})")
    print_sep()


def show_children_info(onto, person):
    """Вывод информации о детях"""
    sons = []
    daughters = []

    for child in person.hasChild:
        if isinstance(child, onto.Man):
            sons.append(child)
        else:
            daughters.append(child)

    print("Дети:")
    if len(daughters) == 0 and len(sons) == 0:
        print("Нет данных")
    else:
        for son in sons:
            son_name, son_year = get_person_info(son)
            print(f"Сын: {son_name} ({son_year})")
        for daughter in daughters:
            daughter_name, daughter_year = get_person_info(daughter)
            print(f"Дочь: {daughter_name} ({daughter_year})")
    print_sep()


def show_grandparents_info(onto, person):
    """Вывод информации о бабушках и дедушках"""
    grandmothers = set()
    grandfathers = set()

    parents = person.hasParent
    for parent in parents:
        for grandparent in parent.hasParent:
            if isinstance(grandparent, onto.Man):
                grandfathers.add(grandparent)
            else:
                grandmothers.add(grandparent)

    print("Бабушки и дедушки:")
    if len(grandfathers) == 0 and len(grandmothers) == 0:
        print("Нет данных")
    else:
        for grandmother in grandmothers:
            grandmother_name, grandmother_year = get_person_info(grandmother)
            print(f"Бабушка: {grandmother_name} ({grandmother_year})")
        for grandfather in grandfathers:
            grandfather_name, grandfather_year = get_person_info(grandfather)
            print(f"Дедушка: {grandfather_name} ({grandfather_year})")
    print_sep()


def show_marriages_info(onto, person):
    """Вывод информации о браках"""
    person_name, person_year = get_person_info(person)
    marriages = set()

    for marriage in onto.Marriage.instances():
        husband, wife, year = get_marriage_info(marriage)
        husband_name, husband_year = get_person_info(husband)
        wife_name, wife_year = get_person_info(wife)
        if husband_name == person_name and husband_year == person_year:
            marriages.add(marriage)
        if wife_name == person_name and wife_year == person_year:
            marriages.add(marriage)

    print("Браки:")
    if len(marriages) == 0:
        print("Нет данных")
    else:
        for marriage in marriages:
            husband, wife, year = get_marriage_info(marriage)
            husband_name, husband_year = get_person_info(husband)
            wife_name, wife_year = get_person_info(wife)
            if isinstance(person, onto.Man):
                print(f"Женился в {year} году на {wife_name} ({wife_year})")
            else:
                print(f"Вышла замуж в {year} году за {husband_name} ({husband_year})")
    print_sep()


def show_divorces_info(onto, person):
    """Вывод информации о разводах"""
    person_name, person_year = get_person_info(person)
    divorces = set()

    for divorce in onto.Divorce.instances():
        ex_husband, ex_wife, year = get_divorce_info(divorce)
        ex_husband_name, ex_husband_year = get_person_info(ex_husband)
        ex_wife_name, ex_wife_year = get_person_info(ex_wife)
        if ex_husband_name == person_name and ex_husband_year == person_year:
            divorces.add(divorce)
        if ex_wife_name == person_name and ex_wife_year == person_year:
            divorces.add(divorce)

    print("Разводы:")
    if len(divorces) == 0:
        print("Нет данных")
    else:
        for divorce in divorces:
            ex_husband, ex_wife, year = get_divorce_info(divorce)
            ex_husband_name, ex_husband_year = get_person_info(ex_husband)
            ex_wife_name, ex_wife_year = get_person_info(ex_wife)
            if isinstance(person, onto.Man):
                print(f"Развелся в {year} году с {ex_wife_name} ({ex_wife_year})")
            else:
                print(f"Развелась в {year} году с {ex_husband_name} ({ex_husband_year})")
    print_sep()


def show_existing_relations(onto, name, year):
    """"Показывает существующие в онтологии связи для человека"""
    print(f"Существующие связи для {name} ({year}): ")
    person = find_person_by_name_year(onto, name, year)

    show_parents_info(onto, person)
    show_siblings_info(onto, person)
    show_children_info(onto, person)
    show_grandparents_info(onto, person)
    show_marriages_info(onto, person)
    show_divorces_info(onto, person)
