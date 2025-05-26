import numpy as np
from scipy.stats import f

# Чтение данных
file = open("C:/Задания/Математическая статистика/Практические/Практическая 4/Lab4/test/iris.csv")
file.readline()

setosa = []
versicolor = []
virginica = []
total = []

for line in file:
    row = line.split(",")

    sepal_length = float(row[0])
    sepal_width = float(row[1])
    petal_length = float(row[2])
    petal_width = float(row[3])

    total_area = sepal_length * sepal_width + petal_length * petal_width

    species = str(row[4].replace('"', '').replace("\n", ""))

    total.append(total_area)
    if species == "setosa":
        setosa.append(total_area)
    elif species == "versicolor":
        versicolor.append(total_area)
    elif species == "virginica":
        virginica.append(total_area)

# Параметры ANOVA
I = 150  # Общее количество наблюдений
J = 3  # Количество групп

# Вычисление средних
setosa_average = np.average(setosa)
versicolor_average = np.average(versicolor)
virginica_average = np.average(virginica)
total_average = np.average(total)

# Вычисление межгрупповой дисперсии
SB = len(setosa) * (setosa_average - total_average) ** 2
SB += len(versicolor) * (versicolor_average - total_average) ** 2
SB += len(virginica) * (virginica_average - total_average) ** 2
MSB = SB / (J - 1)

# Вычисление внутригрупповой дисперсии
SW = 0
for value in setosa:
    SW += (value - setosa_average) ** 2
for value in versicolor:
    SW += (value - versicolor_average) ** 2
for value in virginica:
    SW += (value - virginica_average) ** 2
MSW = SW / (I - J)

# Вычисление F-статистики
F = MSB / MSW

# Критическое значение F-распределения
alpha = 0.05  # Уровень значимости
df_between = J - 1  # Степени свободы числителя
df_within = I - J  # Степени свободы знаменателя

f_critical = f.ppf(1 - alpha, df_between, df_within)

# Вычисление p-value
p_value = 1 - f.cdf(F, df_between, df_within)

# Проверка гипотезы
if F < f_critical:
    print(f"F = {F:.4f} < F-критическое = {f_critical:.4f}")
    print(f"p-value = {p_value:.10f}")
    print("Не отвергаем гипотезу H0: средние значения площадей одинаковы")
else:
    print(f"F = {F:.4f} ≥ F-критическое = {f_critical:.4f}")
    print(f"p-value = {p_value:.10f}")
    print("Отвергаем гипотезу H0: средние значения площадей различаются")
