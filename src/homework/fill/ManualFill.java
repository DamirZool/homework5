package fill;

import student.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ManualFill implements FillStrategy {

    @Override
    public List<Student> fill(int length, Scanner scanner) {
        if (length < 0) {
            throw new IllegalArgumentException("Длина должна быть >= 0, введено:" + length);
        }
        List<Student> result = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            System.out.printf("Заполните параметры для %d элемента массива%n", i+1);
            System.out.print("Введите номер группы студента:" );
            String groupName = readString(scanner, "группы");
            System.out.print("Введите номер зачётной книжки студента: ");
            String studentId = readString(scanner, "зачётной книжки");
            System.out.print("Введите средний бал студента:" );
            double avgScore = readAvgScore(scanner);
            result.add(new Student.Builder().setGroupNum(groupName).setAvgScore(avgScore).setStudentId(studentId).build());
            System.out.println();
        }
        return result;
    }

    private String readString(Scanner scanner, String massage) {
        while (true) {
            String groupName = scanner.nextLine().trim();
            if (!groupName.isEmpty()) {
                return groupName;
            }
            System.out.printf("Номер %s не может быть пустой. Введите значение повторно: ", massage);
        }
    }

    private double readAvgScore(Scanner scanner) {
        while (true) {
            if (scanner.hasNextDouble()) {
                double avgScore = scanner.nextDouble();
                scanner.nextLine();
                if (avgScore < Student.MIN_SCORE || avgScore > Student.MAX_SCORE) {
                    System.out.println("Средний балл должен быть от 0 до 100");
                } else return avgScore;
            } else if (scanner.hasNext()) {
                scanner.next();
                System.out.println("Необходимо ввести числовое значение.");
            } else {
                throw new IllegalStateException("Входной поток завершён");
            }
        }
    }
}