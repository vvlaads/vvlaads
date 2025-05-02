from random import Random


# Чтение исходной матрицы из файла
def read_file(filename):
    matrix = []
    count = 0
    with open(filename) as file:
        for line in file:
            matrix.append([])
            row = line.split()
            for element in row:
                matrix[count].append(float(element))
            count += 1
    return matrix


# Нахождение суммы маршрута
def get_sum_by_order(matrix, order):
    result = matrix[order[-1] - 1][order[0] - 1]  # возврат в начальный город
    for i in range(len(order) - 1):
        row = order[i] - 1
        column = order[i + 1] - 1
        result += matrix[row][column]
    return result


# Генерирует изначальную популяцию
def generate_population(matrix, population_size):
    population = []

    # Порядок городов 1, 2, 3, ...
    cities = []
    for i in range(1, len(matrix) + 1):
        cities.append(i)

    random = Random()
    for i in range(population_size):
        route = cities[:]  # Копирование изначального порядка в новый маршрут

        # Создаем уникальный маршрут, перемешивая элементы
        random.shuffle(route)
        while route in population:
            random.shuffle(route)

        population.append(route)
    return population


# Генерация потомков
def generate_child(population):
    random = Random()

    # Выбираем пару родителей
    first_idx = random.randint(0, len(population) - 1)
    second_idx = random.randint(0, len(population) - 1)
    while second_idx == first_idx:
        second_idx = random.randint(0, len(population) - 1)

    first_parent = population[first_idx]
    second_parent = population[second_idx]

    # Выбираем точку разрыва
    separate_dot = random.randint(1, len(first_parent) - 2)

    # Создаем потомков
    first_child = first_parent[:separate_dot]
    for city in second_parent:
        if city not in first_child:
            first_child.append(city)

    second_child = second_parent[:separate_dot]
    for city in first_parent:
        if city not in second_child:
            second_child.append(city)

    return first_child, second_child


# Мутация потомков
def mutation(first_child, second_child, prob):
    random = Random()

    # Функция мутации для одного потомка
    def mutate(route):
        if random.random() < prob:
            i = random.randint(0, len(first_child) - 1)
            j = random.randint(0, len(first_child) - 1)
            while i == j:
                j = random.randint(0, len(first_child) - 1)
            route[i], route[j] = route[j], route[i]
        return route

    return mutate(first_child), mutate(second_child)


# Сортировка таблицы популяции
def sort_population(population, matrix):
    # Сортируем популяцию по стоимости маршрутов
    sorted_population = sorted(population, key=lambda route: get_sum_by_order(matrix, route))
    return sorted_population


# Добавление потомков в таблицу популяции (если они уникальны)
def append_children(first, second, population, matrix):
    size = len(population)
    if first not in population:
        population.append(first)
    if second not in population:
        population.append(second)
    sorted_population = sort_population(population, matrix)
    return sorted_population[:size]


# Выполнение одной итерации
def iteration(population, matrix, prob):
    first, second = generate_child(population)
    first, second = mutation(first, second, prob)
    population = append_children(first, second, population, matrix)
    return population


# Вывести маршруты текущей популяции
def print_population(population, matrix):
    for route in population:
        print(f"Маршрут: {route} Длина: {get_sum_by_order(matrix, route)}")


# === MAIN ===
name = "../test/town1"
n = 4
mut_prob = 0.01

town_roads = read_file(name)
town_roads_population = generate_population(town_roads, n)

for number in range(10000):
    town_roads_population = iteration(town_roads_population, town_roads, mut_prob)

print_population(town_roads_population, town_roads)
