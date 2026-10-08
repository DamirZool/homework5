package fill;

import student.Student;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class FileFill implements FillStrategy {

    @Override
    public List<Student> fill(int length, Scanner scanner) {
        printHint(length);
        while (true) {
            String path = readPath(scanner);
            if (path == null) {
                System.out.println("Ввод файла прерван. Возвращаю пустой список.");
                return List.of();
            }
            try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
                ReadResult result = readStudents(reader, length);
                if (result.students().size() < length) {
                    System.out.printf(
                            "Валидных строк %d, требуется %d "
                                    + "(всего в файле %d, битых %d). "
                                    + "Укажите другой файл или дополните текущий.%n%n",
                            result.students().size(), length,
                            result.totalLines(), result.skippedLines()
                    );
                    continue;
                }
                System.out.printf(
                        "Прочитано строк: %d. Пропущено: %d. Внесено в список: %d.%n",
                        result.totalLines(), result.skippedLines(), length
                );
                return result.students();
            } catch (FileNotFoundException e) {
                System.out.println("Файл не найден по указанному пути.");
            } catch (InvalidPathException e) {
                System.out.println("Некорректный путь к файлу.");
            } catch (IOException e) {
                System.out.println("Проблема при чтении данных из файла.");
            }
        }
    }

    private ReadResult readStudents(BufferedReader reader, int length) throws IOException {
        List<Student> students = new ArrayList<>(length);
        int totalLines = 0;
        int skippedLines = 0;

        String line;
        while ((line = reader.readLine()) != null) {
            totalLines++;
            Optional<Student> parsed = parseLine(line);
            if (parsed.isEmpty()) {
                skippedLines++;
                continue;
            }
            if (students.size() < length) {
                students.add(parsed.get());
            }
        }
        return new ReadResult(students, totalLines, skippedLines);
    }

    private Optional<Student> parseLine(String line) {
        try {
            String[] fields = line.strip().split(",");
            if (fields.length != 3) {
                throw new IllegalArgumentException("В строке должна содержаться вся информация о студенте");
            }
            String groupNum = fields[0].strip();
            double avgScore = Double.parseDouble(fields[1].strip());
            String studentId = fields[2].strip();
            return Optional.of(
                    new Student.Builder()
                            .setGroupNum(groupNum)
                            .setAvgScore(avgScore)
                            .setStudentId(studentId)
                            .build()
            );
        } catch (IllegalArgumentException e) {
            System.out.printf(
                    "Строка: \"%s\" не валидна (%s), %n",
                    line, e.getMessage()
            );
            return Optional.empty();
        }
    }

    private String readPath(Scanner scanner) {
        while (true) {
            System.out.println("Введите путь к файлу (или '0' для отмены):");
            String path = scanner.nextLine().strip();
            if (path.equals("0")) {
                return null;
            }
            if (!path.isEmpty()) {
                return path;
            }
            System.out.println("Путь не может быть пустым.");
        }
    }

    private void printHint(int length) {
        System.out.printf("""
                Данные в файле должны храниться в формате: Группа, Средний балл, Номер зачётки
                Пример: "А-01-23, 4.5, 12345"
                В массив будут внесены первые %d валидных строк.
                Файл при этом будет прочитан целиком — для полной статистики.%n%n
                """, length);
    }

    private record ReadResult(List<Student> students, int totalLines, int skippedLines) {}
}