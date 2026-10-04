package homework.fill;

import homework.student.Student;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FillMain {

    private enum FillType {
        BACK(0),
        MANUAL(1),
        FILE(2),
        RANDOM(3);

        private static final Map<Integer, FillType> BY_CODE =
                Arrays.stream(values())
                        .collect(Collectors.toMap(t -> t.code, t -> t));

        private final int code;

        FillType(int code) {
            this.code = code;
        }

        public static FillType fromCode(int code) {
            FillType type = BY_CODE.get(code);
            if (type == null) {
                throw new IllegalArgumentException("Неверный код: " + code);
            }
            return type;
        }

        public FillStrategy createStrategy() {
            return switch (this) {
                case MANUAL -> new ManualFill();
                case FILE -> new FileFill();
                case RANDOM -> new RandomFill();
                case BACK -> throw new UnsupportedOperationException(
                        "BACK не создаёт стратегию заполнения");
            };
        }
    }

    public static List<Student> fillMain(Scanner scanner) {
        System.out.print("Введите длину массива (0 — вернуться в меню): ");
        int length = readIntInRange(scanner, 0, Integer.MAX_VALUE, "Введите положительное число");
        if (length == 0) {
            return null;
        }
        System.out.print("Выберите способ заполнения: 1 — вручную, 2 — из файла, 3 — рандомно, 0 — вернуться в меню: ");
        int strategyCode = readIntInRange(scanner, 0, 3, "Неверное значение. Введите число от 0 до 3");
        FillType fillType = FillType.fromCode(strategyCode);
        if (fillType == FillType.BACK) {
            return null;
        }
        FillStrategy strategy = fillType.createStrategy();
        return strategy.fill(length, scanner);
    }

    private static int readIntInRange(Scanner scanner, int min, int max, String errorMessage) {
        while (true) {
            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                scanner.nextLine();
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println(errorMessage);
            } else if (scanner.hasNext()) {
                scanner.next();
                System.out.print("Введите число: ");
            } else {
                throw new IllegalStateException("Входной поток завершён");
            }
        }
    }
}