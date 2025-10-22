from owlready2 import *
import re


def print_sep():
    """Вывод разделителя"""
    print("---  ---  ---  ---  ---")


def get_prop(obj, prop_name):
    """Получение свойства объекта"""
    p = getattr(obj, prop_name, None)
    return p[0] if p else None


def get_person_info(person):
    """Получение информации о человеке"""
    name = get_prop(person, "fullName")
    year = get_prop(person, "birthYear")
    return name, year


def get_marriage_info(marriage):
    """Получение информации о браке"""
    husband = get_prop(marriage, "husband")
    wife = get_prop(marriage, "wife")
    year = get_prop(marriage, "marriageYear")
    return husband, wife, year


def get_divorce_info(divorce):
    """Получение информации о разводе"""
    ex_husband = get_prop(divorce, "ex-husband")
    ex_wife = get_prop(divorce, "ex-wife")
    year = get_prop(divorce, "divorceYear")
    return ex_husband, ex_wife, year


def find_person_by_name_year(onto, name, year):
    """Поиск человека по имени и году рождения"""
    for person in onto.Person.instances():
        person_name, person_year = get_person_info(person)
        if person_name == name and person_year == year:
            return person
    return None


def parse_name(full_name):
    """Парсит ФИО на составляющие."""
    parts = full_name.split()
    if len(parts) == 3:
        return parts[0], parts[1], parts[2]  # фамилия, имя, отчество
    else:
        return full_name, None, None  # нестандартный формат


def is_name_match_patronymic(first_name, patronymic):
    """Проверяет, соответствует ли имя отца отчеству."""
    if not first_name or not patronymic:
        return False

    first_name_lower = first_name.lower()
    patronymic_lower = patronymic.lower()

    return (first_name_lower in patronymic_lower or
            patronymic_lower.startswith(first_name_lower[:3]) or
            first_name_lower.startswith(patronymic_lower[:3]))
