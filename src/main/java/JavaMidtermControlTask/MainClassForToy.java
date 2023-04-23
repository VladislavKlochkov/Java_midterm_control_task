package JavaMidtermControlTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class MainClassForToy {
    public static void main(String[] args) {
        Toy bear = new Toy(1,
                "Медведь",
                1,
                10
        );
        Toy wolf = new Toy(2,
                "Волк",
                1,
                10
        );
        Toy hare = new Toy(3,
                "Заяц",
                2,
                20
        );
        Toy bird = new Toy(4,
                "Птица",
                3,
                30
        );
        Toy fox = new Toy(5,
                "Лиса",
                3,
                30
        );

// 0. Формируем список игрушек для участия в розыгрыше.
        List<Toy> initialToysList = new ArrayList<>(List.of(bear, wolf, hare, bird));
        System.out.println(initialToysList);

// 1. Добавляем новую игрушку.
        List<Toy> toysList = addToy(initialToysList, fox);
        System.out.println(toysList);

// 2. Считаем общее количество игрушек.
        int total = toysCount(initialToysList);
        System.out.println(total);

// 3. Добавляем игрушки.
        toysList = addToyQuantity(initialToysList);
        System.out.println(toysList);

        total = toysCount(toysList);
        System.out.println(total);

// 4. После добавления игрушек, изменяем вероятность выдачи.
        toysList = changeToyFrequency(toysList, total);
        System.out.println(toysList);

// 5. Запускаем розыгрыш и формируем список призовых игрушек.
        Deque<String> prizeToys = raffleToys(toysList);
        System.out.println(prizeToys);

// 6. Уменьшаем количество игрушек и вероятность выдачи после выигрыша.
        toysList = quantityDecrease(toysList, prizeToys);
        total = toysCount(toysList);
        toysList = changeToyFrequency(toysList, total);
        System.out.println(toysList);
        System.out.println(total);

// 7. Записываем игрушку в файл.
        saveToyToFile(prizeToys);

// 8. Удаляем игрушку на выдачу из списка призов.
        removeToyFromPrizeToys(prizeToys);
        System.out.println(prizeToys);
    }

    // 1. Метод добавления новых игрушек.
    private static List<Toy> addToy(List<Toy> list, Toy name) {
        list.add(name);
        return list;
    }

    // 2. Метод добавления нового количества игрушек.
    private static List<Toy> addToyQuantity(List<Toy> list) {
        Scanner sc = new Scanner(System.in);
        System.out.printf("Введите название игрушки: ");
        String nameToy = sc.nextLine();
        for (Toy toy : list) {
            if (toy.getName().equalsIgnoreCase(nameToy)) {
                System.out.printf("Введите количество, которое хотите добавить для " + nameToy + ": ");
                int number = sc.nextInt();
                if (number > 0) {
                    toy.setQuantity(number + toy.getQuantity());
                } else if (number < 0) {
                    System.out.println("Некорректный ввод!");
                }
            }
        }
        return list;
    }

    // 3. Метод подсчёта общего количества игрушек.
    private static int toysCount(List<Toy> list) {
        int sum = 0;
        for (Toy toy : list) {
            sum += toy.getQuantity();
        }
        return sum;
    }

    // 4. Метод изменения частоты выпадения игрушки в зависимости от изменения количества игрушек.
    private static List<Toy> changeToyFrequency(List<Toy> list, int sum) {
        for (Toy toy : list) {
            toy.setFrequency(toy.getQuantity() * 100 / sum);
        }
        return list;
    }

    // 5. Метод розыгрыша с возможностью изменения вероятности выигрыша.
    public static Deque<String> raffleToys(List<Toy> list) {
        Deque<String> prize = new ArrayDeque<>();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.printf("Розыгрыш начинается! \n" +
                    "Введите 0 для выхода \n" +
                    "Введите 1 для продолжения игры \n");
            System.out.printf("Ваш выбор: ");
            int choice = sc.nextInt();
            if (choice == 1) {
                System.out.printf("Введите шанс выпадения игрушки: ");
                int chance = sc.nextInt();
                if (chance >= 0 && chance <= 100) {
                    Random random = new Random();
                    int num = random.nextInt(100);
                    if (num > chance) {
                        System.out.println("Неудача! Попробуйте сыграть еще!");
                    }
                    if (num < chance) {
                        for (Toy toy : list) {
                            if (toy.getFrequency() <= chance) {
                                prize.addLast(toy.getName());
                                System.out.println("Удача! Вы выиграли: " + toy.getName());
                            }
                        }
                    }
                }
            }
            if (choice == 0) {
                System.out.println("До свидания!");
                break;
            }
        }
        return prize;
    }

    // 6. Метод уменьшения количества игрушек после розыгрыша.
    private static List<Toy> quantityDecrease(List<Toy> list, Deque<String> prizeList) {
        for (Toy toy : list) {
            for (String prizeToy : prizeList) {
                if (toy.getName().equalsIgnoreCase(prizeToy)) {
                    if (toy.getQuantity() > 0) {
                        toy.setQuantity(toy.getQuantity() - 1);
                    } else if (toy.getQuantity() <= 0) {
                        toy.setQuantity(0);
                    }
                }
            }
        }
        return list;
    }

    // 7. Метод записи игрушки в файл.
    private static void saveToyToFile(Deque<String> list) {
        try (PrintWriter pw = new PrintWriter("src/main/resources/files/prizeToys.txt")) {
            String text = list.getFirst();
            pw.print(text + "\n");
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // 8. Метод удаления игрушки на выдачу из списка призов.
    public static Deque<String> removeToyFromPrizeToys(Deque<String> list) {
        list.removeFirst();
        return list;
    }
}