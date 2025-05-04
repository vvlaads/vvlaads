import numpy as np
from scipy.stats import chi2, ks_2samp
import pandas as pd

# Загрузка данных
data = pd.read_csv('../test/sex_bmi_smokers.csv')

# Группы ИМТ
bins = [0, 18.5, 25, 30, 35, 40, np.inf]
labels = ['0-18.5', '18.5-25', '25-30', '30-35', '35-40', '40-inf']
data['bmi_group'] = pd.cut(data['bmi'], bins=bins, labels=labels)

# Таблица сопряженности
contingency_table = pd.crosstab(data['bmi_group'], data['smoker'])
print("Таблица сопряженности:")
print(contingency_table)

# Критерий хи-квадрат
observed = contingency_table.values
row_totals = observed.sum(axis=1).reshape(-1, 1)
col_totals = observed.sum(axis=0).reshape(1, -1)
grand_total = observed.sum()

# Ожидаемые значения
expected = row_totals @ col_totals / grand_total

# Хи-квадрат статистика
chi_squared = np.sum((observed - expected) ** 2 / expected)

# Степени свободы
df = (observed.shape[0] - 1) * (observed.shape[1] - 1)

# Мощность критерия (табличное)
p_chi = 1 - chi2.cdf(chi_squared, df=df)

print(f"\nСтатистика хи-квадрат: {chi_squared:.3f}")
print(f"Степени свободы: {df}")
print(f"Мощность критерия: {p_chi:.4f}")
if p_chi < 0.05:
    print("Отвергаем H_0: распределения различаются.")
else:
    print("Не отвергаем H_0: распределения схожи.")

# Разделение данных на две группы
bmi_smokers = data[data['smoker'] == 'yes']['bmi']
bmi_non_smokers = data[data['smoker'] == 'no']['bmi']

# Критерий Колмогорова-Смирнова
ks_stat, p_ks = ks_2samp(bmi_smokers, bmi_non_smokers)

print(f"\nСтатистика критерия Колмогорова-Смирнова: {ks_stat:.3f}")
print(f"Мощность критерия: {p_ks:.4f}")
if p_ks < 0.05:
    print("Отвергаем H_0: распределения различаются.")
else:
    print("Не отвергаем H_0: распределения схожи.")
