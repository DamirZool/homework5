package menu;

import java.util.Scanner;

public class MenuIO {
    protected final Scanner scanner;

    public MenuIO(Scanner scanner) {
        this.scanner = scanner;
    }

    protected void printMainMenu() {
        System.out.print("""
                Меню:
                1 - создать список студентов
                2 - показать список студентов
                3 - отсортировать список
                4 - сортировка по четности среднего бала
                5 - очистить список
                6 - выгрузить список
                7 - посчитать совпадения
                0 - выход
               \s
                Выберите пункт:\s""");
    }

    protected int readChoice(int min, int max) {
        while (true) {
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();
                if (choice >= min && choice <= max) {
                    return choice;
                }
                System.out.println("Введите число от " + min + " до " + max);
            } else if (scanner.hasNext()) {
                scanner.next();
                System.out.println("Введите число от " + min + " до " + max);
            } else {
                throw new IllegalStateException("Входной поток завершён");
            }
        }
    }

    protected void pause() {
        System.out.println("\nНажмите Enter для продолжения...");
        scanner.nextLine();
        System.out.println();
    }
}