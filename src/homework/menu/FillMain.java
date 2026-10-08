package menu;

import fill.*;
import fill.enums.FillType;
import student.Student;

import java.util.List;
import java.util.Scanner;

class FillMain {

    private static final int MAX = 4;
    private static final int MIN = 0;

    public static List<Student> fillMain(Scanner scanner) {
        System.out.print("Введите длину массива (0 — отмена): ");
        int length = checkInt(scanner, false);
        if (length == 0) return null;

        System.out.print("Выберите способ заполнения: 1 — вручную, 2 — из файла, 3 — рандомно, 4 - рандомно потоком, 0 — вернуться в меню: ");
        int code = checkInt(scanner, true);
        FillType type = FillType.fromCode(code);
        if (type == FillType.BACK) return null;

        FillStrategy strategy = type.createStrategy();

        return strategy.fill(length, scanner);
    }
    
    private static int checkInt(Scanner scanner, boolean checkRange) {
        while (true) {
            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                scanner.nextLine();
                if (!checkRange || (value >= MIN && value <= MAX)) {
                    return value;
                }
                System.out.printf("Неверное значение. Введите число от %d до %d%n", MIN, MAX);
            } else if (scanner.hasNext()) {
                scanner.next();
                System.out.println("Неверное значение. Введите число");
            } else {
                throw new IllegalStateException("Входной поток завершён");
            }
        }
    }
}