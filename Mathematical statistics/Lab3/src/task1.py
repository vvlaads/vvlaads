import numpy as np
from math import log2
from scipy.stats import kstest, norm, chi2


# Получить ИМТ из файла
def get_bmi():
    result = []
    with open("../test/sex_bmi_smokers.csv") as file:
        file.readline()
        for line in file:
            row = line.strip().split(",")
            element = float(row[1])
            result.append(element)
    return result


# Считаем уникальные значения и их частоты
def get_unique_values(values):
    result = {}
    for value in values:
        if value not in result:
            result[value] = 1
        else:
            result[value] += 1
    return result


# Разбиваем на интервалы
def split_to_intervals(low, high, count):
    result = []
    step = (high - low) / count
    current = low
    for _ in range(count):
        next_val = current + step
        result.append((current, next_val))
        current = next_val
    return result


# Считаем вероятность попадания в интервалы
def get_probabilities(unique_dict, intervals_arr, count):
    result = [0] * len(intervals_arr)
    current_int = 0
    sorted_keys = sorted(unique_dict.keys())

    for key in sorted_keys:
        while current_int < len(intervals_arr) and key > intervals_arr[current_int][1]:
            current_int += 1
        if current_int >= len(intervals_arr):
            current_int = len(intervals_arr) - 1
        result[current_int] += unique_dict[key]

    result = [x / count for x in result]
    return result


# Нахождение ожидаемых вероятностей через нормальное распределение
def get_expected_probabilities(intervals_arr, average, square_err):
    result = []
    for interval in intervals_arr:
        a, b = interval
        probability = norm.cdf(b, average, square_err) - norm.cdf(a, average, square_err)
        result.append(probability)
    return result


# Подсчёт статистики Хи-квадрат
def get_chi_squared(prob, expected, count_int, count):
    result = 0
    for i in range(count_int):
        result += (prob[i] * count - expected[i] * count) ** 2 / (expected[i] * count)
    return result


# Критерий согласия Пирсона

bmi = get_bmi()
unique_values = get_unique_values(bmi)

n = len(bmi)
k = len(unique_values)

mu = np.mean(bmi)
sigma = np.std(bmi, ddof=1)
alpha = 0.05

interval_count = round(log2(n) + 1)
intervals = split_to_intervals(min(bmi), max(bmi), interval_count)

probabilities = get_probabilities(unique_values, intervals, n)
expected_probabilities = get_expected_probabilities(intervals, mu, sigma)

# Подсчёт хи-квадрат
chi_squared = get_chi_squared(probabilities, expected_probabilities, interval_count, n)

# Степени свободы
df = interval_count - 1 - 2

# Критическое значение
critical_value = chi2.ppf(1 - alpha, df)

print("Критерий согласия Пирсона:")
print("Хи-квадрат:", chi_squared)
print("Степени свободы:", df)
print("Критическое значение:", critical_value)
if chi_squared < critical_value:
    print("Распределение похоже на нормальное (не отвергаем H0)")
else:
    print("Распределение отличается от нормального (отвергаем H0)")

# Критерий Колмогорова-Смирнова
normalized_bmi = (bmi - np.mean(bmi)) / np.std(bmi, ddof=1)

stat, p_value = kstest(normalized_bmi, 'norm')
print("-" * 50)
print("Критерий Колмогорова-Смирнова:")
print("Статистика:", stat)
print("p-значение:", p_value)
if p_value > alpha:
    print("Распределение похоже на нормальное (не отвергаем H0)")
else:
    print("Распределение отличается от нормального (отвергаем H0)")
